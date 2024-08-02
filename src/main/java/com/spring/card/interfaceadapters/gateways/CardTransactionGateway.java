package com.spring.card.interfaceadapters.gateways;

import com.spring.card.cards.CardTransaction;
import com.spring.card.frameworks.db.CardTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardTransactionGateway {

    @Autowired
    private CardTransactionRepository repository;

    public CardTransaction createTransaction(CardTransaction entity) {
        return repository.save(entity);
    }
}
