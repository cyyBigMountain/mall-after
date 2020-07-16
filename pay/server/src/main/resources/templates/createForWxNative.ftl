<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf-8">
    <title>支付</title>
    <script src="https://cdn.bootcss.com/jquery/1.5.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
</head>
<body>
<div id="myQrcode"></div>
<div id="orderId" hidden>${orderNo}</div>
<div id="returnUrl" hidden>${returnUrl}</div>

</body>
</html>

<script>
    jQuery('#myQrcode').qrcode({
        text: "${codeUrl}"
    });
    
    setInterval(() => {
        console.log("开始查询支付状态")
        $.ajax({
            url: "/pay/queryByOrderNo",
            data: {
                "orderNo": ${orderNo}
            },
            success: (result) => {
                console.log(result)
                if (result.platformStatus != null && result.platformStatus === "SUCCESS") {
                    location.href = "${returnUrl}"
                }
            }
        })
    }, 2000)
</script>