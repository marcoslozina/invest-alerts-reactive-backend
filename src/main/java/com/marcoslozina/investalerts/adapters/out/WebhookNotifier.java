package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AlertResult;
import com.marcoslozina.investalerts.domain.port.AlertNotificationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookNotifier implements AlertNotificationPort {

    private final WebClient webClient;

    @Value("${notifier.webhook.url:}")
    private String webhookUrl;

    @Override
    public Mono<Void> notify(AlertResult alertResult) {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            log.warn("🔕 Webhook no configurado, se omite.");
            return Mono.empty();
        }

        return webClient.post()
            .uri(webhookUrl)
            .bodyValue(alertResult)
            .retrieve()
            .bodyToMono(Void.class)
            .doOnSuccess(v -> log.info("📤 Webhook enviado correctamente para alerta {}", alertResult.symbol()))
            .doOnError(e -> log.error("❌ Error al enviar webhook: {}", e.getMessage(), e));
    }
}

