package org.soucecode.config;

import org.soucecode.servlet.TestServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.soucecode.filter.TestFilter;
import org.soucecode.interceptor.TestInterceptor;

/**
 * @Author cnxk
 * @Date 2019/12/16
 * @Version V1.0.0
 **/
@Configuration
public class TestConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private TestInterceptor testInterceptor;

    /**
     * 注册拦截器 * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testInterceptor).addPathPatterns("/**").excludePathPatterns("/index.html", "/");
    }

    /**
     * 注册一个filter * @return
     */
    @Bean
    public FilterRegistrationBean testFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new TestFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean testServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new TestServlet());
        return servletRegistrationBean;
    }

    /**
     * 请求视图映射
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login.html");
    }


}