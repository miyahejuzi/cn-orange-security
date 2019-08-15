package cn.orange.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : kz
 * @date : 2019/7/22
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserException.class)
    public Map<String, Object> userExceptionHandler(UserException ue) {
        return new HashMap<String, Object>(16) {{
            this.put("message", ue.getMessage());
        }};
    }



}
