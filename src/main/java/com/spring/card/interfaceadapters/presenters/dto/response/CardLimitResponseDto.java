package com.spring.card.interfaceadapters.presenters.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.card.cards.CardLimit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CardLimitResponseDto {
    private double limit;
    private LocalDateTime updated;

    public CardLimitResponseDto(CardLimit entity) {
        this.limit = entity.getLimite();
        this.updated = entity.getUpdated();
    }
}
