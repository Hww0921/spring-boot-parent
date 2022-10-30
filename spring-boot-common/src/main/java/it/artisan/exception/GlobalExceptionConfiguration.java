package it.artisan.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：mosence
 * @date ：2019/08/08
 */
public class GlobalExceptionConfiguration {

    /**
     * SERVLET web 默认错误配置
     */
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    /**
     * SERVLET web 默认错误配置
     */
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    public static class ReactiveExceptionConfiguration{
    }
}
