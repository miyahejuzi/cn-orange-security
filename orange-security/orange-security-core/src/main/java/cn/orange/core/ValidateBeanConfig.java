package cn.orange.core;

import cn.orange.core.properties.CustomSecurityProperties;
import cn.orange.core.validate.generator.ValidateCodeGenerator;
import cn.orange.core.validate.generator.impl.ImageCodeGenerator;
import cn.orange.core.validate.generator.impl.SmsCodeGenerator;
import cn.orange.core.validate.processor.ValidateCodeProcessor;
import cn.orange.core.validate.processor.impl.ImageValidateCodeProcessor;
import cn.orange.core.validate.processor.impl.SmsValidateCodeProcessor;
import cn.orange.core.validate.sender.SmsCodeSender;
import cn.orange.core.validate.sender.impl.DefaultSmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kz
 * @date 2019/8/10
 */
@Configuration
public class ValidateBeanConfig {

    private static final String IMAGE_PREFIX = "image";

    private static final String SMS_PREFIX = "sms";

    public static final String BEAN_NAME_SUFFIX = "CodeGenerator";

    private static final String CODE_SUFFIX = ValidateCodeProcessor.CODE_SUFFIX;

    /**
     * 图片验证码的生成器
     */
    @Bean(name = IMAGE_PREFIX + BEAN_NAME_SUFFIX)
    @ConditionalOnMissingBean(name = IMAGE_PREFIX + BEAN_NAME_SUFFIX)
    public ImageCodeGenerator imageCodeGenerator(CustomSecurityProperties securityProperties) {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    /**
     * 短信验证码的生成器
     */
    @Bean(name = SMS_PREFIX + BEAN_NAME_SUFFIX)
    @ConditionalOnMissingBean(name = SMS_PREFIX + BEAN_NAME_SUFFIX)
    public SmsCodeGenerator smsCodeGenerator(CustomSecurityProperties securityProperties) {
        SmsCodeGenerator codeGenerator = new SmsCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 图片验证码的提供者
     */
    @Bean(name = IMAGE_PREFIX + CODE_SUFFIX)
    @ConditionalOnMissingBean(name = IMAGE_PREFIX + CODE_SUFFIX)
    public ImageValidateCodeProcessor imageValidateCodeProcessor() {
        return new ImageValidateCodeProcessor();
    }

    /**
     * 短信验证码的提供者
     */
    @Bean(name = SMS_PREFIX + CODE_SUFFIX)
    @ConditionalOnMissingBean(name = SMS_PREFIX + CODE_SUFFIX)
    public SmsValidateCodeProcessor smsValidateCodeProcessor(SmsCodeSender smsCodeSender) {
        SmsValidateCodeProcessor processor = new SmsValidateCodeProcessor();
        processor.setSmsCodeSender(smsCodeSender);
        return processor;
    }

    /**
     * 短信验证码的发送器
     */
    @Bean(name = "smsCodeSender")
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}











