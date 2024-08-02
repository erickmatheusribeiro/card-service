package com.spring.card.util.exception;

import com.spring.card.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerUtil {

    public ResponseEntity<?> genericExceptions(HttpStatus httpStatus, String message){
        return ResponseEntity
                .status(httpStatus)
                .body(new StandardError(
                        httpStatus.value(),
                        message,
                        MessageUtil.getMessage(message),
                        "/api/cartao"
                ));
    }
}
