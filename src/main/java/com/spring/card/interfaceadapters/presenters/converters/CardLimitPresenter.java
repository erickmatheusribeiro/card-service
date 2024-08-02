package com.spring.card.interfaceadapters.presenters.converters;

import com.spring.card.entities.CardLimit;
import com.spring.card.interfaceadapters.presenters.dto.response.CardLimitResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CardLimitPresenter {

    public CardLimitResponseDto mapToDto(CardLimit entity){
        CardLimitResponseDto responseDto = new CardLimitResponseDto();

        responseDto.setLimit(entity.getLimite());
        responseDto.setUpdated(entity.getUpdated());

        return responseDto;
    }

    public CardLimit mapToEntity(CardLimitResponseDto dto){
        CardLimit entity = new CardLimit();

        entity.setLimite(dto.getLimit());
        entity.setUpdated(dto.getUpdated());

        return entity;
    }


}
