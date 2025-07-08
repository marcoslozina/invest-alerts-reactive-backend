package com.marcoslozina.investalerts.domain.port;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import reactor.core.publisher.Flux;

public interface AlertNotifierPort {
    Flux<AlertPrice> streamTriggeredAlerts();
}
