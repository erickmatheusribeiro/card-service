package com.spring.card.interfaceadapters.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import com.spring.card.usercase.CardTransactionUserCase;
import com.spring.card.util.MessageUtil;
import com.spring.card.util.exception.StandardError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CardTransactionControllerTest {

    @InjectMocks
    private CardTransactionController cardTransactionController;

    @Mock
    private CardTransactionUserCase cardTransactionUserCase;

    private CardTransactionRequestDto validTransactionRequestDto;
    private CardTransactionRequestDto invalidTransactionRequestDto;

    @BeforeEach
    void setUp() {
        validTransactionRequestDto = new CardTransactionRequestDto();
        validTransactionRequestDto.setCpf("12345678901");
        validTransactionRequestDto.setCardNumber("1234567890123456");
        validTransactionRequestDto.setExpirationDate("12/25");
        validTransactionRequestDto.setCvv("123");
        validTransactionRequestDto.setValue(1000.0);

        invalidTransactionRequestDto = new CardTransactionRequestDto();
    }

    @Test
    void testCreateTransactionValid() {
        when(cardTransactionUserCase.createTransaction(validTransactionRequestDto))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = cardTransactionController.createTransaction(validTransactionRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cardTransactionUserCase, times(1)).createTransaction(validTransactionRequestDto);
    }

    @Test
    void testCreateTransactionInvalid() {
        ResponseEntity<?> response = cardTransactionController.createTransaction(invalidTransactionRequestDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        StandardError expectedError = new StandardError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "UNREPORTED_DATA_ERROR",
                MessageUtil.getMessage("UNREPORTED_DATA_ERROR"),
                "/api/cartao"
        );

        StandardError actualError = (StandardError) response.getBody();

        assertNotNull(actualError);
        assertEquals(expectedError.getError(), actualError.getError());
        assertEquals(expectedError.getMessage(), actualError.getMessage());
        assertEquals(expectedError.getPath(), actualError.getPath());

        verify(cardTransactionUserCase, never()).createTransaction(any(CardTransactionRequestDto.class));
    }
}

