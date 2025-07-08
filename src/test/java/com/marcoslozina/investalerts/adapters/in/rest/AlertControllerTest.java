package com.marcoslozina.investalerts.adapters.in.rest;

import com.marcoslozina.investalerts.adapters.in.web.dto.PriceAlertRequest;
import com.marcoslozina.investalerts.domain.model.AlertPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.marcoslozina.investalerts.application.RegisterAlertService;

import static org.mockito.Mockito.*;

@WebFluxTest(AlertController.class)
public class AlertControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RegisterAlertService registerAlertService;

    @Test
    void shouldRegisterAlert() {
        PriceAlertRequest request = new PriceAlertRequest();
        request.symbol = "BTC";
        request.threshold = 70000;
        request.type = AlertPrice.AlertType.GREATER_THAN;

        AlertPrice alert = new AlertPrice("BTC", 70000, AlertPrice.AlertType.GREATER_THAN);
        when(registerAlertService.register("BTC", 70000, AlertPrice.AlertType.GREATER_THAN)).thenReturn(alert);

        webTestClient.post()
            .uri("/alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.message").isEqualTo("Alerta registrada correctamente")
            .jsonPath("$.alert.symbol").isEqualTo("BTC")
            .jsonPath("$.alert.threshold").isEqualTo(70000.0)
            .jsonPath("$.alert.type").isEqualTo("GREATER_THAN");

        verify(registerAlertService, times(1)).register("BTC", 70000, AlertPrice.AlertType.GREATER_THAN);
    }
}
