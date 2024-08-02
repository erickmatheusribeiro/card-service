package com.spring.card;

import com.spring.card.entities.Card;
import com.spring.card.interfaceadapters.gateways.CardGateway;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.usercase.CardUserCase;
import com.spring.card.util.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @InjectMocks
    private CardUserCase cardService;

    @Mock
    private CardGateway cardGateway;

    @Mock
    private Clock clock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCard_DuplicateCard_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "5568872479420822", "0625", "100");
        Card existingCard = new Card();
        existingCard.setCardNumber(dto.getNumero());
        existingCard.setCpf(dto.getCpf());

        when(cardGateway.findByCardNumber(dto.getNumero())).thenReturn(existingCard);

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("CARD_DUPLICATE", thrown.getMessage());
    }

    @Test
    void testCreateCard_NullLimit_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", null);

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("CARD_LIMIT_NOT_FOUND", thrown.getMessage());
    }

    @Test
    void testCreateCard_LimitNotGreaterThanZero_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "0");

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("CARD_LIMIT_NOT_FOUND", thrown.getMessage());
    }

    @Test
    void testCreateCard_CpfExceeded_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "100");
        when(cardGateway.countByCpf(dto.getCpf())).thenReturn(2L);

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("CARD_CPF_EXCEEDED", thrown.getMessage());
    }

    @Test
    void testCreateCard_InvalidCardNumberLength_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("123456789012345", 12345678901L, "123", "0625", "100");

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("INVALID_LENGTH_CARD_NUMBER", thrown.getMessage());
    }

    @Test
    void testCreateCard_InvalidExpirationDateLength_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "062", "100");

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("INVALID_EXPIRATION_DATE", thrown.getMessage());
    }

    @Test
    void testCreateCard_ExpirationDateInvalid_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "1325", "100");
        when(clock.instant()).thenReturn(LocalDateTime.now().toInstant(Clock.systemUTC().getZone().getRules().getOffset(LocalDateTime.now())));

        when(cardGateway.findByCardNumber(dto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(dto.getCpf())).thenReturn(0L);
        when(cardGateway.insert(any(Card.class))).thenReturn(new Card());

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("INVALID_EXPIRATION_DATE", thrown.getMessage());
    }

    @Test
    void testCreateCard_InvalidCpf_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "100");
        when(cardGateway.findByCardNumber(dto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(dto.getCpf())).thenReturn(0L);
        when(cardGateway.insert(any(Card.class))).thenReturn(new Card());
        when(cardService.isCpfValid(dto.getCpf())).thenReturn(true);

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("INVALID_CPF", thrown.getMessage());
    }

    @Test
    void testCreateCard_Success() throws BusinessException {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "100");
        Card card = new Card();
        card.setCpf(dto.getCpf());
        card.setCardNumber(dto.getNumero());
        card.setExpirationDate(dto.getData_validade());
        card.setCvv(dto.getCvv());
        when(cardGateway.findByCardNumber(dto.getNumero())).thenReturn(null);
        when(cardGateway.countByCpf(dto.getCpf())).thenReturn(0L);
        when(cardGateway.insert(any(Card.class))).thenReturn(card);
        when(clock.instant()).thenReturn(LocalDateTime.now().toInstant(Clock.systemUTC().getZone().getRules().getOffset(LocalDateTime.now())));

        cardService.createCard(dto);

        verify(cardGateway).insert(any(Card.class));
    }

    // Continue criando mais testes para cobrir todos os cenários
    @Test
    void testCreateCard_ValidCardNumberLength_16Characters() throws BusinessException {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "100");

        assertDoesNotThrow(() -> cardService.createCard(dto));
    }

    @Test
    void testCreateCard_ValidExpirationDate_4Characters() throws BusinessException {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "100");

        assertDoesNotThrow(() -> cardService.createCard(dto));
    }

    @Test
    void testCreateCard_ValidLimit() throws BusinessException {
        CardResquestDto dto = new CardResquestDto("1234567890123456", 12345678901L, "123", "0625", "100");

        assertDoesNotThrow(() -> cardService.createCard(dto));
    }

    @Test
    void testCreateCard_InvalidCardNumber_ThrowsBusinessException() {
        CardResquestDto dto = new CardResquestDto("invalidnumber", 12345678901L, "123", "0625", "100");

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cardService.createCard(dto);
        });

        assertEquals("INVALID_LENGTH_CARD_NUMBER", thrown.getMessage());
    }

//    @Test
//    void testCreateCard_InvalidCpfFormat_ThrowsBusinessException() {
//        CardResquestDto dto = new CardResquestDto("1234567890123456", 0L, "123", "0625", "100");
//
//        BusinessException thrown = assertThrows(BusinessException.class, () -> {
//            cardService.createCard(dto);
//        });
//
//        assertEquals("INVALID_CPF", thrown.getMessage());
//    }

    // Continue variando os cenários e validações de acordo com os requisitos

}
