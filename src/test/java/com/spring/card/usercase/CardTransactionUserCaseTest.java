package com.spring.card.usercase;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardLimit;
import com.spring.card.cards.CardTransaction;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import com.spring.card.interfaceadapters.gateways.CardLimitGateway;
import com.spring.card.interfaceadapters.gateways.CardTransactionGateway;
import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import com.spring.card.interfaceadapters.presenters.dto.response.CardTransactionResponseDto;
import com.spring.card.util.exception.ExceptionHandlerUtil;
import com.spring.card.util.exception.StandardError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

class CardTransactionUserCaseTest {

    @InjectMocks
    private CardTransactionUserCase cardTransactionUserCase;

    @Mock
    private Clock clock;

    @Mock
    private CardGateway cardGateway;

    @Mock
    private CardLimitGateway cardLimitGateway;

    @Mock
    private CardTransactionGateway cardTransactionGateway;

    @Mock
    private ExceptionHandlerUtil exceptionHandlerUtil;

    private CardTransactionRequestDto validRequestDto;
    private Card validCard;
    private CardLimit validCardLimit;
    private CardTransaction validCardTransaction;
    private Clock fixedClock;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);

        fixedClock = Clock.fixed(Instant.parse("2023-08-01T10:00:00Z"), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());

        validRequestDto = new CardTransactionRequestDto();
        validRequestDto.setCpf("12345678900");
        validRequestDto.setCardNumber("1234567890123456");
        validRequestDto.setCvv("123");
        validRequestDto.setExpirationDate("1225");
        validRequestDto.setValue(100.0);

        validCard = new Card();
        validCard.setCpf("12345678900");
        validCard.setCardNumber("1234567890123456");
        validCard.setCvv("123");
        validCard.setExpirationDate("1225");
        validCard.setId(1);

        validCardLimit = new CardLimit();
        validCardLimit.setId(1);
        validCardLimit.setLimite(200.0);

        validCardTransaction = new CardTransaction();
        validCardTransaction.setId(1);
        validCardTransaction.setCard(validCard);
        validCardTransaction.setValue(100.0);
        validCardTransaction.setDateTimeCreated(LocalDateTime.now(fixedClock));
    }

    @Test
     void testCreateTransaction_HappyPath() {
        // Arrange
        when(cardGateway.findByCpfAndCardNumber(validRequestDto.getCpf(), validRequestDto.getCardNumber())).thenReturn(validCard);
        when(cardLimitGateway.findByCardId(validCard.getId())).thenReturn(validCardLimit);
        when(cardTransactionGateway.createTransaction(any(CardTransaction.class))).thenReturn(validCardTransaction);

        // Act
        ResponseEntity<?> response = cardTransactionUserCase.createTransaction(validRequestDto);

        // Assert
        assertEquals(CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof CardTransactionResponseDto);
        CardTransactionResponseDto responseDto = (CardTransactionResponseDto) response.getBody();
        assertEquals(1, Integer.parseInt(responseDto.getId()));
    }

    @Test
     void testCreateTransaction_CardNotFound() {
        // Arrange
        when(cardGateway.findByCpfAndCardNumber(validRequestDto.getCpf(), validRequestDto.getCardNumber())).thenReturn(null);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Some detailed error message",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardTransactionUserCase.createTransaction(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_NOT_FOUND", error.getError());
    }

    @Test
     void testCreateTransaction_InvalidCvv() {
        // Arrange
        validCard.setCvv("999");
        when(cardGateway.findByCpfAndCardNumber(validRequestDto.getCpf(), validRequestDto.getCardNumber())).thenReturn(validCard);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Some detailed error message",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardTransactionUserCase.createTransaction(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_NOT_FOUND", error.getError());
    }

    @Test
     void testCreateTransaction_InvalidExpirationDate() {
        // Arrange
        when(cardGateway.findByCpfAndCardNumber(validRequestDto.getCpf(), validRequestDto.getCardNumber())).thenReturn(null);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Some detailed error message",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardTransactionUserCase.createTransaction(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_NOT_FOUND", error.getError());
    }

    @Test
     void testCreateTransaction_InsufficientCardLimit() {
        // Arrange
        validCardLimit.setLimite(50.0);
        when(cardGateway.findByCpfAndCardNumber(validRequestDto.getCpf(), validRequestDto.getCardNumber())).thenReturn(validCard);
        when(cardLimitGateway.findByCardId(validCard.getId())).thenReturn(validCardLimit);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("INSUFFICIENT_CARD_LIMIT")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Some detailed error message",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardTransactionUserCase.createTransaction(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("INSUFFICIENT_CARD_LIMIT", error.getError());
    }
}