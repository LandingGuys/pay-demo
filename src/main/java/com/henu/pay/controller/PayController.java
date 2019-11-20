package com.henu.pay.controller;

import com.henu.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lv
 * @date 2019-11-19 15:20
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IPayService iPayService;
    @GetMapping("/")
    public String index(){
        return "index";
    }


    @GetMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType")BestPayTypeEnum bestPayTypeEnum,
                            Model model){
        PayResponse response = iPayService.create(orderId, amount,bestPayTypeEnum);
        //支付方式不同，渲染就不同，WXPAY_NATIVE使用codeUrl,ALIPAY_PC使用body

//        Map<String,String> map=new HashMap<>();
        if(bestPayTypeEnum==BestPayTypeEnum.WXPAY_NATIVE){
//            map.put("codeUrl",response.getCodeUrl());
            model.addAttribute("codeUrl",response.getCodeUrl());

            return "createForWxNative";
        }else if(bestPayTypeEnum==BestPayTypeEnum.ALIPAY_PC){
            model.addAttribute("body",response.getBody());
//            map.put("body",response.getBody());
//            return new ModelAndView("createForAliPc",response.getBody());
            return "createForAliPc";
        }

        throw new RuntimeException("暂不支持的支付类型");
    }
    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData){
        return iPayService.asyncNotify(notifyData);
    }
}
