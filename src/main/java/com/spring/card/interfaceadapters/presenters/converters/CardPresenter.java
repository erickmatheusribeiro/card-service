package com.spring.card.interfaceadapters.presenters.converters;

import com.spring.card.entities.Card;
import com.spring.card.interfaceadapters.presenters.dto.CardDto;
import org.springframework.stereotype.Component;

@Component
public class CardPresenter {
    public CardDto mapToDto(Card card) {
        CardDto dto = new CardDto();

        dto.setCpf(card.getCpf());
        dto.setNumero(card.getCardNumber());
        dto.setData_validade(card.getExpirationDate());
        dto.setCvv(card.getCvv());

        return dto;
    }

    public Card mapToEntity(CardDto dto){
        Card entity = new Card();

        entity.setCpf(dto.getCpf());
        entity.setCardNumber(dto.getNumero());
        entity.setExpirationDate(dto.getData_validade());
        entity.setCvv(dto.getCvv());

        return entity;
    }
}
