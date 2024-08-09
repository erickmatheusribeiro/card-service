package com.spring.card.interfaceadapters.presenters.dto.response;

import com.spring.card.cards.CardLimit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CardLimitResponseDtoTest {

    @Test
    public void testNoArgsConstructor() {
        // Arrange & Act
        CardLimitResponseDto dto = new CardLimitResponseDto();

        // Assert
        assertEquals(0.0, dto.getLimit());
        assertNull(dto.getUpdated());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        CardLimitResponseDto dto = new CardLimitResponseDto();
        double limit = 5000.0;
        LocalDateTime updated = LocalDateTime.now();

        // Act
        dto.setLimit(limit);
        dto.setUpdated(updated);

        // Assert
        assertEquals(limit, dto.getLimit());
        assertEquals(updated, dto.getUpdated());
    }

    @Test
    public void testConstructorWithCardLimit() {
        // Arrange
        CardLimit entity = new CardLimit();
        double limit = 5000.0;
        LocalDateTime updated = LocalDateTime.now();
        entity.setLimite(limit);
        entity.setUpdated(updated);

        // Act
        CardLimitResponseDto dto = new CardLimitResponseDto(entity);

        // Assert
        assertEquals(limit, dto.getLimit());
        assertEquals(updated, dto.getUpdated());
    }
}
