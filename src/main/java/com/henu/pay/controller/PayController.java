package com.henu.pay.controller;

import com.henu.pay.config.WxAccountConfig;
import com.henu.pay.pojo.PayInfo;
import com.henu.pay.request.PayMentRequest;
import com.henu.pay.service.IPayService;
import com.henu.pay.vo.PayVo;
import com.henu.pay.vo.ResponseVo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * @author lv
 * @date 2019-11-19 15:20
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Resource
    private IPayService iPayService;
    @Resource
    private WxAccountConfig wxAccountConfig;
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/test")
    public String indexTest(){
        return "indexTest";
    }

    @GetMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                         @RequestParam("amount") BigDecimal amount,
                         @RequestParam("payType")BestPayTypeEnum bestPayTypeEnum,Model model){
        PayResponse response = iPayService.create(orderId, amount,bestPayTypeEnum);
        //支付方式不同，渲染就不同，WXPAY_NATIVE使用codeUrl,ALIPAY_PC使用body
        if(bestPayTypeEnum.equals(BestPayTypeEnum.WXPAY_NATIVE)){
            //微信支付
            model.addAttribute("codeUrl",response.getCodeUrl());
            model.addAttribute("orderId",orderId);
            model.addAttribute("returnUrl",wxAccountConfig.getReturnUrl());
            return "createForWxNative";
        }else if(bestPayTypeEnum.equals(BestPayTypeEnum.ALIPAY_PC)){
            //支付宝支付
            model.addAttribute("body",response.getBody());
            return "createForAliPc";
        }
        throw new RuntimeException("暂不支持的支付类型");
    }
    @PostMapping("/create")
    @ResponseBody
    public ResponseVo create(@RequestBody PayMentRequest payMentRequest){
        PayResponse response = iPayService.create(payMentRequest.getOrderId(), payMentRequest.getAmount(),payMentRequest.getPayType());
        //支付方式不同，渲染就不同，WXPAY_NATIVE使用codeUrl,ALIPAY_PC使用body
        if(payMentRequest.getPayType().equals(BestPayTypeEnum.WXPAY_NATIVE)){
            //微信支付
            PayVo weiXinVo = new PayVo();
            weiXinVo.setCodeUrl(response.getCodeUrl())
                    .setOrderId(payMentRequest.getOrderId())
                    .setReturnUrl(wxAccountConfig.getReturnUrl());
            return ResponseVo.success(weiXinVo);
        }else if(payMentRequest.getPayType().equals(BestPayTypeEnum.ALIPAY_PC)){
            //支付宝支付
            PayVo aLiVo =new PayVo();
            aLiVo.setBody(response.getBody());
            return ResponseVo.success(aLiVo);
        }
        throw new RuntimeException("暂不支持的支付类型");
    }
    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData){
        log.info(notifyData);
        return iPayService.asyncNotify(notifyData);
    }
    @GetMapping("/queryByOrderId")
    @ResponseBody
    public ResponseVo<PayInfo> queryByOrderId(@RequestParam String orderId){
        return iPayService.queryByOrderId(orderId);
    }
}
