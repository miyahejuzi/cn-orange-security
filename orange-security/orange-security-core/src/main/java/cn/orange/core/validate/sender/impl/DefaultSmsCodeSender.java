package cn.orange.core.validate.sender.impl;

import cn.orange.core.validate.sender.SmsCodeSender;

/**
 * @author kz
 * @date 2019/8/11
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("===> 向 " + mobile + "发送验证码 : " + code);
    }
}
