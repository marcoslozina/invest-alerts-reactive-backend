package com.marcoslozina.investalerts.config;

import com.marcoslozina.investalerts.adapters.out.PriceApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@WebFluxTest(controllers = LocalSecurityConfigTest.DummyController.class)
@ActiveProfiles("local")
@Import(LocalSecurityConfig.class)
public class LocalSecurityConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @RestController
    static class DummyController {
        @GetMapping("/secure/hello")
        public String hello() {
            return "🔐 Hola desde endpoint seguro (con JWT)";
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DummyController dummyController() {
            return new DummyController();
        }
    }

    @Test
    void testAccessAllowedInLocalProfile() {
        webTestClient.get()
            .uri("/secure/hello")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("🔐 Hola desde endpoint seguro (con JWT)");
    }
}
