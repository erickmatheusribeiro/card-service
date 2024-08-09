package com.spring.card.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardTest {

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        CardLimit cardLimit = new CardLimit();
        List<CardTransaction> cardTransactions = new ArrayList<>();

        Card card = new Card(1, "12345678901", "1234567812345678", "12/25", "123", now, cardLimit, cardTransactions);

        assertEquals(1, card.getId());
        assertEquals("12345678901", card.getCpf());
        assertEquals("1234567812345678", card.getCardNumber());
        assertEquals("12/25", card.getExpirationDate());
        assertEquals("123", card.getCvv());
        assertEquals(now, card.getDateTimeCreated());
        assertEquals(cardLimit, card.getCardLimit());
        assertEquals(cardTransactions, card.getCardTransactions());
    }

    @Test
    public void testGettersAndSetters() {
        Card card = new Card();
        LocalDateTime now = LocalDateTime.now();
        CardLimit cardLimit = new CardLimit();
        List<CardTransaction> cardTransactions = new ArrayList<>();

        card.setId(1);
        card.setCpf("12345678901");
        card.setCardNumber("1234567812345678");
        card.setExpirationDate("12/25");
        card.setCvv("123");
        card.setDateTimeCreated(now);
        card.setCardLimit(cardLimit);
        card.setCardTransactions(cardTransactions);

        assertEquals(1, card.getId());
        assertEquals("12345678901", card.getCpf());
        assertEquals("1234567812345678", card.getCardNumber());
        assertEquals("12/25", card.getExpirationDate());
        assertEquals("123", card.getCvv());
        assertEquals(now, card.getDateTimeCreated());
        assertEquals(cardLimit, card.getCardLimit());
        assertEquals(cardTransactions, card.getCardTransactions());
    }
}
