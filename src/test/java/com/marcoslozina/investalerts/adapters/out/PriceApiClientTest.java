package com.marcoslozina.investalerts.adapters.out;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

class PriceApiClientTest {

    @Test
    void shouldReturnParsedPriceFromApi() {
        String json = """
        {
          "symbol": "BTC",
          "price": 12345.67,
          "timestamp": "2025-07-09T10:00:00Z"
        }
        """;

        WebClient webClient = WebClient.builder()
            .exchangeFunction(clientRequest ->
                Mono.just(ClientResponse.create(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(json)
                    .build())
            )
            .build();

        PriceApiClient client = new PriceApiClient(webClient, new SimpleMeterRegistry());

        StepVerifier.create(client.getCurrentPrice("BTC"))
            .expectNextMatches(price -> price == 12345.67)
            .verifyComplete();
    }

    @Test
    void shouldHandleTimeoutGracefully() {
        WebClient webClient = WebClient.builder()
            .exchangeFunction(clientRequest ->
                Mono.delay(Duration.ofSeconds(3)) // simula timeout
                    .then(Mono.error(new RuntimeException("Timeout simulated")))
            )
            .build();

        PriceApiClient client = new PriceApiClient(webClient, new SimpleMeterRegistry());

        StepVerifier.create(client.getCurrentPrice("BTC"))
            .expectErrorMatches(e -> e.getMessage().contains("Timeout"))
            .verify();
    }

    @Test
    void shouldHandleHttpError() {
        WebClient webClient = WebClient.builder()
            .exchangeFunction(clientRequest ->
                Mono.just(ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("""
                        { "error": "internal server error" }
                    """)
                    .build())
            )
            .build();

        PriceApiClient client = new PriceApiClient(webClient, new SimpleMeterRegistry());

        StepVerifier.create(client.getCurrentPrice("BTC"))
            .expectErrorMatches(e -> e.getMessage().contains("500"))
            .verify();
    }

    @Test
    void shouldFailOnInvalidJson() {
        String malformedJson = """
            { "price": "NaN }
        """;

        WebClient webClient = WebClient.builder()
            .exchangeFunction(clientRequest ->
                Mono.just(ClientResponse.create(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(malformedJson)
                    .build())
            )
            .build();

        PriceApiClient client = new PriceApiClient(webClient, new SimpleMeterRegistry());

        StepVerifier.create(client.getCurrentPrice("BTC"))
            .expectError()
            .verify();
    }
}
