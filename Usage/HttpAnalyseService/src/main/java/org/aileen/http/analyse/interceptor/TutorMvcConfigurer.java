package org.aileen.http.analyse.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/6
 * {@code @project} TuTorSelenium
 */
@Configuration
public class TutorMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有的请求都进行拦截处理
        registry.addInterceptor(new RequestInterceptor())
                .addPathPatterns("/**");
    }
}

//1、addPathPatterns方法定义拦截的地址
//2、excludePathPatterns定义排除某些地址不被拦截
//3、添加的一个拦截器没有addPathPattern任何一个url则默认拦截所有请求
//4、如果没有excludePathPatterns任何一个请求，则默认不放过任何一个请求。
