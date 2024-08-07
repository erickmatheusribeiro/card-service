package com.spring.card.interfaceadapters.gateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.spring.card.cards.Card;
import com.spring.card.frameworks.db.CardRepository;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardGatewayTest {

    @InjectMocks
    private CardGateway cardGateway;

    @Mock
    private CardRepository cardRepository;

    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(1);
        card.setCardNumber("1234567890123456");
        card.setCpf("12345678901");
        // configure other fields as necessary
    }

    @Test
    void testFindByCardNumber() {
        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);

        Card foundCard = cardGateway.findByCardNumber(card.getCardNumber());

        assertEquals(card, foundCard);
        verify(cardRepository, times(1)).findByCardNumber(card.getCardNumber());
    }

    @Test
    void testFindByCpfAndCardNumber() {
        when(cardRepository.findByCpfAndCardNumber(card.getCpf(), card.getCardNumber())).thenReturn(card);

        Card foundCard = cardGateway.findByCpfAndCardNumber(card.getCpf(), card.getCardNumber());

        assertEquals(card, foundCard);
        verify(cardRepository, times(1)).findByCpfAndCardNumber(card.getCpf(), card.getCardNumber());
    }

    @Test
    void testInsert() {
        when(cardRepository.save(card)).thenReturn(card);

        Card savedCard = cardGateway.insert(card);

        assertEquals(card, savedCard);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testUpdate() {
        when(cardRepository.save(card)).thenReturn(card);

        Card updatedCard = cardGateway.update(card);

        assertEquals(card, updatedCard);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testCountByCpf() {
        long expectedCount = 5L;
        when(cardRepository.countByCpf(card.getCpf())).thenReturn(expectedCount);

        long count = cardGateway.countByCpf(card.getCpf());

        assertEquals(expectedCount, count);
        verify(cardRepository, times(1)).countByCpf(card.getCpf());
    }
}

