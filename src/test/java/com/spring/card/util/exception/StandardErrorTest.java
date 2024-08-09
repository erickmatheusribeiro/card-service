package com.spring.card.util.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardErrorTest {

    @Test
    public void testNoArgsConstructor() {
        // Teste do construtor sem argumentos (NoArgsConstructor)
        StandardError standardError = new StandardError();
        assertNotNull(standardError);
    }

    @Test
    public void testAllArgsConstructor() {
        // Teste do construtor com todos os argumentos (AllArgsConstructor)
        StandardError standardError = new StandardError(404, "Not Found", "The resource was not found", "/api/resource");

        assertEquals(404, standardError.getStatusCode());
        assertEquals("Not Found", standardError.getError());
        assertEquals("The resource was not found", standardError.getMessage());
        assertEquals("/api/resource", standardError.getPath());
    }

    @Test
    public void testGettersAndSetters() {
        // Teste dos getters e setters
        StandardError standardError = new StandardError();

        standardError.setStatusCode(500);
        standardError.setError("Internal Server Error");
        standardError.setMessage("An unexpected error occurred");
        standardError.setPath("/api/resource");

        assertEquals(500, standardError.getStatusCode());
        assertEquals("Internal Server Error", standardError.getError());
        assertEquals("An unexpected error occurred", standardError.getMessage());
        assertEquals("/api/resource", standardError.getPath());
    }
}

