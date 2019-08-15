package cn.orange.core.validate.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author kz
 * @date 2019/8/11
 */
public interface ValidateCodeProcessor {

    String CODE_SUFFIX = "ValidateCodeProcessor";

    /**
     * 执行验证码的全部流程, 生成 - 保存 - 发送
     * @param request request & response
     */
    void create(ServletWebRequest request);

    /**
     * 执行校验验证码的正确
     * @param request request & response
     */
    void validate(ServletWebRequest request);

}
