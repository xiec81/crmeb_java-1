package com.zbkj.front.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化消息工具类
 */
@Component
public class LocaleMessageUtil {

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取国际化消息
     * @param code 消息编码
     * @return 对应语言的消息
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * 获取国际化消息
     * @param code 消息编码
     * @param args 参数
     * @return 对应语言的消息
     */
    public String getMessage(String code, Object[] args) {
        // 获取当前请求的语言环境
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }

    /**
     * 获取当前语言代码
     * @return 语言代码（小写，例如：en, zh, es, pt）
     */
    public String getCurrentLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }
} 