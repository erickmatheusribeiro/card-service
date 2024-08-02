package com.spring.card.interfaceadapters.gateways;

import com.spring.card.cards.Card;
import com.spring.card.frameworks.db.CardRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CardGateway {
    @Resource
    private CardRepository repository;

    public Card findByCardNumber(String cardNumber){
        return repository.findByCardNumber(cardNumber);
    }

    public Card findByCpfAndCardNumber(String cpf, String cardNumber){
        return repository.findByCpfAndCardNumber(cpf, cardNumber);
    }

    public Card insert(Card card){
        return repository.save(card);
    }

    public Card update(Card card){
        return repository.save(card);
    }

    public long countByCpf(String cpf){
        return repository.countByCpf(cpf);
    }
}
