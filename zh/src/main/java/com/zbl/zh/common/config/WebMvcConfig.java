package com.zbl.zh.common.config;

// import java.util.List;

// import com.zbl.zh.common.core.H;
// import com.zbl.zh.common.core.I;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
// import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * 资源访问管理 WebMvcConfigurer
 * 
 * @Configuration 注解 No 'Access-Control-Allow-Origin' header is present on the
 *                requested resource. 配置后端跨域
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // // 注入
    // @Autowired
    // private I interceptor;

    // @Autowired
    // private H resolver;

    // /**
    //  * 注入解析器  参数解析器
    //  */
    // @Override
    // public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    //     argumentResolvers.add(resolver);
    // }


    // /**
    //  * 注入拦截器，后台登陆和权限判断，拦截器，所拦截的路径
    //  */
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(interceptor).addPathPatterns("/**")
    //     .excludePathPatterns("/user/login")  //放行
    //     .excludePathPatterns("/upload/**")
    //     .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");   
    // }

    // 打开跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedHeaders("*").allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS").maxAge(3600);

    }

    // // 静态资源路径映射
    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     // 将所有/static/** 访问都映射到classpath:/static/ 目录下
    //     registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/");
    // }

}