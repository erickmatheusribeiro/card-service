package com.spring.card.util.exception;

import com.spring.card.util.MessageUtil;

public class BusinessException extends Exception {

    public BusinessException(String code) {
        super(MessageUtil.getMessage(code));
    }
}

