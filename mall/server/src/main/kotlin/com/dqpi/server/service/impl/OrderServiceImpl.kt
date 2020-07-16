package com.dqpi.server.service.impl

import com.dqpi.server.dao.OrderMasterDao
import com.dqpi.server.dao.OrderItemDao
import com.dqpi.server.dao.ProductDao
import com.dqpi.server.dao.ShippingDao
import com.dqpi.server.domain.common.PageVo
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.OrderMaster
import com.dqpi.server.domain.entity.OrderItem
import com.dqpi.server.domain.entity.Product
import com.dqpi.server.domain.entity.Shipping
import com.dqpi.server.domain.vo.OrderItemVo
import com.dqpi.server.domain.vo.OrderVo
import com.dqpi.server.enums.OrderStatusEnum
import com.dqpi.server.enums.PaymentTypeEnum
import com.dqpi.server.enums.ProductStatusEnum
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.service.CartService
import com.dqpi.server.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.annotation.Resource

@Service
class OrderServiceImpl: OrderService {
    
    private val log = LoggerFactory.getLogger(this::class.java)

    @Resource
    private lateinit var shippingDao: ShippingDao
    
    @Resource
    private lateinit var cartService: CartService
    
    @Resource
    private lateinit var productDao: ProductDao
    
    @Resource
    private lateinit var orderMasterDao: OrderMasterDao
    
    @Resource
    private lateinit var orderItemDao: OrderItemDao
    
    /**
     * 创建订单
     */
    @Transactional
    override fun create(uid: Int, shippingId: Int): ResponseVo<OrderVo> {
        //收货地址校验
        val shippingOpt = shippingDao.findByIdAndUserId(shippingId, uid)
        require(shippingOpt.isPresent) { 
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST)
        }
        
