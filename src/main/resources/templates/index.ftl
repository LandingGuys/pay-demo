<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>



<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.bootcss.com/sweetalert/2.1.2/sweetalert.min.js"></script>
<script src="https://at.alicdn.com/t/font_1520996_ezq33u1hujd.js"></script>
<style type="text/css">
    .icon {
        width: 1em; height: 1em;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
        font-size: 50px;
        margin: 30px;
    }
</style>
<body>
<div id="" style="width: 500px">
    <form>
        <div class="form-group">
            <label for="OrderId">订单号</label>
            <input type="text" class="form-control" id="OrderId" placeholder="输入订单号" >
        </div>
        <div class="form-group">
            <label for="Amount">金额</label>
            <input type="text" class="form-control" id="Amount">
        </div>
        <div>
            <svg class="icon" aria-hidden="true"  onclick="weixin()">
                <use xlink:href="#icon-weixinzhifu-"></use>
            </svg>
            <svg class="icon" aria-hidden="true" onclick="ali()">
                <use xlink:href="#icon-zhifubaozhifu"></use>
            </svg>
        </div>

    </form>

</div>


<script type="text/javascript">


    function weixin() {
        var orderId=$("#OrderId").val();
        var amount=$("#Amount").val();
        var payType="WXPAY_NATIVE";
        if(!orderId){
            swal("问题标题不能为空~~~", "You clicked the button!", "warning");
            return;
        }
        if(!amount){
            swal("问题内容不能为空~~~", "You clicked the button!", "warning");
            return;
        }
        var link ="http://www.main.wast.club/pay/create" + '?orderId='+orderId + '&amount='+amount+'&payType='+payType;
        window.location.href=link;
    }
    function ali() {
        var orderId=$("#OrderId").val();
        var amount=$("#Amount").val();
        var payType="ALIPAY_PC";
        if(!orderId){
            swal("问题标题不能为空~~~", "You clicked the button!", "warning");
            return;
        }
        if(!amount){
            swal("问题内容不能为空~~~", "You clicked the button!", "warning");
            return;
        }
        var link ="http://www.main.wast.club/pay/create" + '?orderId='+orderId + '&amount='+amount+'&payType='+payType;
        window.location.href=link;
    }

</script>
</body>
</html>
