package org.appsugar.integration.projectloom.experimental.kafka;

import org.appsugar.integration.projectloom.experimental.VirtualThreadConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.web.reactive.config.EnableWebFlux;

@ConditionalOnBean(ConcurrentKafkaListenerContainerFactoryConfigurer.class)
@ConditionalOnClass(value = {EnableWebFlux.class}, name = {"jdk.internal.misc.VirtualThreads"})
@Configuration(proxyBeanMethods = false)
public class VirtualThreadKafkaConfiguration {


    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory, KafkaProperties kafkaProperties
            , VirtualThreadConfiguration.VirtualThreadExecutor executor) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>() {
            @Override
            protected void initializeContainer(ConcurrentMessageListenerContainer<Object, Object> instance,
                                               KafkaListenerEndpoint endpoint) {
                ContainerProperties properties = instance.getContainerProperties();
                properties.setConsumerTaskExecutor(new SimpleAsyncTaskExecutor() {
                    @Override
                    protected void doExecute(Runnable task) {
                        executor.execute(task);
                    }
                });
                super.initializeContainer(instance, endpoint);
            }
        };
        configurer.configure(factory, kafkaConsumerFactory
                .getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties())));

        return factory;
    }
}
