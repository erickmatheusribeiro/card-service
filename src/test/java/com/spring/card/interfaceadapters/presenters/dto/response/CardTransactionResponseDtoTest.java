package com.spring.card.interfaceadapters.presenters.dto.response;

import com.spring.card.cards.CardTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CardTransactionResponseDtoTest {

    @Test
    public void testNoArgsConstructor() {
        // Arrange & Act
        CardTransactionResponseDto dto = new CardTransactionResponseDto();

        // Assert
        assertNull(dto.getId());
        assertEquals(0.0, dto.getValue());
        assertNull(dto.getCreated());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        CardTransactionResponseDto dto = new CardTransactionResponseDto();
        String id = "1";
        double value = 100.0;
        String created = "2024-08-09T12:00:00";

        // Act
        dto.setId(id);
        dto.setValue(value);
        dto.setCreated(created);

        // Assert
        assertEquals(id, dto.getId());
        assertEquals(value, dto.getValue());
        assertEquals(created, dto.getCreated());
    }

    @Test
    public void testConstructorWithCardTransaction() {
        // Arrange
        CardTransaction entity = new CardTransaction();
        entity.setId(1);
        entity.setValue(100.0);
        entity.setDateTimeCreated(LocalDateTime.parse("2024-08-09T12:00:00"));

        // Act
        CardTransactionResponseDto dto = new CardTransactionResponseDto(entity);

        // Assert
        assertEquals("1", dto.getId());
        assertEquals(100.0, dto.getValue());
        assertEquals("2024-08-09T12:00", dto.getCreated());
    }
}

