package com.spring.card.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.util.Properties;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MessageUtilTest {

    private Properties properties;

    @BeforeEach
    public void setUp() {
        properties = new Properties();
        properties.setProperty("greeting", "Hello, %s!");
        properties.setProperty("farewell", "Goodbye, %s.");
    }

    @Test
    public void testGetMessageWithValidCode() {
        String code = "VALIDATE_CODE";
        String expectedMessage = "Código Validado!";
        String message = MessageUtil.getMessage(code, "John");
        assertEquals(expectedMessage, message);
    }

    @Test
    public void testGetMessageWithInvalidCode() {
        String code = "invalidCode";
        String message = MessageUtil.getMessage(code);
        assertEquals(null, message);
    }

    @Test
    public void testGetMessageWithMultipleValues() {
        String code = "VALIDATE_CODE";
        String expectedMessage = "Código Validado!";
        String message = MessageUtil.getMessage(code, "John");
        assertEquals(expectedMessage, message);
    }

    @Test
    public void testNoArgsConstructor() throws Exception {
        // Verifica se a classe pode ser instanciada
        Constructor<MessageUtil> constructor = MessageUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true); // Torna o construtor acessível se for privado
        MessageUtil messageUtil = constructor.newInstance();
        assertNotNull(messageUtil, "MessageUtil instance should be created");
    }
}
