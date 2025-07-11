package com.marcoslozina.investalerts.domain.repository;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlertRepository {
    Mono<Void> save(AlertPrice alert);
    Flux<AlertPrice> findAll();
}
