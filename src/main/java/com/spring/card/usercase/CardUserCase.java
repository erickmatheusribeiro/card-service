package com.spring.card.usercase;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardLimit;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import com.spring.card.interfaceadapters.gateways.CardLimitGateway;
import com.spring.card.interfaceadapters.presenters.converters.CardPresenter;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.interfaceadapters.presenters.dto.response.CardLimitResponseDto;
import com.spring.card.util.CpfValidator;
import com.spring.card.util.ExpirationDateValidator;
import com.spring.card.util.exception.ExceptionHandlerUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class CardUserCase {
    @Autowired
    private Clock clock;

    @Resource
    private CardGateway cardGateway;

    @Resource
    private CardLimitGateway cardLimitGateway;

    @Resource
    private CardPresenter presenter;

    @Resource
    private ExceptionHandlerUtil exceptionHandlerUtil;

    public ResponseEntity<?> createCard(CardResquestDto dto){
        Card card = cardGateway.findByCardNumber(dto.getNumero());

        if(card != null
                && card.getCardNumber().equals(dto.getNumero())
                && card.getCpf().equals(dto.getCpf())){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "CARD_DUPLICATE");
        }

        if(dto.getLimite() <= 0){
            return exceptionHandlerUtil.genericExceptions(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "CARD_LIMIT_NOT_FOUND");
        }

        if(cardGateway.countByCpf(dto.getCpf()) > 1){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.FORBIDDEN,
                    "CARD_CPF_EXCEEDED");
        }
        if(dto.getNumero().length() != 16){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "INVALID_LENGTH_CARD_NUMBER");
        }
        if(dto.getData_validade().length() != 4){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "INVALID_EXPIRATION_DATE");
        }
        if(!isExpirationDateValid(dto.getData_validade())){
            return exceptionHandlerUtil.genericExceptions(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_EXPIRATION_DATE");
        }
        if(!isCpfValid(dto.getCpf())){
            return exceptionHandlerUtil.genericExceptions(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_CPF");
        }

        Card cardEntity = new Card();

        cardEntity.setCpf(dto.getCpf());
        cardEntity.setCardNumber(dto.getNumero());
        cardEntity.setExpirationDate(dto.getData_validade());
        cardEntity.setCvv(dto.getCvv());

        cardEntity.setDateTimeCreated(LocalDateTime.now(clock));

        cardEntity = cardGateway.insert(cardEntity);

        createCardLimit(cardEntity, dto.getLimite());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(presenter.mapToDto(cardEntity));
    }

    public void createCardLimit(Card entity, double cardLimit){
        CardLimit cardLimitEntity = new CardLimit();

        cardLimitEntity.setCard(entity);
        cardLimitEntity.setLimite(cardLimit);
        cardLimitEntity.setUpdated(LocalDateTime.now(clock));

        cardLimitGateway.insert(cardLimitEntity);
    }

    public ResponseEntity<?> getLimitByCard(String cpf, String numero, String data, String cvv)  {
        Card card = cardGateway.findByCpfAndCardNumber(cpf,numero);

        if(card == null
            || !card.getCvv().equals(cvv)
            || !card.getExpirationDate().equals(data)){
            return exceptionHandlerUtil.genericExceptions(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "CARD_NOT_FOUND");
        }

        CardLimit cardLimit = cardLimitGateway.findByCardId(card.getId());

        return ResponseEntity.ok().body(new CardLimitResponseDto(cardLimit));
    }

    public boolean isCpfValid(String cpf){
        return CpfValidator.isValid(cpf);
    }

    public boolean isExpirationDateValid(String expirationDate){
        return ExpirationDateValidator.isValid(expirationDate);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
