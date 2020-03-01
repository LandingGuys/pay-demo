package com.henu.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lv
 * @date 2019-12-04 21:51
 */
@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxAccountConfig {
    private String AppId;
    private String MchId;
    private String MchKey;
    private String NotifyUrl;
    private String ReturnUrl;
}
