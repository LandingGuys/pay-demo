# pay-demo
微信，支付宝 PC 端支付 demo


#微信配置 
         ```java
         WxPayConfig wxPayConfig=new WxPayConfig();
         wxPayConfig.setAppId("**********");
         wxPayConfig.setMchId("********");
         wxPayConfig.setMchKey("**********************");
         wxPayConfig.setNotifyUrl("*********");
         ```
#支付宝配置
        ```java
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId("***************");
        aliPayConfig.setPrivateKey("********")
        aliPayConfig.setAliPayPublicKey("***********************");
        aliPayConfig.setNotifyUrl("*******");
        aliPayConfig.setReturnUrl("******************");```
 #发起支付
        ```java
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("订单名称");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(bestPayTypeEnum);
        PayResponse response = bestPayService.pay(payRequest);```
 测试：<http://www.main.wast.club>
