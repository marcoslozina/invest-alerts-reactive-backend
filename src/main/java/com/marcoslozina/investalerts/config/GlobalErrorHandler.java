package com.marcoslozina.investalerts.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<String> handleWebClientRequest(WebClientRequestException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
            .body("Error de red al contactar proveedor externo.");
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponse(WebClientResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode())
            .body("Error de respuesta del proveedor externo: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error inesperado: " + ex.getMessage());
    }
}
