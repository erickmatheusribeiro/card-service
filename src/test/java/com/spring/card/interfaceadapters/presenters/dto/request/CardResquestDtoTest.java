package com.spring.card.interfaceadapters.presenters.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CardResquestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        // Arrange & Act
        CardResquestDto dto = new CardResquestDto();

        // Assert
        assertNull(dto.getCpf());
        assertEquals(0.0, dto.getLimite());
        assertNull(dto.getNumero());
        assertNull(dto.getData_validade());
        assertNull(dto.getCvv());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String cpf = "21910056081";
        double limite = 1000.0;
        String numero = "5568872479420825";
        String data_validade = "0625";
        String cvv = "545";

        // Act
        CardResquestDto dto = new CardResquestDto(cpf, limite, numero, data_validade, cvv);

        // Assert
        assertEquals(cpf, dto.getCpf());
        assertEquals(limite, dto.getLimite());
        assertEquals(numero, dto.getNumero());
        assertEquals(data_validade, dto.getData_validade());
        assertEquals(cvv, dto.getCvv());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        CardResquestDto dto = new CardResquestDto();
        String cpf = "21910056081";
        double limite = 1000.0;
        String numero = "5568872479420825";
        String data_validade = "0625";
        String cvv = "545";

        // Act
        dto.setCpf(cpf);
        dto.setLimite(limite);
        dto.setNumero(numero);
        dto.setData_validade(data_validade);
        dto.setCvv(cvv);

        // Assert
        assertEquals(cpf, dto.getCpf());
        assertEquals(limite, dto.getLimite());
        assertEquals(numero, dto.getNumero());
        assertEquals(data_validade, dto.getData_validade());
        assertEquals(cvv, dto.getCvv());
    }
}

