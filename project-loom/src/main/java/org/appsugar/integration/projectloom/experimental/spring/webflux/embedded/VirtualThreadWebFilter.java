package org.appsugar.integration.projectloom.experimental.spring.webflux.embedded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 使用WebFlux时,转发所有请求到轻量级线程中
 */
public class VirtualThreadWebFilter implements WebFilter {
    @Autowired
    private VirtualThreadExecutor virtualThreadExecutor;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.create(sink -> {
            virtualThreadExecutor.execute(() -> chain.filter(exchange)
                    .subscriberContext(sink.currentContext())
                    .subscribe(r -> sink.success(), r -> sink.error(r)));
        });
    }

}
