package org.appsugar.integration.projectloom.experimental.spring.webflux.embedded;

import org.appsugar.integration.projectloom.experimental.VirtualThreadConfiguration;
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
    private VirtualThreadConfiguration.VirtualThreadExecutor execute;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.create(sink -> {
            execute.execute(() -> chain.filter(exchange)
                    .subscriberContext(sink.currentContext())
                    .subscribe(r -> sink.success(), r -> sink.error(r)));
        });
    }

}
