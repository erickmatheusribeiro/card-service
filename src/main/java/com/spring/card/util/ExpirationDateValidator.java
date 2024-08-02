package com.spring.card.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class ExpirationDateValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMyy");

    public static boolean isValid(String expirationDate) {
        // Verifica se a string contém apenas números e tem exatamente 4 caracteres
        if (expirationDate == null || !Pattern.matches("\\d{4}", expirationDate)) {
            return false;
        }

        // Extrai o mês e o ano da string
        int month = Integer.parseInt(expirationDate.substring(0, 2));
        int year = Integer.parseInt(expirationDate.substring(2));

        // Verifica se o mês está no intervalo válido
        if (month < 1 || month > 12) {
            return false;
        }

        // Obtém o mês e ano atual
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear() % 100; // Obtém os dois últimos dígitos do ano

        // Compara com a data atual
        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            return false;
        }

        return true;
    }
}
