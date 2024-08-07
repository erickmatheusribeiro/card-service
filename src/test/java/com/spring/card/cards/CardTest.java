package com.spring.card.cards;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;

public class CardTest {

    @Test
    public void testCardEntity() {
        // Criação de uma instância de Card
        Card card = new Card();

        // Definindo valores para os campos
        Integer id = 1;
        String cpf = "21910056081";
        String cardNumber = "5568872479420825";
        String expirationDate = "0625";
        String cvv = "545";
        LocalDateTime dateTimeCreated = LocalDateTime.now();

        card.setId(id);
        card.setCpf(cpf);
        card.setCardNumber(cardNumber);
        card.setExpirationDate(expirationDate);
        card.setCvv(cvv);
        card.setDateTimeCreated(dateTimeCreated);

        // Verificação dos valores definidos
        assertThat(card.getId()).isEqualTo(id);
        assertThat(card.getCpf()).isEqualTo(cpf);
        assertThat(card.getCardNumber()).isEqualTo(cardNumber);
        assertThat(card.getExpirationDate()).isEqualTo(expirationDate);
        assertThat(card.getCvv()).isEqualTo(cvv);
        assertThat(card.getDateTimeCreated()).isEqualTo(dateTimeCreated);
    }
}

