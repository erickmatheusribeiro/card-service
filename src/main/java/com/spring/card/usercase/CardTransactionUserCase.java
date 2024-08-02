package com.spring.card.usercase;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardLimit;
import com.spring.card.cards.CardTransaction;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import com.spring.card.interfaceadapters.gateways.CardLimitGateway;
import com.spring.card.interfaceadapters.gateways.CardTransactionGateway;
import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import com.spring.card.interfaceadapters.presenters.dto.response.CardTransactionResponseDto;
import com.spring.card.util.exception.ExceptionHandlerUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class CardTransactionUserCase {
    @Autowired
    private Clock clock;

    @Resource
    private CardGateway cardGateway;

    @Resource
    private CardLimitGateway cardLimitGateway;

    @Resource
    private CardTransactionGateway cardTransactionGateway;

    @Resource
    private ExceptionHandlerUtil exceptionHandlerUtil;

    public ResponseEntity<?> createTransaction(CardTransactionRequestDto dto){

        Card card = cardGateway.findByCpfAndCardNumber(dto.getCpf(),dto.getCardNumber());

        if(card == null
                || !card.getCvv().equals(dto.getCvv())
                || !card.getExpirationDate().equals(dto.getExpirationDate())){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "CARD_NOT_FOUND");
        }

        CardLimit cardLimit = cardLimitGateway.findByCardId(card.getId());

        if(cardLimit.getLimite() <= 0
                || cardLimit.getLimite() < dto.getValue()){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "INSUFFICIENT_CARD_LIMIT");
        }

        CardTransaction entity = new CardTransaction();

        entity.setCard(card);
        entity.setPaymentId(dto.getPaymentId());
        entity.setValue(dto.getValue());
        entity.setDateTimeCreated(LocalDateTime.now(clock));

        cardLimit.setLimite(cardLimit.getLimite() + dto.getValue());
        cardLimitGateway.insert(cardLimit);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CardTransactionResponseDto(cardTransactionGateway.createTransaction(entity)));
    }
}
