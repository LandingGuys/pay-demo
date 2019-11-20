<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>支付</title>
    </head>
<body>
<h3>微信支付</h3>
<div id="myqrcode">

</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.js"></script>
<script src="https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>

<script type="text/javascript">
    jQuery('#myqrcode').qrcode({
        text : "${codeUrl}"
    });
</script>
</body>
</html>