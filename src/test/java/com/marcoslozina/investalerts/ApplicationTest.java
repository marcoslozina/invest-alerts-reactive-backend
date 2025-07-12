package com.marcoslozina.investalerts;

import com.marcoslozina.investalerts.domain.port.AlertNotificationPort;
import com.marcoslozina.investalerts.domain.port.AlertNotifierPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ApplicationTest {

    @MockBean
    private AssetPriceProviderPort assetPriceProviderPort;

    @MockBean(name = "telegramNotifier")
    private AlertNotificationPort telegramNotifier;

    @MockBean(name = "webhookNotifier")
    private AlertNotificationPort webhookNotifier;

    @MockBean
    private AlertNotifierPort alertNotifierPort; // ← este es el nuevo requerido

    @Test
    void contextLoadsSuccessfully() {
        // El test pasa si el contexto carga sin errores
    }
}
