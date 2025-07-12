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
public class TelegramNotifier implements AlertNotificationPort {

    private final WebClient webClient;

    @Value("${notifier.telegram.bot-token:}")
    private String botToken;

    @Value("${notifier.telegram.chat-id:}")
    private String chatId;

    @Override
    public Mono<Void> notify(AlertResult alertResult) {
        if (botToken == null || botToken.isBlank() || chatId == null || chatId.isBlank()) {
            log.warn("🔕 Telegram no configurado, se omite.");
            return Mono.empty();
        }

        String message = String.format(
            "🚨 Alerta activa para %s\nPrecio actual: %.2f\nUmbral: %.2f\nTipo: %s",
            alertResult.symbol(),
            alertResult.price(),
            alertResult.threshold(),
            alertResult.triggered() ? "DISPARADA" : "NO DISPARADA"
        );

        return webClient.post()
            .uri("https://api.telegram.org/bot{token}/sendMessage", botToken)
            .bodyValue(new TelegramMessage(chatId, message))
            .retrieve()
            .bodyToMono(Void.class)
            .doOnSuccess(v -> log.info("📩 Mensaje Telegram enviado para {}", alertResult.symbol()))
            .doOnError(e -> log.error("❌ Error enviando a Telegram: {}", e.getMessage(), e));
    }

    private record TelegramMessage(String chat_id, String text) {}
}
