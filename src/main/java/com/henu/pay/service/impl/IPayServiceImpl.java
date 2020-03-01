package com.henu.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.henu.pay.enums.PayPlatformEnum;
import com.henu.pay.enums.ResponseEnum;
import com.henu.pay.exception.PayException;
import com.henu.pay.mapper.PayInfoMapper;
import com.henu.pay.pojo.PayInfo;
import com.henu.pay.pojo.PayInfoExample;
import com.henu.pay.service.IPayService;
import com.henu.pay.vo.ResponseVo;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lv
 * @date 2019-11-19 14:32
 */
@Service
@Slf4j
public class IPayServiceImpl implements IPayService {
    private final static String QUEUE_PAY_NOTIFY="payNotify";
    @Resource
    private BestPayService bestPayService;
    @Resource
    private PayInfoMapper payInfoMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建、发起支付
     * @param orderId
     * @param amount
     * @param bestPayTypeEnum
     * @return
     */
    @Override
    public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) {

        //写入数据库前先判断 该条数据是否存在
        PayInfoExample example = new PayInfoExample();
        example.createCriteria().andOrderNoEqualTo(Long.parseLong(orderId));
        List<PayInfo> payInfoList = payInfoMapper.selectByExample(example);
        //若存在
        if(payInfoList.size() > 0){
            PayInfo payInfoDb = payInfoList.get(0);
            if(payInfoDb.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())){
                throw new PayException();
            }
            payInfoDb.setUpdateTime(new Date());
            payInfoMapper.updateByPrimaryKeySelective(payInfoDb);
        }else {
            //不存在
            PayInfo payInfo=new PayInfo(Long.parseLong(orderId),
                    PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                    OrderStatusEnum.NOTPAY.name(),
                    amount);
            payInfoMapper.insertSelective(payInfo);
        }
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("8009396-智慧药房");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(bestPayTypeEnum);
        PayResponse response = bestPayService.pay(payRequest);
        log.info("支付response={}",response);
        return  response;
    }

    /**
     * 异步通知处理
     * @param notifyData
     * @return
     */
    @Override
    public String asyncNotify(String notifyData) {
       //1.签名校验
        PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.info("异步通知payResponse={}",payResponse);

        //2.金额校验（从数据库查订单）
        //告警
        PayInfoExample example = new PayInfoExample();
        example.createCriteria().andOrderNoEqualTo(Long.parseLong(payResponse.getOrderId()));
        List<PayInfo> payInfos = payInfoMapper.selectByExample(example);

        if(payInfos == null){
            throw new RuntimeException("通过orderNo查询支付订单为null");
        }
        //如果支付状态不是"已支付"
        if(!payInfos.get(0).getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())){
            if(payInfos.get(0).getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount()))!=0){
                //告警
                throw new RuntimeException("异步通知里的金额和数据库里的不一致；orderNo"+payResponse.getOrderId());
            }
            //3.修改订单支付状态
            payInfos.get(0).setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfos.get(0).setPlatformNumber(payResponse.getOutTradeNo());
            payInfos.get(0).setUpdateTime(null);
            payInfoMapper.updateByPrimaryKeySelective(payInfos.get(0));
        }else{
            throw new PayException();
        }
        //pay 发送MQ消息，mall 接受MQ消息
        rabbitTemplate.convertAndSend(QUEUE_PAY_NOTIFY, JSON.toJSONString(payInfos.get(0)));

        if(payResponse.getPayPlatformEnum()== BestPayPlatformEnum.WX){
            //4.告诉微信不再通知了
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if(payResponse.getPayPlatformEnum()==BestPayPlatformEnum.ALIPAY){
            return "success";
        }

        throw new RuntimeException("异步通知中错误的支付平台");
    }
    @Override
    public ResponseVo<PayInfo> queryByOrderId(String orderId) {
        PayInfoExample example = new PayInfoExample();
        example.createCriteria().andOrderNoEqualTo(Long.parseLong(orderId));
        List<PayInfo> payInfos = payInfoMapper.selectByExample(example);
        if(payInfos.size() > 0){
            return ResponseVo.success(payInfos.get(0));
        }
        return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
    }
}

