package org.appsugar.integration.projectloom.experimental.spring.webflux.embedded;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 使用WebFlux时,转发所有请求到轻量级线程中
 */
public class VirtualThreadWebFilter implements WebFilter {

    private Executor execute = Executors.newUnboundedVirtualThreadExecutor();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.create(sink -> {
            execute.execute(() -> chain.filter(exchange)
                    .subscriberContext(sink.currentContext())
                    .subscribe(r -> sink.success(), r -> sink.error(r)));
        });
    }

}
