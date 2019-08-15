package cn.orange.core.properties;

import lombok.Data;

/**
 * @author kz
 * @date 2019/8/10
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties imageCode = new ImageCodeProperties();

    private SmsCodeProperties smsCode = new SmsCodeProperties();

}
