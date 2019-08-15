package cn.orange.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kz
 * @date 2019/8/11
 */
@Getter
@Setter
public class SmsCodeProperties {

    private int length = 5;

    private int expirationTime = 60;
}
