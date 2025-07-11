package com.marcoslozina.investalerts;

import com.marcoslozina.investalerts.domain.port.AlertNotifierPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ApplicationTest {

    @MockBean
    private AssetPriceProviderPort assetPriceProviderPort;

    @MockBean
    private AlertNotifierPort alertNotifierPort;

    @Test
    void contextLoadsSuccessfully() {
        // Verifica que el contexto arranca sin errores
    }
}
