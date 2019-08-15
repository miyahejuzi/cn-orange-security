package cn.orange.browser.controller;

import cn.orange.browser.core.Result;
import cn.orange.core.constant.Constant;
import cn.orange.core.properties.CustomSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于测试 spring security 的一个控制器
 *
 * @author : kz
 * @date : 2019/7/30
 */
@Slf4j
@Controller
public class SecurityController {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private CustomSecurityProperties securityProperties;

    /**
     * 为了抽取 security 的配置, 成为以一个单独的模块
     * 把所有的需要权限验证的请求（原本是直接跳转到登陆页面的）经过这个方法处理
     * 主要是判断是什么类型（json/html)的请求，根据不同的配置返回不同的东西；
     *
     * @param request  request
     * @param response response
     * @return result
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("/authentication/login")
    public Result requireAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // session 过期, 则获取不到 savedRequest
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是:" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                try {
                    redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Result("Need to identity authentication");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("/session/invalid")
    public Result sessionInvalid() {
        return new Result("session invalid ");
    }

    /**
     * 首页 所有人都可以访问
     */
    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }

    /**
     * 登录页 所有人可都可以访问
     * 只是做了一个登录页跳转，但是具体登录的验证逻辑却没有，这是因为Spring Security要求使用者将此块的功能必须委托给它来处理
     * 也就是 spring security 会拦截 /login + post 请求
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 欢迎页 登录后的用户都可以访问
     */
    @PreAuthorize("hasAnyRole('user')")
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 管理页 只有管理员可访问
     */
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    /**
     * 无权限提醒页 当一个用户的访问没有权限时，跳转到该页
     */
    @GetMapping("/403")
    public String error403() {
        return "403";
    }

}
