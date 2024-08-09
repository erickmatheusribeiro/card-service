package com.spring.card.frameworks.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.card.interfaceadapters.controllers.CardController;
import com.spring.card.interfaceadapters.controllers.CardTransactionController;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardWeb.class)
public class CardWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CardController cardController;

    @Mock
    private CardTransactionController cardTransactionController;

    @InjectMocks
    private CardWeb cardWeb;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testInsertCard() throws Exception {
        CardResquestDto cardRequestDto = new CardResquestDto();
        // Set fields for cardRequestDto

        when(cardController.insert(cardRequestDto)).thenReturn(MockMvcResultMatchers.status().isOk().build());

        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCard() throws Exception {
        String cpf = "21910056081";
        String numero = "5568872479420825";
        String data = "0625";
        String cvv = "545";

        when(cardController.findByCardNumber(cpf, numero, data, cvv)).thenReturn(MockMvcResultMatchers.status().isOk().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/cards")
                        .param("cpf", cpf)
                        .param("numero", numero)
                        .param("data", data)
                        .param("cvv", cvv))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCard() throws Exception {
        CardTransactionRequestDto transactionRequestDto = new CardTransactionRequestDto();
        // Set fields for transactionRequestDto

        when(cardTransactionController.createTransaction(transactionRequestDto)).thenReturn(MockMvcResultMatchers.status().isOk().build());

        mockMvc.perform(MockMvcRequestBuilders.post("/cards/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDto)))
                .andExpect(status().isOk());
    }
}

