package com.spring.card.usercase;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardLimit;
import com.spring.card.cards.CardTransaction;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import com.spring.card.interfaceadapters.presenters.converters.CardPresenter;
import com.spring.card.interfaceadapters.presenters.dto.CardDto;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

public class CardUserCaseTest {

    @InjectMocks
    private CardUserCase cardUserCase;

    @Mock
    private CardGateway cardGateway;


    @Mock
    private CardPresenter presenter;

    @Mock
    private ExceptionHandlerUtil exceptionHandlerUtil;

    private CardResquestDto validRequestDto;
    private Card validCard;
    private CardLimit validCardLimit;
    private CardDto validCardDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequestDto = new CardResquestDto();
        validRequestDto.setCpf("12345678900");
        validRequestDto.setNumero("1234567890123456");
        validRequestDto.setCvv("123");
        validRequestDto.setData_validade("1225");
        validRequestDto.setLimite(200.0);

        validCard = new Card();
        validCard.setCpf("12345678900");
        validCard.setCardNumber("1234567890123456");
        validCard.setCvv("123");
        validCard.setExpirationDate("1225");
        validCard.setId(1);

        validCardLimit = new CardLimit();
        validCardLimit.setId(1);
        validCardLimit.setLimite(200.0);

        validCardDto = new CardDto();
        validCardDto.setCpf("12345678900");
        validCardDto.setNumero("1234567890123456");
        validCardDto.setData_validade("1225");
        validCardDto.setCvv("123");
    }

//    todo: ajustar
//    @Test
//    public void testCreateCard_HappyPath() {
//        // Arrange
//        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
//        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
//        when(cardGateway.insert(any(Card.class))).thenReturn(validCard);
//        when(presenter.mapToDto(validCard)).thenReturn(validCardDto);
//
//        // Act
//        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);
//
//        // Assert
//        assertNotNull(response, "Response should not be null");
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody() instanceof CardDto);
//        CardDto responseDto = (CardDto) response.getBody();
//        assertEquals(validCardDto.getNumero(), responseDto.getNumero());
//        assertEquals(validCardDto.getCpf(), responseDto.getCpf());
//    }

    @Test
    public void testCreateCard_CardDuplicate() {
        // Arrange
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(validCard);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_DUPLICATE")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Card already exists",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_DUPLICATE", error.getError());
    }

    @Test
    public void testCreateCard_InvalidCardLimit() {
        // Arrange
        validRequestDto.setLimite(-1.0); // Invalid limit
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_LIMIT_NOT_FOUND")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Invalid card limit",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_LIMIT_NOT_FOUND", error.getError());
    }

    @Test
    public void testCreateCard_CpfExceeded() {
        // Arrange
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(2L); // Exceeds limit
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.FORBIDDEN), eq("CARD_CPF_EXCEEDED")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "CPF exceeds card limit",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_CPF_EXCEEDED", error.getError());
    }

    @Test
    public void testCreateCard_InvalidLengthCardNumber() {
        // Arrange
        validRequestDto.setNumero("123456"); // Invalid length
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("INVALID_LENGTH_CARD_NUMBER")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Invalid card number length",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("INVALID_LENGTH_CARD_NUMBER", error.getError());
    }

    @Test
    public void testCreateCard_InvalidExpirationDate() {
        // Arrange
        validRequestDto.setData_validade("123"); // Invalid expiration date length
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("INVALID_EXPIRATION_DATE")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Invalid expiration date",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("INVALID_EXPIRATION_DATE", error.getError());
    }

    @Test
    public void testCreateCard_InvalidCpf() {
        // Arrange
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("INVALID_CPF")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Invalid CPF",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("INVALID_CPF", error.getError());
    }

}

