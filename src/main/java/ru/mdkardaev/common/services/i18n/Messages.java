package ru.mdkardaev.common.services.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class Messages {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code, Object... args){
        return messageSource.getMessage(code, args, new Locale("RU"));
    }
}
