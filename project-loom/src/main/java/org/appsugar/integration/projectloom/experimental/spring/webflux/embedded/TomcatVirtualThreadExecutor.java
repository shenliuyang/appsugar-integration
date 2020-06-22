package org.appsugar.integration.projectloom.experimental.spring.webflux.embedded;

import java.util.concurrent.Executor;

public class TomcatVirtualThreadExecutor extends VirtualThreadExecutor {

    @Override
    public Executor getExecutor() {
        return null;
    }

}
