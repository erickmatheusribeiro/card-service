package com.spring.card.interfaceadapters.controllers;

import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import com.spring.card.usercase.CardTransactionUserCase;
import com.spring.card.util.MessageUtil;
import com.spring.card.util.exception.StandardError;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CardTransactionController {

    @Resource
    private CardTransactionUserCase business;

    public ResponseEntity<?> createTransaction(CardTransactionRequestDto dto) {
        if (Objects.isNull(dto.getCpf())
                || Objects.isNull(dto.getCardNumber())
                || Objects.isNull(dto.getExpirationDate())
                || Objects.isNull(dto.getCvv())
                || Objects.isNull(dto.getValue())) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardError(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "UNREPORTED_DATA_ERROR",
                            MessageUtil.getMessage("UNREPORTED_DATA_ERROR"),
                            "/api/cartao"
                    ));
        }

        return business.createTransaction(dto);

    }

}
