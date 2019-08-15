package cn.orange.core.properties;

import cn.orange.core.enums.LoginType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : kz
 * @date : 2019/8/4
 */
@Data
@ConfigurationProperties(prefix = "custom.security")
public class CustomSecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties validateCode = new ValidateCodeProperties();

    private LoginType loginType = LoginType.JSON;
}





















