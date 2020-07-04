package com.example.demo.config;

import com.example.demo.component.LoginHandlerInterceptor;
import com.example.demo.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

//使用WebMvcConfigurer可以用来扩展SpringMVC的功能
//@EnableWebMvc全面接管SpringMVC
@EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       //浏览器发送test请求，会来到success页面
        registry.addViewController("/test").setViewName("success");
    }
    /*
    1.引入thymeleaf的依赖包（在pom.xml文件中加入）
    2.static里面放所有的静态资源，template放页面。这两个属于同一级，都位于resource下面。就会自动转化为resource根路径，中间不需要加static，直接接后面的文件路径
     */

    //静态资源放行方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }



    //所有的WebMvcConfigurer组件都会一起起作用
    //使用组件注册到容器中
    @Bean
    public WebMvcConfigurer myWebMvcConfigurer(){
        WebMvcConfigurer mvcConfigurer=new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login.html","/user/login","/");

            }
        };
        return mvcConfigurer;
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }


}
