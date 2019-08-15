package cn.orange.core.validate.processor.impl;

import cn.orange.core.pojo.ValidateCode;
import cn.orange.core.validate.processor.AbstractValidateCodeProcessor;
import cn.orange.core.validate.sender.SmsCodeSender;
import lombok.Setter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author kz
 * @date 2019/8/11
 */
@Setter
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
            smsCodeSender.send(mobile, validateCode.getCode());
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
    }

}
