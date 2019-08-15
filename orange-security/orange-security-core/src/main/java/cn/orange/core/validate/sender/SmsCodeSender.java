package cn.orange.core.validate.sender;

/**
 * @author kz
 * @date 2019/8/11
 */
public interface SmsCodeSender {

    void send(String mobile, String code);
}
