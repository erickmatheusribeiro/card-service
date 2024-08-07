package com.spring.card.cards;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
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
        cardTransaction.setPaymentId(paymentId);
        cardTransaction.setValue(value);
        cardTransaction.setDateTimeCreated(dateTimeCreated);
        cardTransaction.setCard(card);

        // Verificação dos valores definidos
        assertThat(cardTransaction.getId()).isEqualTo(id);
        assertThat(cardTransaction.getPaymentId()).isEqualTo(paymentId);
        assertThat(cardTransaction.getValue()).isEqualTo(value);
        assertThat(cardTransaction.getDateTimeCreated()).isEqualTo(dateTimeCreated);
        assertThat(cardTransaction.getCard()).isEqualTo(card);
    }
}
