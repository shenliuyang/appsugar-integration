package org.appsugar.integration.projectloom.experimental.spring.web.embedded;

import java.util.concurrent.Executor;

/**
 * 虚拟线程执行器
 */
public abstract class VirtualThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        Thread.Builder builder = Thread.builder().name("VirtualThread-" + Thread.currentThread().getName());
        Executor executor = getExecutor();
        if (executor == null) {
            builder.virtual();
        } else {
            builder.virtual(getExecutor());
        }
        builder.inheritThreadLocals();
        builder.task(command);
        builder.start();
    }

    public abstract Executor getExecutor();
}
