package com.henu.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author lv
 * @date 2019-11-19 14:24
 */
public interface IPayService {

    /**
     * 创建/发起支付
     */
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     * @param notifyData
     * @return
     */
    String asyncNotify(String notifyData);
}
