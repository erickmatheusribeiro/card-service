package com.spring.card.interfaceadapters.presenters.converters;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.spring.card.cards.Card;
import com.spring.card.interfaceadapters.presenters.dto.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CardPresenterTest {

    @InjectMocks
    private CardPresenter cardPresenter;

    @Mock
    private Card card;

    @Mock
    private CardDto cardDto;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testMapToDto_HappyPath() {
        // Arrange
        when(card.getCpf()).thenReturn("12345678900");
        when(card.getCardNumber()).thenReturn("1234567890123456");
        when(card.getExpirationDate()).thenReturn("1225");
        when(card.getCvv()).thenReturn("123");

        // Act
        CardDto result = cardPresenter.mapToDto(card);

        // Assert
        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
        assertEquals("1234567890123456", result.getNumero());
        assertEquals("1225", result.getData_validade());
        assertEquals("123", result.getCvv());
    }

    @Test
     void testMapToDto_UnhappyPath() {
        // Arrange
        when(card.getCpf()).thenReturn(null);
        when(card.getCardNumber()).thenReturn(null);
        when(card.getExpirationDate()).thenReturn(null);
        when(card.getCvv()).thenReturn(null);

        // Act
        CardDto result = cardPresenter.mapToDto(card);

        // Assert
        assertNotNull(result);
        assertNull(result.getCpf());
        assertNull(result.getNumero());
        assertNull(result.getData_validade());
        assertNull(result.getCvv());
    }

    @Test
     void testMapToEntity_HappyPath() {
        // Arrange
        when(cardDto.getCpf()).thenReturn("12345678900");
        when(cardDto.getNumero()).thenReturn("1234567890123456");
        when(cardDto.getData_validade()).thenReturn("1225");
        when(cardDto.getCvv()).thenReturn("123");

        // Act
        Card result = cardPresenter.mapToEntity(cardDto);

        // Assert
        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
        assertEquals("1234567890123456", result.getCardNumber());
        assertEquals("1225", result.getExpirationDate());
        assertEquals("123", result.getCvv());
    }

    @Test
     void testMapToEntity_UnhappyPath() {
        // Arrange
        when(cardDto.getCpf()).thenReturn(null);
        when(cardDto.getNumero()).thenReturn(null);
        when(cardDto.getData_validade()).thenReturn(null);
        when(cardDto.getCvv()).thenReturn(null);

        // Act
        Card result = cardPresenter.mapToEntity(cardDto);

        // Assert
        assertNotNull(result);
        assertNull(result.getCpf());
        assertNull(result.getCardNumber());
        assertNull(result.getExpirationDate());
        assertNull(result.getCvv());
    }
}