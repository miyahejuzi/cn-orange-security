package cn.orange.browser.security;

import cn.orange.browser.exception.ValidateCodeException;
import cn.orange.core.enums.ValidateCodeType;
import cn.orange.core.pojo.ValidateCode;
import cn.orange.core.repository.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author kz
 * @date 2019/8/10
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private static final String LOGIN_URL = "/login/authentication";

    private static final String LOGIN_METHOD = "POST";

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURI().startsWith(LOGIN_URL) && Objects.equals(LOGIN_METHOD, httpServletRequest.getMethod())) {
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException ve) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, ve);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validate(ServletWebRequest request) {
        String last = StringUtils.substringAfterLast(request.getRequest().getRequestURI(), "/").toUpperCase();
        ValidateCode validateCode = repository.get(request, ValidateCodeType.valueOf(last));
        if (validateCode != null) {
            try {
                String stringParameter = ServletRequestUtils.getStringParameter(request.getRequest(), last.toLowerCase());
                if (Objects.equals(validateCode.getCode(), stringParameter)) {
                    repository.remove(request, ValidateCodeType.valueOf(last));
                } else {
                    throw new ValidateCodeException("验证码错误");
                }
            } catch (ServletRequestBindingException e) {
                e.printStackTrace();
            }
        }
    }
}
