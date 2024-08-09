package com.spring.card.cards;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class CardLimitTest {

    @Test
    public void testNoArgsConstructor() {
        // Teste do construtor sem argumentos
        CardLimit cardLimit = new CardLimit();
        assertNotNull(cardLimit);
    }

    @Test
    public void testAllArgsConstructor() {
        // Teste do construtor com todos os argumentos
        Card card = new Card();
        LocalDateTime now = LocalDateTime.now();

        CardLimit cardLimit = new CardLimit(1, card, 5000.0, now);

        assertEquals(1, cardLimit.getId());
        assertEquals(card, cardLimit.getCard());
        assertEquals(5000.0, cardLimit.getLimite());
        assertEquals(now, cardLimit.getUpdated());
    }

    @Test
    public void testGettersAndSetters() {
        // Teste dos getters e setters
        CardLimit cardLimit = new CardLimit();
        Card card = new Card();
        LocalDateTime now = LocalDateTime.now();

        cardLimit.setId(1);
        cardLimit.setCard(card);
        cardLimit.setLimite(5000.0);
        cardLimit.setUpdated(now);

        assertEquals(1, cardLimit.getId());
        assertEquals(card, cardLimit.getCard());
        assertEquals(5000.0, cardLimit.getLimite());
        assertEquals(now, cardLimit.getUpdated());
    }
}

