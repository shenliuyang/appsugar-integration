package org.appsugar.integration.projectloom.experimental.embedded;

import io.undertow.Undertow;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xnio.SslClientAuthMode;
import reactor.netty.http.server.HttpServer;

/**
 * 平台执行器配置
 * Netty:NettyExecutor
 * Undertow:UndertowExecutor
 * Tomcat:UnconfinedExecutor
 */
@ConditionalOnClass(ContinuationScope.class)
@Configuration(proxyBeanMethods = false)
public class ExecutorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public ExecutorProvider unconfinedPlatformExecutorProvider() {
        return new UnconfinedExecutor.UncondfinedExecutorProvider();
    }

    /**
     * 配置netty
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HttpServer.class)
    public static class NettyVirtualThreadWebFilterConfiguration {
        @Bean
        public ExecutorProvider nettyPlatformExecutorProvider() {
            return new NettyExecutor.NettyPlatformEventExecutorProvider();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({Undertow.class, SslClientAuthMode.class})
    public static class UndertowVirtualThreadWebFilterConfiguration {
        @Bean
        public ExecutorProvider undertowPlatofrExecutorProvider() {
            return new UndertowExecutorProvider();
        }
    }
}
