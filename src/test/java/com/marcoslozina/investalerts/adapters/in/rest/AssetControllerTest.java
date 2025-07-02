package com.marcoslozina.investalerts.adapters.in.rest;

import com.marcoslozina.investalerts.adapters.out.PriceApiClient;
import com.marcoslozina.investalerts.application.AssetService;
import com.marcoslozina.investalerts.domain.model.AssetPrice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers = AssetController.class)
class AssetControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AssetService assetService;

    @Test
    void getPriceReturnsExpectedAssetPrice() {
        // Arrange
        String symbol = "BTC";
        AssetPrice mockPrice = new AssetPrice(symbol, BigDecimal.valueOf(54321.00), Instant.parse("2025-07-02T10:15:30Z"));

        Mockito.when(assetService.getPrice(eq(symbol)))
            .thenReturn(Mono.just(mockPrice));

        // Act & Assert
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/assets/price")
                .queryParam("symbol", symbol)
                .build())
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.symbol").isEqualTo("BTC")
            .jsonPath("$.price").isEqualTo(54321.00)
            .jsonPath("$.timestamp").isEqualTo("2025-07-02T10:15:30Z");
    }

    @Test
    void getCurrentPriceReturnsParsedAssetPrice() {
        String jsonBody = """
        {
          "bitcoin": {
            "usd": 12345.67
          }
        }
    """;

        WebClient webClient = WebClient.builder()
            .exchangeFunction(clientRequest -> Mono.just(
                ClientResponse.create(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .build()
            ))
            .build();

        PriceApiClient client = new PriceApiClient(webClient);
        AssetPrice price = client.getCurrentPrice("bitcoin").block();

        assertEquals("BITCOIN", price.getSymbol());
        assertEquals(new BigDecimal("12345.67"), price.getPrice());
    }
}
