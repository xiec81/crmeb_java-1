package com.zbkj.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 国际化配置
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    // 支持的语言列表
    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("zh"),
            new Locale("es"),
            new Locale("pt")
    );

    // 默认语言为英文
    private static final Locale DEFAULT_LOCALE = new Locale("en");

    /**
     * 配置解析请求头中的语言信息
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setSupportedLocales(SUPPORTED_LOCALES);
        localeResolver.setDefaultLocale(DEFAULT_LOCALE);
        return localeResolver;
    }

    /**
     * 配置国际化资源
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 指定国际化资源文件路径
        messageSource.setBasenames("i18n/messages");
        // 指定默认编码
        messageSource.setDefaultEncoding("UTF-8");
        // 设置缓存时间（秒）
        messageSource.setCacheMillis(3600);
        return messageSource;
    }
} 