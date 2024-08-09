package com.spring.card.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpirationDateValidatorTest {

    @Test
    public void testValidExpirationDate() {
        // Testa um caso válido no futuro
        assertTrue(ExpirationDateValidator.isValid("1225"));
    }

    @Test
    public void testValidExpirationDateCurrentMonth() {
        // Testa um caso válido com o mês atual
        LocalDate now = LocalDate.now();
        String currentMonthYear = String.format("%02d%02d", now.getMonthValue(), now.getYear() % 100);
        assertTrue(ExpirationDateValidator.isValid(currentMonthYear));
    }

    @Test
    public void testInvalidExpirationDateMonthTooLow() {
        // Testa um caso com mês menor que 1
        assertFalse(ExpirationDateValidator.isValid("0000"));
    }

    @Test
    public void testInvalidExpirationDateMonthTooHigh() {
        // Testa um caso com mês maior que 12
        assertFalse(ExpirationDateValidator.isValid("1310"));
    }

    @Test
    public void testInvalidExpirationDateYearTooLow() {
        // Testa um caso com ano menor que o ano atual
        LocalDate now = LocalDate.now();
        String pastYear = String.format("%02d%02d", now.getMonthValue(), now.getYear() % 100 - 1);
        assertFalse(ExpirationDateValidator.isValid(pastYear));
    }

    @Test
    public void testInvalidExpirationDateFormat() {
        // Testa casos com formato inválido
        assertFalse(ExpirationDateValidator.isValid("12/25"));
        assertFalse(ExpirationDateValidator.isValid("25/12"));
        assertFalse(ExpirationDateValidator.isValid("2025"));
        assertFalse(ExpirationDateValidator.isValid("123"));
        assertFalse(ExpirationDateValidator.isValid("abcd"));
    }

    @Test
    public void testNullExpirationDate() {
        // Testa caso com valor null
        assertFalse(ExpirationDateValidator.isValid(null));
    }
}

