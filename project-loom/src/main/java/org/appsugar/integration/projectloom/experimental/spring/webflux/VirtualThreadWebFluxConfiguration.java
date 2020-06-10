package org.appsugar.integration.projectloom.experimental.spring.webflux;

import org.appsugar.integration.projectloom.experimental.embedded.VirtualExecutorConfiguration;
import org.appsugar.integration.projectloom.experimental.embedded.VirtualThreadExecutorProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动配置各容器对应的filter
 * 支持project loom
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(VirtualThreadServerProperties.class)
@ConditionalOnClass(ContinuationScope.class)
@Import(VirtualExecutorConfiguration.class)
public class VirtualThreadWebFluxConfiguration {

    @ConditionalOnBean(VirtualThreadExecutorProvider.class)
    @Bean
    public VirtualThreadWebFilter nettyVirtualThreadWebFilter() {
        return new VirtualThreadWebFilter();
    }


}
