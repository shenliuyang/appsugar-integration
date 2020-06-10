package org.appsugar.integration.projectloom.experimental.embedded;

import io.netty.bootstrap.ServerBootstrap;
import io.undertow.Undertow;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xnio.SslClientAuthMode;

/**
 * 平台执行器配置
 * Netty:NettyExecutor
 * Undertow:UndertowExecutor
 * Tomcat:UnconfinedExecutor
 */
@ConditionalOnClass(ContinuationScope.class)
@Configuration(proxyBeanMethods = false)
public class VirtualExecutorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public VirtualThreadExecutorProvider unconfinedPlatformExecutorProvider() {
        return new VirtualThreadDefaultExecutor.UncondfinedExecutorProvider();
    }

    /**
     * 配置netty
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(ServerBootstrap.class)
    public static class NettyVirtualThreadWebFilterConfiguration {
        @Bean
        public VirtualThreadExecutorProvider nettyPlatformExecutorProvider() {
            return new VirtualThreadNettyExecutor.NettyPlatformEventExecutorProvider();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({Undertow.class, SslClientAuthMode.class})
    public static class UndertowVirtualThreadWebFilterConfiguration {
        @Bean
        public VirtualThreadExecutorProvider undertowPlatofrExecutorProvider() {
            return new VirtualUndertowExecutorProvider();
        }
    }
}
