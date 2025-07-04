package com.marcoslozina.investalerts.adapters.out;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

class PriceApiClientTest {

    private static MockWebServer mockWebServer;
    private static PriceApiClient priceApiClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
            .baseUrl(mockWebServer.url("/").toString()) // Important: points to the mock server
            .build();

        priceApiClient = new PriceApiClient(webClient); // Uses injected mock client
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getCurrentPriceReturnsParsedAssetPrice() {
        String symbol = "BTC";
        String apiResponse = """
        {
          "BTC": {
            "usd": 62000.00
          }
        }
        """;

        // Enqueue mock response
        mockWebServer.enqueue(new MockResponse()
            .setBody(apiResponse)
            .addHeader("Content-Type", "application/json"));

        StepVerifier.create(priceApiClient.getCurrentPrice(symbol))
            .expectNextMatches(assetPrice ->
                assetPrice.getSymbol().equals("BTC") &&
                    assetPrice.getPrice().compareTo(new BigDecimal("62000.00")) == 0 &&
                    assetPrice.getTimestamp().isBefore(Instant.now().plusSeconds(5)))
            .verifyComplete();
    }
}
