package com.spring.card.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CpfValidatorTest {

    @Test
    public void testValidCpf() {
        // CPF válido: 123.456.789-09
        String cpf = "12345678909";
        assertTrue(CpfValidator.isValid(cpf), "CPF should be valid");
    }

    @Test
    public void testInvalidCpfWithIncorrectDigits() {
        // CPF inválido (dígitos verificadores errados)
        String cpf = "12345678900";
        assertFalse(CpfValidator.isValid(cpf), "CPF should be invalid");
    }

    @Test
    public void testInvalidCpfWithAllDigitsSame() {
        // CPF inválido (todos os dígitos iguais)
        String cpf = "11111111111";
        assertFalse(CpfValidator.isValid(cpf), "CPF with all digits the same should be invalid");
    }

    @Test
    public void testInvalidCpfWithLessThan11Digits() {
        // CPF inválido (menos de 11 dígitos)
        String cpf = "1234567890";
        assertFalse(CpfValidator.isValid(cpf), "CPF with less than 11 digits should be invalid");
    }

    @Test
    public void testInvalidCpfWithMoreThan11Digits() {
        // CPF inválido (mais de 11 dígitos)
        String cpf = "123456789012";
        assertFalse(CpfValidator.isValid(cpf), "CPF with more than 11 digits should be invalid");
    }

    @Test
    public void testInvalidCpfWithNull() {
        // CPF inválido (nulo)
        String cpf = null;
        assertFalse(CpfValidator.isValid(cpf), "Null CPF should be invalid");
    }

    @Test
    public void testInvalidCpfWithSpecialCharacters() {
        // CPF inválido (contém caracteres especiais)
        String cpf = "123.456.789-09";
        assertFalse(CpfValidator.isValid(cpf), "CPF with special characters should be invalid");
    }
}
