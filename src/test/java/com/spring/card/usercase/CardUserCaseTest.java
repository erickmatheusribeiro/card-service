package com.spring.card.usercase;

import com.spring.card.cards.Card;
import com.spring.card.cards.CardLimit;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import com.spring.card.interfaceadapters.gateways.CardLimitGateway;
import com.spring.card.interfaceadapters.presenters.converters.CardPresenter;
import com.spring.card.interfaceadapters.presenters.dto.CardDto;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.interfaceadapters.presenters.dto.response.CardLimitResponseDto;
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
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CardUserCaseTest {

    @InjectMocks
    private CardUserCase cardUserCase;

    @Mock
    private CardGateway cardGateway;

    @Mock
    private CardLimitGateway cardLimitGateway;

    @Mock
    private CardPresenter presenter;

    @Mock
    private ExceptionHandlerUtil exceptionHandlerUtil;

    @Mock
    private Clock clock;

    private CardResquestDto validRequestDto;
    private CardResquestDto validRequesDuplicatetDto;
    private Card validCard;
    private CardLimit validCardLimit;
    private CardDto validCardDto;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequestDto = new CardResquestDto();
        validRequestDto.setCpf("21910056081");
        validRequestDto.setNumero("5568872479420825");
        validRequestDto.setCvv("123");
        validRequestDto.setData_validade("1225");
        validRequestDto.setLimite(1000.0);

        validCard = new Card();
        validCard.setCpf("12345678900");
        validCard.setCardNumber("1234567890123456");
        validCard.setCvv("123");
        validCard.setExpirationDate("1225");
        validCard.setId(1);

        validCardLimit = new CardLimit();
        validCardLimit.setId(1);
        validCardLimit.setLimite(1000.0);

        validCardDto = new CardDto();
        validCardDto.setCpf("12345678900");
        validCardDto.setNumero("1234567890123456");
        validCardDto.setData_validade("1225");
        validCardDto.setCvv("123");

        // Configurar o Clock para retornar um tempo fixo sem stubbing direto
        Instant fixedInstant = Instant.parse("2023-01-01T10:00:00Z");
        ZoneId zoneId = ZoneId.systemDefault();
        clock = Clock.fixed(fixedInstant, zoneId);
        cardUserCase.setClock(clock);
    }

    @Test
     void testCreateCard_HappyPath() {
        // Arrange
        when(cardGateway.findByCardNumber(validRequestDto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(validRequestDto.getCpf())).thenReturn(0L);
        when(cardGateway.insert(any(Card.class))).thenReturn(validCard);
        when(cardLimitGateway.insert(any(CardLimit.class))).thenReturn(validCardLimit);
        when(presenter.mapToDto(validCard)).thenReturn(validCardDto);

        // Act
        ResponseEntity<?> response = cardUserCase.createCard(validRequestDto);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof CardDto);
        CardDto responseDto = (CardDto) response.getBody();
        assertEquals(validCardDto.getNumero(), responseDto.getNumero());
        assertEquals(validCardDto.getCpf(), responseDto.getCpf());
    }

    @Test
     void testCreateCard_InvalidCardLimit() {
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
     void testCreateCard_CpfExceeded() {
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
     void testCreateCard_InvalidLengthCardNumber() {
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
     void testCreateCard_InvalidExpirationDate() {
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
     void testCreateCard_InvalidCpf() {
        // Arrange
        validRequestDto.setCpf("12345678900"); // Invalid CPF
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

    @Test
    public void testGetLimitByCard_CardNotFound() {
        // Arrange
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Card not found",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.getLimitByCard("21910056081", "5568872479420825", "0625", "545");

        // Assert
        verify(exceptionHandlerUtil, times(1)).genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_NOT_FOUND", error.getError());
    }

    @Test
    public void testGetLimitByCard_CardDetailsMismatch() {
        // Arrange
        when(cardGateway.findByCpfAndCardNumber(eq("21910056081"), eq("5568872479420825"))).thenReturn(validCard);
        when(exceptionHandlerUtil.genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND")))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(0);
                    String message = invocation.getArgument(1);
                    return ResponseEntity.status(status)
                            .body(new StandardError(
                                    status.value(),
                                    message,
                                    "Card not found",
                                    "/api/cartao"
                            ));
                });

        // Act
        ResponseEntity<?> response = cardUserCase.getLimitByCard("21910056081", "5568872479420825", "1234", "545");

        // Assert
        verify(exceptionHandlerUtil, times(1)).genericExceptions(eq(HttpStatus.INTERNAL_SERVER_ERROR), eq("CARD_NOT_FOUND"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof StandardError);
        StandardError error = (StandardError) response.getBody();
        assertEquals("CARD_NOT_FOUND", error.getError());
    }

    @Test
    void testGetLimitByCard_Success() {
        // Arrange
        String cpf = "12345678900";
        String numero = "1234567890123456";
        String data = "1225";
        String cvv = "123";

        // Configurar mocks
        when(cardGateway.findByCpfAndCardNumber(cpf, numero)).thenReturn(validCard);
        when(cardLimitGateway.findByCardId(validCard.getId())).thenReturn(validCardLimit);

        // Act
        ResponseEntity<?> response = cardUserCase.getLimitByCard(cpf, numero, data, cvv);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody() instanceof CardLimitResponseDto, "Response body should be instance of CardLimitResponseDto");

        CardLimitResponseDto responseDto = (CardLimitResponseDto) response.getBody();
        assertEquals(validCardLimit.getLimite(), responseDto.getLimit(), "Card limit should match");

        // Verificar interações
        verify(cardGateway, times(1)).findByCpfAndCardNumber(cpf, numero);
        verify(cardLimitGateway, times(1)).findByCardId(validCard.getId());
    }
}

