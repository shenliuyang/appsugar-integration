package org.appsugar.integration.projectloom.experimental.spring.webflux;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 服务配置
 */
@ConfigurationProperties(prefix = "server")
public class VirtualThreadServerProperties {
    /**
     * strict模式,在web filter 转发前,确保线程正确.
     */
    private boolean strict = true;

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }
}
