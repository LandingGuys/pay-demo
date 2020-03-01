package com.henu.pay.request;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author lv
 * @date 2020-02-29 17:22
 */
@Data
public class PayMentRequest {
    @NotBlank
    private String orderId;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private BestPayTypeEnum payType;
}
