package com.spring.card.interfaceadapters.presenters.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.card.cards.CardTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CardTransactionResponseDto {
    private String id;
    private double value;
    private String created;

    public CardTransactionResponseDto(CardTransaction entity) {
        this.id = entity.getId().toString();
        this.value = entity.getValue();
        this.created = entity.getDateTimeCreated().toString();
    }

}
