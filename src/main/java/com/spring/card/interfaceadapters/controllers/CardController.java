package com.spring.card.interfaceadapters.controllers;

import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.usercase.CardUserCase;
import com.spring.card.util.MessageUtil;
import com.spring.card.util.exception.ExceptionHandlerUtil;
import com.spring.card.util.exception.StandardError;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CardController {

    @Resource
    private CardUserCase business;

    @Resource
    private ExceptionHandlerUtil exceptionHandlerUtil;

    public ResponseEntity<?> insert(CardResquestDto cardDto){

        if (Objects.isNull(cardDto.getNumero())
            || Objects.isNull(cardDto.getCpf())
            || Objects.isNull(cardDto.getCvv())
            || Objects.isNull(cardDto.getData_validade())
            || Objects.isNull(cardDto.getLimite())) {
                return exceptionHandlerUtil.genericExceptions(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "UNREPORTED_DATA_ERROR");
        }
        return business.createCard(cardDto);
    }

    public ResponseEntity<?> findByCardNumber(String cpf, String numero, String data, String cvv){
        if(Objects.isNull(cpf)
                || Objects.isNull(numero)
                || Objects.isNull(data)
                || Objects.isNull(cvv)){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "UNREPORTED_DATA_ERROR");
        }
        return business.getLimitByCard(cpf, numero, data, cvv);
    }

}
