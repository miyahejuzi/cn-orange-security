package cn.orange.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : kz
 * @date : 2019/7/22
 */
@Component
public class MethodInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("url --> " + httpServletRequest.getRequestURI() + "  |  method --> "+((HandlerMethod) o).getMethod().getName());
        return true;
//        System.out.println(Thread.currentThread().getName() + "---preHandle-->" + httpServletRequest.getRequestURI());
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println(Thread.currentThread().getName() + "---postHandle-->" + httpServletRequest.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println(Thread.currentThread().getName() + "---afterCompletion-->" + httpServletRequest.getRequestURI());
    }
}
