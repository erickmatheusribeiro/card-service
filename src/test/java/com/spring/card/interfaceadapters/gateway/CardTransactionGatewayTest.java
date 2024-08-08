package com.spring.card.interfaceadapters.gateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardTransaction;
import com.spring.card.frameworks.db.CardTransactionRepository;
import com.spring.card.interfaceadapters.gateways.CardTransactionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardTransactionGatewayTest {

    @InjectMocks
    private CardTransactionGateway cardTransactionGateway;

    @Mock
    private CardTransactionRepository cardTransactionRepository;

    private CardTransaction cardTransaction;
    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(123);
        // Configure other fields of Card as necessary

        cardTransaction = new CardTransaction();
        cardTransaction.setId(1);
        cardTransaction.setValue(1000.0);
        cardTransaction.setDateTimeCreated(LocalDateTime.now());
        cardTransaction.setCard(card);
    }

    @Test
    void testCreateTransaction() {
        when(cardTransactionRepository.save(cardTransaction)).thenReturn(cardTransaction);

        CardTransaction savedCardTransaction = cardTransactionGateway.createTransaction(cardTransaction);

        assertEquals(cardTransaction, savedCardTransaction);
        verify(cardTransactionRepository, times(1)).save(cardTransaction);
    }
}

