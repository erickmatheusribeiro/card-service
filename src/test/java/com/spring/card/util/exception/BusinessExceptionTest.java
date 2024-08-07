package com.spring.card.util.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusinessExceptionTest {

    @Test
    public void testBusinessExceptionMessage() {
        // Define o código de erro que será usado para criar a exceção
        String errorCode = "ERROR_CODE";

        // Simula o comportamento esperado do MessageUtil.getMessage(code)
        // Aqui você pode definir o que a sua função MessageUtil.getMessage(code) deve retornar
        String expectedMessage = "Expected error message for the code"; // Substitua pela mensagem esperada

        // Gera a exceção com o código de erro
        BusinessException exception = new BusinessException(errorCode);

        // Verifica se a mensagem da exceção é a esperada
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected message");
    }
}

