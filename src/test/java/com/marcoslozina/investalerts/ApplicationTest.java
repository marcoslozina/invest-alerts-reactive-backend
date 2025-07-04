package com.marcoslozina.investalerts;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@SpringBootTest
@Import(ApplicationTest.AssetProviderTestConfig.class)
public class ApplicationTest {

    @Test
    void contextLoadsSuccessfully() {
        // Test vacío que verifica si el contexto de Spring Boot inicia sin errores
    }

    @TestConfiguration
    static class AssetProviderTestConfig {
        @Bean
        @Primary
        public AssetPriceProviderPort assetPriceProviderPort() {
            return mock(AssetPriceProviderPort.class);
        }
    }
}
