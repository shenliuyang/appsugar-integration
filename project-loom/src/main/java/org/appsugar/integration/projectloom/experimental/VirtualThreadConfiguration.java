package org.appsugar.integration.projectloom.experimental;

import jdk.internal.misc.VirtualThreads;
import org.appsugar.integration.projectloom.experimental.kafka.VirtualThreadKafkaConfiguration;
import org.appsugar.integration.projectloom.experimental.spring.web.embedded.VirtualThreadWebConfiguration;
import org.appsugar.integration.projectloom.experimental.spring.webflux.embedded.VirtualThreadWebFluxReactiveConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ConditionalOnClass({VirtualThreads.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "project.loom", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({VirtualThreadWebFluxReactiveConfiguration.class, VirtualThreadWebConfiguration.class, VirtualThreadKafkaConfiguration.class})
public class VirtualThreadConfiguration {

    @ConditionalOnMissingBean(VirtualThreadExecutor.class)
    @Bean
    public VirtualThreadExecutor virtualThreadExecutor() {
        return new VirtualThreadExecutor();
    }

    public static class VirtualThreadExecutor implements Executor {
        protected Executor executor = Executors.newUnboundedVirtualThreadExecutor();

        @Override
        public void execute(Runnable command) {

        }
    }

}
