package cn.orange.core.validate.processor;

import cn.orange.core.ValidateBeanConfig;
import cn.orange.core.enums.ValidateCodeType;
import cn.orange.core.exception.ValidateCodeException;
import cn.orange.core.pojo.ValidateCode;
import cn.orange.core.repository.ValidateCodeRepository;
import cn.orange.core.validate.generator.ValidateCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author kz
 * @date 2019/8/11
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * java 的假泛型, 这个 C 根本就不能被判断是什么类型, 所以 + 的 SuppressWarnings
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type +  ValidateBeanConfig.BEAN_NAME_SUFFIX;
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器 validateCodeGenerator " + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType());
    }

    /**
     * 自定义发送验证码给前台的行为
     *
     * @param request      ServletWebRequest
     * @param validateCode Object
     */
    protected abstract void send(ServletWebRequest request, C validateCode);

    /**
     * 向 ioc 注入 bean 的时候, name 是有规则的
     * 根据实现的类判断执行 image sms
     * 也就是获得 bean_name 的前缀
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), ValidateCodeProcessor.CODE_SUFFIX);
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType codeType = getValidateCodeType();

        @SuppressWarnings("unchecked")
        C codeInSession = (C) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExprie()) {
            validateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, codeType);

    }
}
