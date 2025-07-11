package com.marcoslozina.investalerts.adapters.in.rest;

import com.marcoslozina.investalerts.application.AssetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(PublicController.class)
@Import(TestSecurityConfig.class)
public class PublicControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private AssetService assetService;

    @Test
    void helloPublicReturnsExpectedMessage() {
        webTestClient.get()
            .uri("/public/hello")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("👋 Hola desde endpoint público (sin auth)");
    }
}
