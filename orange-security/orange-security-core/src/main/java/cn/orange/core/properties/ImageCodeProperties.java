package cn.orange.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kz
 * @date 2019/8/10
 */
@Setter
@Getter
public class ImageCodeProperties extends SmsCodeProperties {

    private int height = 30;

    private int width = 70;

    public ImageCodeProperties() {
        setLength(4);
    }

}
