package com.henu.pay.enums;

import lombok.Getter;

/**
 * @author lv
 * @date 2020-02-20 19:35
 */
@Getter
public enum  ResponseEnum {
    SUCCESS(0,"成功"),
    PAY_ORDER_NO_REPEAT(50,"订单已支付过"),
    ORDER_NOT_EXIST(60,"订单不存在")
    ;
    private Integer code;
    private String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
