package com.henu.pay.service;

import com.henu.pay.pojo.PayInfo;
import com.henu.pay.vo.ResponseVo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
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
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     * @param notifyData
     * @return
     */
    String asyncNotify(String notifyData);

    /**
     * 查询支付记录（通过订单号）
     * @param orderId
     * @return
     */
    ResponseVo<PayInfo> queryByOrderId(String orderId);
}
