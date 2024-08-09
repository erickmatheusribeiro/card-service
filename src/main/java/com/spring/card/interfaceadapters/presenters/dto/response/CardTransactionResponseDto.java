package com.spring.card.interfaceadapters.presenters.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.card.cards.CardTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CardTransactionResponseDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private double value;
    @JsonProperty("created")
    private String created;

    public CardTransactionResponseDto(CardTransaction entity) {
        this.id = entity.getId().toString();
        this.value = entity.getValue();
        this.created = entity.getDateTimeCreated().toString();
    }

}
