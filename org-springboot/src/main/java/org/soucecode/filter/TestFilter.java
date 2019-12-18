package org.soucecode.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author cnxk
 * @Date 2019/12/16
 * @Version V1.0.0
 **/
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter的doFilter方法");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
