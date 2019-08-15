package cn.orange.core.controller;

import cn.orange.core.validate.processor.ValidateCodeProcessor;
import cn.orange.core.validate.processor.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : kz
 * @date : 2019/8/8
 */
@Controller
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

}
