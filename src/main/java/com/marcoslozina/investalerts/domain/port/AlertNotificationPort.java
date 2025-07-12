package com.marcoslozina.investalerts.domain.port;


import com.marcoslozina.investalerts.domain.model.AlertResult;
import reactor.core.publisher.Mono;

public interface AlertNotificationPort {
    Mono<Void> notify(AlertResult alertResult);
}
