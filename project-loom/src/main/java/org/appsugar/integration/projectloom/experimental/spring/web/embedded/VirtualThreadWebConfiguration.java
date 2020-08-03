package org.appsugar.integration.projectloom.experimental.spring.web.embedded;

import jdk.internal.misc.VirtualThreads;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.appsugar.integration.projectloom.experimental.VirtualThreadConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ConditionalOnClass({EnableWebMvc.class, VirtualThreads.class})
@ConditionalOnBean(DispatcherServlet.class)
@Configuration
public class VirtualThreadWebConfiguration {

    /**
     * 配置tomcat使用无边界虚拟线程池
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(Tomcat.class)
    public static class TomcatVirtualThreadWebConfiguration {
        @Bean
        public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryWebServerFactoryCustomizer(VirtualThreadConfiguration.VirtualThreadExecutor executor) {
            return new WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory>() {
                @Override
                public void customize(ConfigurableTomcatWebServerFactory factory) {
                    factory.addConnectorCustomizers((connector) -> {
                        ProtocolHandler handler = connector.getProtocolHandler();
                        if (handler instanceof AbstractProtocol) {
                            AbstractProtocol protocol = (AbstractProtocol) handler;
                            protocol.setExecutor(executor);
                        }
                    });
                }
            };
        }
    }
}
