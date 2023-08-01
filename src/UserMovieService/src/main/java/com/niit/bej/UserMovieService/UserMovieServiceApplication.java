package com.niit.bej.UserMovieService;

import com.niit.bej.UserMovieService.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class UserMovieServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMovieServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> registrationBean() {
        FilterRegistrationBean<JwtFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new JwtFilter());
        filterFilterRegistrationBean.addUrlPatterns("/userMovie/user/*");
        return filterFilterRegistrationBean;
    }
}