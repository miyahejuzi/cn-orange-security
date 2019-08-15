package cn.orange.browser.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author kz
 * @date 2019/8/10
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
