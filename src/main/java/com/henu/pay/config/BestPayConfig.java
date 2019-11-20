package com.henu.pay.config;

import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author lv
 * @date 2019-11-19 17:15
 */
@Component
public class BestPayConfig {

    @Bean
    public BestPayService bestPayService(){
        WxPayConfig wxPayConfig=new WxPayConfig();
        wxPayConfig.setAppId("**********");
        wxPayConfig.setMchId("********");
        wxPayConfig.setMchKey("**********************");
        wxPayConfig.setNotifyUrl("*********");

        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId("***************");
        aliPayConfig.setPrivateKey("********")
        aliPayConfig.setAliPayPublicKey("***********************");
        aliPayConfig.setNotifyUrl("*******");
        aliPayConfig.setReturnUrl("******************");

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        bestPayService.setAliPayConfig(aliPayConfig);
        return bestPayService;
    }

}
