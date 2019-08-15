package cn.orange.core.validate.generator;

import cn.orange.core.pojo.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码的生成器
 *
 * @author kz
 * @date 2019/8/10
 */
public interface ValidateCodeGenerator {

    /**
     * 生成图片验证码的方法接口, 用于可配置个性化的图片验证码的生成逻辑
     *
     * @param request request
     * @return imageCode
     */
    ValidateCode generate(ServletWebRequest request);

}
