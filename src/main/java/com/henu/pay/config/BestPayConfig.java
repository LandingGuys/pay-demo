package com.henu.pay.config;

import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author lv
 * @date 2019-11-19 17:15
 */
@Component
public class BestPayConfig {
    @Autowired
    private WxAccountConfig wxAccountConfig;
    @Autowired
    private AlAccountConfig alAccountConfig;
    @Bean
    public BestPayService bestPayService(){
        WxPayConfig wxPayConfig=new WxPayConfig();

        wxPayConfig.setAppId(wxAccountConfig.getAppId());
        wxPayConfig.setMchId(wxAccountConfig.getMchId());
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey());
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
        wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());

        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(alAccountConfig.getAppId());
        aliPayConfig.setPrivateKey(alAccountConfig.getPrivateKey());
        aliPayConfig.setAliPayPublicKey(alAccountConfig.getAliPayPublicKey());
        aliPayConfig.setNotifyUrl(alAccountConfig.getNotifyUrl());
        aliPayConfig.setReturnUrl(alAccountConfig.getReturnUrl());

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        bestPayService.setAliPayConfig(aliPayConfig);
        return bestPayService;
    }


}
