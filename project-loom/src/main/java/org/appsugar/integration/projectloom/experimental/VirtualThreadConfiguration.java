package org.appsugar.integration.projectloom.experimental;

import org.appsugar.integration.projectloom.experimental.kafka.VirtualThreadKafkaConfiguration;
import org.appsugar.integration.projectloom.experimental.spring.web.embedded.VirtualThreadWebConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ConditionalOnClass(name = {"jdk.internal.misc.VirtualThreads"})
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "project.loom", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({VirtualThreadWebConfiguration.class, VirtualThreadKafkaConfiguration.class})
public class VirtualThreadConfiguration {

    @ConditionalOnMissingBean(VirtualThreadExecutor.class)
    @Bean
    public VirtualThreadExecutor virtualThreadExecutor() {
        return new VirtualThreadExecutor();
    }

    public static class VirtualThreadExecutor implements Executor {
        protected Executor executor = Executors.newVirtualThreadPerTaskExecutor();

        @Override
        public void execute(Runnable command) {
            executor.execute(command);
        }
    }

}
