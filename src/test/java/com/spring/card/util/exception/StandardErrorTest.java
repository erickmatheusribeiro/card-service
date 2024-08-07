package com.spring.card.util.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardErrorTest {

    @Test
    public void testStandardErrorConstructor() {
        // Dados de entrada para o construtor
        int expectedStatusCode = 500;
        String expectedError = "UNREPORTED_DATA_ERROR";
        String expectedMessage = "An error occurred";
        String expectedPath = "/api/cartao";

        // Criação da instância de StandardError usando o construtor
        StandardError standardError = new StandardError(expectedStatusCode, expectedError, expectedMessage, expectedPath);

        // Verificação dos valores dos campos
        assertEquals(expectedStatusCode, standardError.getStatusCode(), "Status code should match");
        assertEquals(expectedError, standardError.getError(), "Error should match");
        assertEquals(expectedMessage, standardError.getMessage(), "Message should match");
        assertEquals(expectedPath, standardError.getPath(), "Path should match");
    }
}

