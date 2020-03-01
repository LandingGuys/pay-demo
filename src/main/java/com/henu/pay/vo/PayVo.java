package com.henu.pay.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lv
 * @date 2020-02-29 20:48
 */
@Data
@Accessors(chain = true)
public class PayVo {

    private String codeUrl;

    private String  orderId;

    private String returnUrl;

    private String body;

}
