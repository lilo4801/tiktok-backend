package com.example.tiktok.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LanguageUtils {

    static MessageSource messageSource;

    public LanguageUtils(MessageSource messageSource) {
        this.messageSource= messageSource;
    }

    //  default language is english
    // when changing language ,  header includes accept-language
    public static String getMessage(String key) {
        String outMessage = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        if (TextUtils.isEmpty(outMessage)) {
            outMessage = messageSource.getMessage("message.error", null,  LocaleContextHolder.getLocale());
        }
        return outMessage;
    }

}