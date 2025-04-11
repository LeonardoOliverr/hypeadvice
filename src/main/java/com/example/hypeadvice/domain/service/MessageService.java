package com.example.hypeadvice.domain.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String id, String... params) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, params, locale);
    }

    public String getMessageLocale(String id, String lang, Object... params) {
        Locale locale = StringUtils.isNotEmpty(lang) ? Locale.forLanguageTag(lang) : LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, params, locale);
    }
}
