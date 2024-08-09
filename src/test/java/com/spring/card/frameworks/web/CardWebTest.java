package com.spring.card.frameworks.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.card.interfaceadapters.controllers.CardController;
import com.spring.card.interfaceadapters.controllers.CardTransactionController;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardWeb.class)
@AutoConfigureMockMvc
class CardWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardController cardController;

    @MockBean
    private CardTransactionController cardTransactionController;

    @Autowired
    private CardWeb cardWeb;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testInsertCard() throws Exception {
        CardResquestDto cardRequestDto = new CardResquestDto();
        // Set fields for cardRequestDto

        when(cardController.insert(any(CardResquestDto.class))).thenReturn(
                ResponseEntity.ok().build()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/cartao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCard() throws Exception {
        String cpf = "21910056081";
        String numero = "5568872479420825";
        String data = "0625";
        String cvv = "545";

        when(cardController.findByCardNumber(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/cartao")
                        .param("cpf", cpf)
                        .param("numero", numero)
                        .param("data", data)
                        .param("cvv", cvv))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateCard() throws Exception {
        CardTransactionRequestDto transactionRequestDto = new CardTransactionRequestDto();
        // Set fields for transactionRequestDto

        when(cardTransactionController.createTransaction(any(CardTransactionRequestDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.post("/cartao/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDto)))
                .andExpect(status().isOk());
    }
}