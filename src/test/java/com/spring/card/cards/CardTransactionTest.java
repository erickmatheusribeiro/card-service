package com.spring.card.cards;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

public class CardTransactionTest {

    @Test
    public void testCardTransactionEntity() {
        // Criação de uma instância de CardTransaction
        CardTransaction cardTransaction = new CardTransaction();

        // Criação de uma instância de Card para a associação
        Card card = new Card();
        card.setId(1); // Ajuste conforme necessário

        // Definindo valores para os campos
        Integer id = 1;
        String paymentId = "12345678901";
        double value = 100.0;
        LocalDateTime dateTimeCreated = LocalDateTime.now();

        cardTransaction.setId(id);
        cardTransaction.setValue(value);
        cardTransaction.setDateTimeCreated(dateTimeCreated);
        cardTransaction.setCard(card);

        // Verificação dos valores definidos
        assertThat(cardTransaction.getId()).isEqualTo(id);
        assertThat(cardTransaction.getValue()).isEqualTo(value);
        assertThat(cardTransaction.getDateTimeCreated()).isEqualTo(dateTimeCreated);
        assertThat(cardTransaction.getCard()).isEqualTo(card);
    }

    @Test
    public void testAllArgsConstructor() {
        // Dados de teste
        Integer id = 1;
        double value = 100.50;
        LocalDateTime dateTimeCreated = LocalDateTime.of(2024, 8, 8, 20, 53, 32);
        Card card = new Card(); // Crie um objeto Card apropriado ou use um mock, se necessário

        // Criação do objeto usando o construtor AllArgsConstructor
        CardTransaction cardTransaction = new CardTransaction(id, value, dateTimeCreated, card);

        // Verificações
        assertEquals(id, cardTransaction.getId(), "ID should be initialized correctly");
        assertEquals(value, cardTransaction.getValue(), "Value should be initialized correctly");
        assertEquals(dateTimeCreated, cardTransaction.getDateTimeCreated(), "DateTimeCreated should be initialized correctly");
        assertEquals(card, cardTransaction.getCard(), "Card should be initialized correctly");
    }

    @Test
    public void testNoArgsConstructor() {
        // Criação do objeto usando o construtor NoArgsConstructor
        CardTransaction cardTransaction = new CardTransaction();

        // Verificações dos valores iniciais padrão
        assertNull(cardTransaction.getId(), "ID should be null by default");
        assertEquals(0.0, cardTransaction.getValue(), "Value should be 0.0 by default");
        assertNull(cardTransaction.getDateTimeCreated(), "DateTimeCreated should be null by default");
        assertNull(cardTransaction.getCard(), "Card should be null by default");
    }

    @Test
    public void testGettersAndSetters() {
        // Dados de teste
        Integer id = 1;
        double value = 100.50;
        LocalDateTime dateTimeCreated = LocalDateTime.of(2024, 8, 8, 20, 53, 32);
        Card card = new Card(); // Crie um objeto Card apropriado ou use um mock, se necessário

        // Criação do objeto usando o construtor NoArgsConstructor
        CardTransaction cardTransaction = new CardTransaction();

        // Teste dos setters
        cardTransaction.setId(id);
        cardTransaction.setValue(value);
        cardTransaction.setDateTimeCreated(dateTimeCreated);
        cardTransaction.setCard(card);

        // Teste dos getters
        assertEquals(id, cardTransaction.getId(), "ID should be set and retrieved correctly");
        assertEquals(value, cardTransaction.getValue(), "Value should be set and retrieved correctly");
        assertEquals(dateTimeCreated, cardTransaction.getDateTimeCreated(), "DateTimeCreated should be set and retrieved correctly");
        assertEquals(card, cardTransaction.getCard(), "Card should be set and retrieved correctly");
    }
}
