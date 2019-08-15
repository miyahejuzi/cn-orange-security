package cn.orange.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author : kz
 * @date : 2019/7/22
 */
public class TimerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("start timer filter..");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("To do something...");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
