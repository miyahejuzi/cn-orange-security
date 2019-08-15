package cn.orange.core.validate.processor.impl;

import cn.orange.core.pojo.ImageCode;
import cn.orange.core.validate.processor.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author kz
 * @date 2019/8/11
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) {
        try {
            ImageIO.write(imageCode.getImageBuffer(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
