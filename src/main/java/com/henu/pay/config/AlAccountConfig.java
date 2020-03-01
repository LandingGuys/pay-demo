package com.henu.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lv
 * @date 2019-12-04 21:58
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlAccountConfig {
    private String AppId;
    private String PrivateKey;
    private String AliPayPublicKey;
    private String NotifyUrl;
    private String ReturnUrl;
}
