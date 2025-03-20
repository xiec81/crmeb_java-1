package com.zbkj.front.controller;

import com.zbkj.front.util.LocaleMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 国际化测试控制器
 */
@RestController
@RequestMapping("/api/locale")
public class LocaleController {

    @Autowired
    private LocaleMessageUtil localeMessageUtil;

    /**
     * 测试国际化支持
     * @return 当前语言的欢迎消息
     */
    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        
        // 获取当前语言编码
        String language = localeMessageUtil.getCurrentLanguage();
        
        // 获取对应语言的消息
        String welcome = localeMessageUtil.getMessage("common.welcome");
        String hello = localeMessageUtil.getMessage("common.hello");
        
        result.put("language", language);
        result.put("welcome", welcome);
        result.put("hello", hello);
        result.put("locale", LocaleContextHolder.getLocale().toString());
        
        return result;
    }
} 