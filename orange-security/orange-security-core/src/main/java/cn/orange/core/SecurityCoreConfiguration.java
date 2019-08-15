package cn.orange.core;

import cn.orange.core.properties.CustomSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : kz
 * @date : 2019/8/4
 */
@Configuration
@EnableConfigurationProperties({CustomSecurityProperties.class})
public class SecurityCoreConfiguration {
}
