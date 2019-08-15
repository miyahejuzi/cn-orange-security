package cn.orange.core.validate.generator.impl;

import cn.orange.core.validate.generator.ValidateCodeGenerator;
import cn.orange.core.pojo.ValidateCode;
import cn.orange.core.properties.CustomSecurityProperties;
import cn.orange.core.properties.SmsCodeProperties;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author kz
 * @date 2019/8/11
 */
@Setter
public class SmsCodeGenerator implements ValidateCodeGenerator {

    private CustomSecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        SmsCodeProperties smsCode = securityProperties.getValidateCode().getSmsCode();
        String randomNumeric = RandomStringUtils.randomNumeric(smsCode.getLength());
        int expirationTime = smsCode.getExpirationTime();
        return new ValidateCode(randomNumeric, expirationTime);
    }
}
