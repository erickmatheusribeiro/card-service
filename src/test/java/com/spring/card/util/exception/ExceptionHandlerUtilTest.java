package com.spring.card.util.exception;

import com.spring.card.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionHandlerUtilTest {
//
//    @Test
//    public void testGenericExceptions() {
//        // Arrange
//        ExceptionHandlerUtil exceptionHandlerUtil = new ExceptionHandlerUtil();
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        String message = "ERROR_MESSAGE";
//        String expectedMessage = "This is an error message";
//        String expectedPath = "/api/cartao";
//
//        // Mock MessageUtil to return a custom message for the given code
//        MessageUtil messageUtil = org.mockito.Mockito.mock(MessageUtil.class);
//        org.mockito.Mockito.when(MessageUtil.getMessage(message)).thenReturn(expectedMessage);
//
//        // Act
//        ResponseEntity<?> response = exceptionHandlerUtil.genericExceptions(httpStatus, message);
//
//        // Assert
//        assertEquals(httpStatus.value(), ((StandardError) response.getBody()).getStatusCode());
//        assertEquals(expectedMessage, ((StandardError) response.getBody()).getMessage());
//        assertEquals(message, ((StandardError) response.getBody()).getError());
//        assertEquals(expectedPath, ((StandardError) response.getBody()).getPath());
//    }
}

