package cn.orange.core.validate.generator.impl;

import cn.orange.core.validate.generator.ValidateCodeGenerator;
import cn.orange.core.pojo.ImageCode;
import cn.orange.core.pojo.ValidateCode;
import cn.orange.core.properties.CustomSecurityProperties;
import cn.orange.core.properties.ImageCodeProperties;
import cn.orange.core.utils.VerificationCodeUtil;
import lombok.Setter;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author kz
 * @date 2019/8/10
 */
@Setter
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private CustomSecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        ImageCodeProperties codeProperties = securityProperties.getValidateCode().getImageCode();
        VerificationCodeUtil code = new VerificationCodeUtil(codeProperties.getWidth(), codeProperties.getHeight(), codeProperties.getLength());
        return new ImageCode(code.bufferedImageOutputStream(), code.getVerificationCode(), codeProperties.getExpirationTime());
    }
}
