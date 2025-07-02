package com.marcoslozina.investalerts.adapters.in.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = AdminController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class AdminControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnHelloAdmin() {
        webTestClient
            .get()
            .uri("/admin/hello")
            .headers(headers -> headers.setBasicAuth("admin", "admin")) // usuario con rol ADMIN
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Hello Admin 👋");
    }
}
