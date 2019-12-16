package soucecode.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBoot2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2Application.class, args);
    }

    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { return application.sources(SpringBoot2Application.class); }

}
