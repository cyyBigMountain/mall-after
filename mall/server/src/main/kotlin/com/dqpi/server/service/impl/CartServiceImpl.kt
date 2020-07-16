package com.dqpi.server.service.impl

import com.alibaba.fastjson.JSON
import com.dqpi.server.dao.ProductDao
import com.dqpi.server.domain.common.ResponseVo
import com.dqpi.server.domain.entity.Cart
import com.dqpi.server.domain.entity.Product
import com.dqpi.server.domain.vo.CartProductVo
import com.dqpi.server.domain.vo.CartVo
import com.dqpi.server.enums.ProductStatusEnum
import com.dqpi.server.enums.ResponseEnum
import com.dqpi.server.form.CartAddForm
import com.dqpi.server.form.CartUpdateForm
import com.dqpi.server.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.math.BigDecimal
import javax.annotation.Resource

@Service
class CartServiceImpl: CartService {
    
    @Resource
    private lateinit var productDao: ProductDao
    
    @Autowired
    private lateinit var redisTemplate: StringRedisTemplate
    
    
    /**
     * 购物车添加商品
     */
    override fun add(uid: Int, cartAddForm: CartAddForm): ResponseVo<CartVo> {
        val quantity = 1
        val productOpt = productDao.findById(cartAddForm.productId!!)
        //商品是否存在
        require(productOpt.isPresent) { 
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST)
        }

        productOpt.get().let {
            //商品是否正常在售
            require(it.status == ProductStatusEnum.ON_SALE.code) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE)
            }

            //商品库存是否充足
            require(it.stock!! > 0) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR)
            }
                
            //写入redis
            //key: cart_{id}
            val (opsForHash, redisKey) = getOpsForHashAndRedisKey(uid)

            val cart: Cart
            val value = opsForHash.get(redisKey, it.id.toString())
            if (StringUtils.isEmpty(value)) {
                cart = Cart(it.id, quantity, cartAddForm.selected)
            }else {
                cart = JSON.parseObject(value, Cart::class.java)
                cart.quantity = cart.quantity!! + quantity
            }
            
            opsForHash.put(redisKey, it.id.toString(), JSON.toJSONString(cart))
        }
        
        return list(uid)
    }

    /**
     * 查询购物车商品
     */
    override fun list(uid: Int): ResponseVo<CartVo> {
        val (opsForHash, redisKey) = getOpsForHashAndRedisKey(uid)
        
        //获取redis内所有商品id
        val ids = opsForHash.entries(redisKey).map { (id, _) -> id.toInt() }.toList()
        //数据库查询所有redis内的商品
        val products = productDao.findAllByIdInAndStatus(ids)
        //创建返回所需对象
        val cartVo = CartVo()
        val cartProductVoList = ArrayList<CartProductVo>()
        //是否全选
        var selectAll = true
        //购物车产品数量总数
        var cartTotalQuantity = 0
        //购物车总价
        var cartTotalPrice = BigDecimal("0")
        
        
        //数据组装
        opsForHash.entries(redisKey).forEach{ (_, value) -> 
            val cart = JSON.parseObject(value, Cart::class.java)
            products.asSequence()
                    .filter { it.id == cart.productId }
                    .forEach {
                        val cartProductVo = productToCartProductVo(cart, it)
                        cartProductVoList.add(cartProductVo)
                        
                        //判断是否选中
                        if (!cart.productSelected) { selectAll = false }
                        
                        //计算总价(只计算选中)
                        if (cart.productSelected) {
                            cartTotalPrice = cartTotalPrice.add(cartProductVo.productTotalPrice)
                        }
                    }
            
            cartTotalQuantity += cart.quantity ?: 0
        }
        
        cartVo.selectAll = selectAll
        cartVo.cartTotalQuantity = cartTotalQuantity
        cartVo.cartTotalPrice = cartTotalPrice
        cartVo.cartProductVoList = cartProductVoList
        return ResponseVo.success(data = cartVo)
    }

    /**
     * 跟新购物车商品
     */
    override fun update(uid: Int, productId: Int, cartUpdateForm: CartUpdateForm): ResponseVo<CartVo> {
        val (opsForHash, redisKey) = getOpsForHashAndRedisKey(uid)
        val value = opsForHash.get(redisKey, productId.toString())
        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错 
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST)
        }

        //已经有了，修改内容
        val cart = JSON.parseObject(value, Cart::class.java)
        if (cartUpdateForm.quantity >= 0) {
            cart.quantity = cartUpdateForm.quantity
        }
        cart.productSelected = cartUpdateForm.selected

        opsForHash.put(redisKey, productId.toString(), JSON.toJSONString(cart))
        return list(uid)
    }
    

    /**
     * 删除购物车商品
     */
    override fun delete(uid: Int, productId: Int): ResponseVo<CartVo> {
        val (opsForHash, redisKey) = getOpsForHashAndRedisKey(uid)
        
        val value = opsForHash.get(redisKey, productId.toString())
        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错 
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST)
        }

        opsForHash.delete(redisKey, productId.toString())
        return list(uid)
    }

    /**
     * 全选
     */
    override fun selectAll(uid: Int): ResponseVo<CartVo> {
        return selectUpd(uid, true)
    }

    /**
     * 全不选
     */
    override fun unSelectAll(uid: Int): ResponseVo<CartVo> {
        return selectUpd(uid, false)
    }
    
    
    /**
     * 商品总数
     */
    override fun sum(uid: Int): ResponseVo<Int> {
        val sum = listForCart(uid).asSequence()
                .map { it.quantity }
                .reduce { acc, i -> (acc ?: 0) + (i ?: 0) }
        return ResponseVo.success(data = sum ?: 0)
    }

    /**
     * 修改选中状态
     */
    private fun selectUpd(uid: Int, selected: Boolean): ResponseVo<CartVo> {
        val (opsForHash, redisKey) = getOpsForHashAndRedisKey(uid)
        listForCart(uid).forEach {
            it.productSelected = selected
            opsForHash.put(redisKey, it.productId.toString(), JSON.toJSONString(it))
        }

        return list(uid)
    }

    /**
     * 获取购物车列表
     */
    override fun listForCart(uid: Int): List<Cart> {
        val (opsForHash, redisKey) = getOpsForHashAndRedisKey(uid)
        val cartList = ArrayList<Cart>()
        opsForHash.entries(redisKey).forEach { (_, cart) ->
            cartList.add(JSON.parseObject(cart, Cart::class.java))
        }

        return cartList
    }
    
    
    /**
     * 获取OpsForHashAndRedisKey
     */
    private fun getOpsForHashAndRedisKey(uid: Int): Pair<HashOperations<String, String, String>, String>{
        val opsForHash = redisTemplate.opsForHash<String, String>()
        val redisKey = "cart_$uid"
        return Pair(opsForHash, redisKey)
    }
    
    
    /**
     * 类型转换
     */
    private fun productToCartProductVo(cart: Cart, product: Product): CartProductVo {
        return CartProductVo(
                product.id,
                cart.quantity,
                product.name,
                product.subtitle,
                product.mainImage,
                product.price,
                product.status, 
                product.price?.multiply(BigDecimal(cart.quantity.toString())),
                product.stock,
                cart.productSelected
        )
    }
}