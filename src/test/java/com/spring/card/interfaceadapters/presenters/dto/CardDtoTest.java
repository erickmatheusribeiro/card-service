package com.spring.card.interfaceadapters.presenters.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardDtoTest {

    @Test
    public void testNoArgsConstructor() {
        // Teste do construtor sem argumentos (NoArgsConstructor)
        CardDto cardDto = new CardDto();
        assertNotNull(cardDto);
    }

    @Test
    public void testAllArgsConstructor() {
        // Teste do construtor com todos os argumentos (AllArgsConstructor)
        CardDto cardDto = new CardDto("user123", "12345678901", "5568872479420825", "0625", "545");

        assertEquals("user123", cardDto.getUsers());
        assertEquals("12345678901", cardDto.getCpf());
        assertEquals("5568872479420825", cardDto.getNumero());
        assertEquals("0625", cardDto.getData_validade());
        assertEquals("545", cardDto.getCvv());
    }

    @Test
    public void testGettersAndSetters() {
        // Teste dos getters e setters
        CardDto cardDto = new CardDto();

        cardDto.setUsers("user123");
        cardDto.setCpf("12345678901");
        cardDto.setNumero("5568872479420825");
        cardDto.setData_validade("0625");
        cardDto.setCvv("545");

        assertEquals("user123", cardDto.getUsers());
        assertEquals("12345678901", cardDto.getCpf());
        assertEquals("5568872479420825", cardDto.getNumero());
        assertEquals("0625", cardDto.getData_validade());
        assertEquals("545", cardDto.getCvv());
    }
}