        //获取购物车, 校验（是否有商品，库存)
        val cartList = cartService.listForCart(uid).asSequence()
                .filter { it.productSelected }
                .toList()
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY)
        }

        //组装productMap["id"] = Product()
        val productIdList = cartList.asSequence().map { it.productId!! }.toList()
        val productList = productDao.findAllByIdIn(productIdList)
        val productMap = productList.asSequence().map { it.id to it }.toMap()
        
        
        val orderItemList = ArrayList<OrderItem>()
        val orderNo = generateOrderNo()
        cartList.forEach { 
            //根据productId查询数据库
            val product = productMap[it.productId] ?: return ResponseVo.error(
                    ResponseEnum.PRODUCT_NOT_EXIST, "商品不存在. productId = ${it.productId}")
            
            //商品上下架状态
            require(product.status == ProductStatusEnum.ON_SALE.code) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE, 
                        "商品不是在售状态. productName = ${product.name}")
            }
            
            //库存是否充足
            if (product.stock ?: 0 < it.quantity ?: 0) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR, 
                        "库存不正确. productName = ${product.name}")
            }
            
            orderItemList.add(buildOrderItem(uid, orderNo, it.quantity ?: 0, product))

            //减库存
            product.stock = product.stock!! - it.quantity!!
            try {
                productDao.save(product)
            } catch (e: Exception) {
                return ResponseVo.error(ResponseEnum.ERROR)
            }

        }
        
        //计算总价, 计算被选中的商品价格
        //生成订单
        val order = buildOrder(uid, orderNo, shippingId, orderItemList)
        
        try {
            orderMasterDao.save(order)
            orderItemDao.saveAll(orderItemList)
        }catch (e: Exception){
            log.error("订单生成失败, 错误信息: ${e.message}")
            return ResponseVo.error(ResponseEnum.ERROR)
        }
        
        //更新购物车
        cartList.forEach { cartService.delete(uid, it.productId!!) }
        
        //构造orderVo
        val orderVo = buildOrderVo(order, orderItemList, shippingOpt.get())
        
        return ResponseVo.success(data = orderVo)
    }

    /**
     * 分页查询订单列表
     */
    override fun list(uid: Int, pageNum: Int, pageSize: Int): ResponseVo<PageVo<List<OrderVo>>> {
        val pageRequest = PageRequest.of(pageNum - 1, pageSize)
        val pageOrder = orderMasterDao.findAllByUserId(uid, pageRequest)

        //组装orderItemMap["orderNo"] = List<OrderItem>()
        val orderNoSet = pageOrder.content.asSequence().map { it.orderNo!! }.toHashSet()
        val orderItemList = orderItemDao.findAllByOrderNoIn(orderNoSet)
        val orderItemMap = orderItemList.asSequence().groupBy { it.orderNo }

        //组装shippingMap["id"] = Shipping()
        val shippingIdSet = pageOrder.content.asSequence().map { it.shippingId!! }.toHashSet()
        val shippingList = shippingDao.findAllByIdIn(shippingIdSet)
        val shippingMap = shippingList.asSequence().map { it.id to it }.toMap()

        //组装orderVoList
        val orderVoList = ArrayList<OrderVo>()
        pageOrder.content.forEach {
            val orderVo = buildOrderVo(it, orderItemMap[it.orderNo] ?: error("不存在的订单列表"),
                    shippingMap[it.shippingId] ?: error("不存在的地址"))
            orderVoList.add(orderVo)
        }
        
        //组装pageVo
        val pageVo = PageVo<List<OrderVo>>()
        pageVo.pageNum = pageOrder.number + 1
        pageVo.pageSize = pageOrder.size
        pageVo.totalElements = pageOrder.totalElements
        pageVo.pageData = orderVoList
        
        return ResponseVo.success(data = pageVo)
    }

    /**
     * 订单详情
     */
    override fun detail(uid: Int, orderNo: Long): ResponseVo<OrderVo> {
        val orderOpt = orderMasterDao.findByOrderNo(orderNo)
        require(orderOpt.isPresent || orderOpt.get().userId == uid) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST)
        }
        
        orderOpt.get().let {
            val orderItemList = orderItemDao.findAllByOrderNoIn(hashSetOf(it.orderNo!!))

            val shippingOpt = shippingDao.findById(it.shippingId ?: -1)
            
            val orderVo = buildOrderVo(it, orderItemList, shippingOpt.orElse(Shipping()))
            
            return ResponseVo.success(data = orderVo)
        }
    }

    /**
     * 取消订单
     */
    override fun cancel(uid: Int, orderNo: Long): ResponseVo<String> {
        val orderOpt = orderMasterDao.findByOrderNo(orderNo)
        require(orderOpt.isPresent || orderOpt.get().userId == uid) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST)
        }
        
        orderOpt.get().let {
            //只有未付款订单可以取消
            require(it.status == OrderStatusEnum.NO_PAY.code) {
                return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR)
            }
            
            it.status = OrderStatusEnum.CANCELED.code
            it.closeTime = LocalDateTime.now()
            try {
                orderMasterDao.save(it)
            }catch (e: Exception) {
                log.error("取消订单失败. orderId=${it.id}")
                return ResponseVo.error(ResponseEnum.ERROR)
            }
        }
        
        return ResponseVo.success()
    }


    private fun buildOrderVo(order: OrderMaster, orderItemList: List<OrderItem>, 
                             shipping: Shipping): OrderVo {
        val orderVo = OrderVo()
        BeanUtils.copyProperties(order, orderVo)

        val orderItemVoList = orderItemList.asSequence()
                .map {
                    val orderItemVo = OrderItemVo()
                    BeanUtils.copyProperties(it, orderItemVo)
                    orderItemVo
                }
                .toList()
        
        orderVo.orderItemVoList = orderItemVoList
        
        if (shipping.id != null) {
            orderVo.shippingId = shipping.id
            orderVo.shippingVo = shipping
        }
        
        return orderVo
    }

    private fun buildOrder(uid: Int, orderNo: Long, shippingId: Int, 
                           orderItemList: List<OrderItem>): OrderMaster {
        return OrderMaster(
                orderNo = orderNo,
                userId = uid,
                shippingId = shippingId,
                payment = orderItemList.asSequence().map { it.totalPrice!! }.reduce(BigDecimal::add),
                paymentType = PaymentTypeEnum.PAY_ONLINE.code,
                postage = 0,
                status = OrderStatusEnum.NO_PAY.code
        )
    }
    
    private fun buildOrderItem(uid: Int, orderNo: Long, quantity: Int,  product: Product): OrderItem {
        return OrderItem(
                userId = uid,
                orderNo = orderNo,
                productId = product.id,
                productName = product.name,
                productImage = product.mainImage,
                currentUnitPrice = product.price,
                quantity = quantity,
                totalPrice = product.price!!.multiply(BigDecimal(quantity.toString()))
        )
    }

    private fun generateOrderNo(): Long {
        return System.currentTimeMillis() + (Math.random() * 999).toLong()
    }
}