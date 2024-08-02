package com.spring.card.interfaceadapters.gateways;

import com.spring.card.entities.CardLimit;
import com.spring.card.frameworks.db.CardLimitRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CardLimitGateway {
    @Resource
    private CardLimitRepository repository;

    public CardLimit findByCardId(Integer cardId){
        return repository.findByCardId(cardId);
    }

    public CardLimit insert(CardLimit cardLimit){
        return repository.save(cardLimit);
    }

    public CardLimit update(CardLimit cardLimit){
        return repository.save(cardLimit);
    }

}
