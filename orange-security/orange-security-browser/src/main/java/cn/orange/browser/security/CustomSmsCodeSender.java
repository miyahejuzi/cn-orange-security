package cn.orange.browser.security;

import cn.orange.core.validate.sender.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author kz
 * @date 2019/8/12
 */
@Slf4j
@Component("smsCodeSender")
public class CustomSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info(mobile + "--" + code);
    }
}
