package cn.orange.core.pojo;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author kz
 * @date 2019/8/9
 */
@Setter
@Getter
public class ImageCode extends ValidateCode {

    private BufferedImage imageBuffer;

    public ImageCode(BufferedImage imageBuffer, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.imageBuffer = imageBuffer;
    }

    public ImageCode(BufferedImage imageBuffer, String code, int expireTime) {
        super(code, expireTime);
        this.imageBuffer = imageBuffer;
    }
}
