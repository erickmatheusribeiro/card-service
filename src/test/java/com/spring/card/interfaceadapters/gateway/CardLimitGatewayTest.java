package com.spring.card.interfaceadapters.gateway;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardLimit;
import com.spring.card.frameworks.db.CardLimitRepository;
import com.spring.card.interfaceadapters.gateways.CardLimitGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardLimitGatewayTest {

    @InjectMocks
    private CardLimitGateway cardLimitGateway;

    @Mock
    private CardLimitRepository cardLimitRepository;

    private CardLimit cardLimit;
    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(123);
        // Configure other fields of Card as necessary

        cardLimit = new CardLimit();
        cardLimit.setId(1);
        cardLimit.setCard(card);
        cardLimit.setLimite(1000.0);
        cardLimit.setUpdated(LocalDateTime.now());
    }

    @Test
    void testFindByCardId() {
        when(cardLimitRepository.findByCardId(card.getId())).thenReturn(cardLimit);

        CardLimit foundCardLimit = cardLimitGateway.findByCardId(card.getId());

        assertEquals(cardLimit, foundCardLimit);
        verify(cardLimitRepository, times(1)).findByCardId(card.getId());
    }

    @Test
    void testInsert() {
        when(cardLimitRepository.save(cardLimit)).thenReturn(cardLimit);

        CardLimit savedCardLimit = cardLimitGateway.insert(cardLimit);

        assertEquals(cardLimit, savedCardLimit);
        verify(cardLimitRepository, times(1)).save(cardLimit);
    }

    @Test
    void testUpdate() {
        when(cardLimitRepository.save(cardLimit)).thenReturn(cardLimit);

        CardLimit updatedCardLimit = cardLimitGateway.update(cardLimit);

        assertEquals(cardLimit, updatedCardLimit);
        verify(cardLimitRepository, times(1)).save(cardLimit);
    }
}
