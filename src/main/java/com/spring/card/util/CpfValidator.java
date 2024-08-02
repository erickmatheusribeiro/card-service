package com.spring.card.util;

import java.util.regex.Pattern;

public class CpfValidator {
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");

    public static boolean isValid(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Calcula os dígitos verificadores
        int[] cpfArray = cpf.chars().map(Character::getNumericValue).toArray();
        int firstDigit = calculateDigit(cpfArray, 9);
        int secondDigit = calculateDigit(cpfArray, 10);

        return firstDigit == cpfArray[9] && secondDigit == cpfArray[10];
    }

    private static int calculateDigit(int[] cpf, int length) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += cpf[i] * (length + 1 - i);
        }
        int result = sum % 11;
        return result < 2 ? 0 : 11 - result;
    }
}

