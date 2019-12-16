package soucecode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import soucecode.interceptor.TestInterceptor;

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
}