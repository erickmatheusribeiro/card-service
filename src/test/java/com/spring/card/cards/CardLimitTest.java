package com.spring.card.cards;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;

public class CardLimitTest {

    @Test
    public void testCardLimitEntity() {
        // Criação de uma instância de CardLimit
        CardLimit cardLimit = new CardLimit();

        // Criação de uma instância de Card para a associação
        Card card = new Card();
        card.setId(1); // Ajuste conforme necessário

        // Definindo valores para os campos
        Integer id = 1;
        double limite = 1000.0;
        LocalDateTime updated = LocalDateTime.now();

        cardLimit.setId(id);
        cardLimit.setCard(card);
        cardLimit.setLimite(limite);
        cardLimit.setUpdated(updated);

        // Verificação dos valores definidos
        assertThat(cardLimit.getId()).isEqualTo(id);
        assertThat(cardLimit.getCard()).isEqualTo(card);
        assertThat(cardLimit.getLimite()).isEqualTo(limite);
        assertThat(cardLimit.getUpdated()).isEqualTo(updated);
    }
}

