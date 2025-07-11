package com.marcoslozina.investalerts.config;


import com.marcoslozina.investalerts.domain.port.AlertNotifierPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class SecurityConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private AlertNotifierPort alertNotifierPort;

    @MockBean
    private AssetPriceProviderPort assetPriceProviderPort;

    @MockBean
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @Test
    void publicEndpointAccessibleWithoutAuth() {
        webTestClient.get()
            .uri("/swagger-ui.html")
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", ".*/webjars/swagger-ui/index.html.*");
    }

    @Test
    void secureEndpointUnauthorizedWithoutToken() {
        webTestClient.get()
            .uri("/secure/hello")
            .exchange()
            .expectStatus().isUnauthorized();
    }
}
