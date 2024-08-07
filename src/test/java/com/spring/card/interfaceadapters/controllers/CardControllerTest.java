package com.spring.card.interfaceadapters.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.usercase.CardUserCase;
import com.spring.card.util.exception.ExceptionHandlerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {
    @InjectMocks
    private CardController cardController;

    @Mock
    private CardUserCase cardUserCase;

    @Mock
    private ExceptionHandlerUtil exceptionHandlerUtil;

    private CardResquestDto validCardRequestDto;
    private CardResquestDto invalidCardRequestDto;

    @BeforeEach
    void setUp() {
        validCardRequestDto = new CardResquestDto();
        validCardRequestDto.setNumero("1234567890123456");
        validCardRequestDto.setCpf("12345678901");
        validCardRequestDto.setCvv("123");
        validCardRequestDto.setData_validade("12/25");
        validCardRequestDto.setLimite(1000D);

        invalidCardRequestDto = new CardResquestDto();
    }

    @Test
    void testInsertValidCard() {
        when(cardUserCase.createCard(validCardRequestDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = cardController.insert(validCardRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cardUserCase, times(1)).createCard(validCardRequestDto);
        verify(exceptionHandlerUtil, never()).genericExceptions(any(HttpStatus.class), anyString());
    }

    @Test
    void testInsertInvalidCard() {
        when(exceptionHandlerUtil.genericExceptions(HttpStatus.INTERNAL_SERVER_ERROR, "UNREPORTED_DATA_ERROR"))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<?> response = cardController.insert(invalidCardRequestDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(cardUserCase, never()).createCard(any(CardResquestDto.class));
        verify(exceptionHandlerUtil, times(1)).genericExceptions(HttpStatus.INTERNAL_SERVER_ERROR, "UNREPORTED_DATA_ERROR");
    }

    @Test
    void testFindByCardNumberValid() {
        String cpf = "12345678901";
        String numero = "1234567890123456";
        String data = "12/25";
        String cvv = "123";

        when(cardUserCase.getLimitByCard(cpf, numero, data, cvv)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = cardController.findByCardNumber(cpf, numero, data, cvv);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cardUserCase, times(1)).getLimitByCard(cpf, numero, data, cvv);
        verify(exceptionHandlerUtil, never()).genericExceptions(any(HttpStatus.class), anyString());
    }

    @Test
    void testFindByCardNumberInvalid() {
        when(exceptionHandlerUtil.genericExceptions(HttpStatus.INTERNAL_SERVER_ERROR, "UNREPORTED_DATA_ERROR"))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<?> response = cardController.findByCardNumber(null, null, null, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(cardUserCase, never()).getLimitByCard(anyString(), anyString(), anyString(), anyString());
        verify(exceptionHandlerUtil, times(1)).genericExceptions(HttpStatus.INTERNAL_SERVER_ERROR, "UNREPORTED_DATA_ERROR");
    }
}
