package org.appsugar.integration.projectloom.experimental.embedded;

import java.util.Objects;
import java.util.concurrent.Executor;

public class VirtualThreadBuilder {
    public static Thread buildVirtualThread(Executor executor, Runnable task) {
        Thread.Builder builder = Thread.builder().name("VirtualThread-" + Thread.currentThread().getName());
        builder.inheritThreadLocals();
        builder.task(task);
        if (Objects.isNull(executor)) {
            builder.virtual();
        } else {
            builder.virtual(executor);
        }
        return builder.build();
    }
}
