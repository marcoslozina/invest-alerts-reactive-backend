package com.marcoslozina.investalerts.adapters.out;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

class PriceApiClientIT {

    static MockWebServer mockWebServer;
    static PriceApiClient client;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();

        // Encola la respuesta simulada
        mockWebServer.enqueue(new MockResponse()
            .setHeader("Content-Type", "application/json")
            .setBody("{\"symbol\":\"BTC\",\"price\":30000.0,\"timestamp\":\"2024-01-01T00:00:00Z\"}"));

        mockWebServer.start();

        // Creamos el WebClient apuntando al mock
        String baseUrl = mockWebServer.url("/").toString();
        WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();

        client = new PriceApiClient(webClient);
    }

    @AfterAll
    static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldReturnPriceFromMockApi() {
        StepVerifier.create(client.getCurrentPrice("BTC"))
            .expectNext(30000.0)
            .verifyComplete();
    }
}
