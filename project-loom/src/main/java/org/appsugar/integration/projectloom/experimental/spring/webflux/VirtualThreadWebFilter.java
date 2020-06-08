package org.appsugar.integration.projectloom.experimental.spring.webflux;

import org.appsugar.integration.projectloom.experimental.embedded.ExecutorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * 在spring-webflux中增加对project-loom的支持
 * 在不改变现有代码的情况下,提高系统的处理能力.
 */
public class VirtualThreadWebFilter implements WebFilter {
    private static final Logger logger = LoggerFactory.getLogger(VirtualThreadWebFilter.class);

    @Autowired
    private VirtualThreadServerProperties properties;

    @Autowired
    private ExecutorProvider platformExecutorProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Executor executor = platformExecutorProvider.getOrCreateCurrentExecutor();
        if (properties.isStrict() && Objects.isNull(executor)) {
            return Mono.error(new RuntimeException("Current Thread " + Thread.currentThread().getName() + " get Platform Executor failed," +
                    "set server.strict=false to use System Executor instead of platform Executor"));
        }
        String parentThreadName = Thread.currentThread().getName();
        return Mono.create(sink -> {
            Thread.Builder builder = Thread.builder().name("VirtualThread-" + parentThreadName).inheritThreadLocals();
            if (Objects.nonNull(executor) && !platformExecutorProvider.useDefaultDispatcher()) {
                builder.virtual(executor);
            } else {
                builder.virtual();
            }
            builder.task(() -> {
                chain.filter(exchange).subscriberContext(sink.currentContext()).subscribe(r -> sink.success(r), (r) -> sink.error(r));
            });
            Thread fiber = builder.build();
            fiber.start();
        });
    }
}
