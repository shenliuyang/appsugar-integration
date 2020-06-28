package org.appsugar.integration.projectloom.experimental.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadpool.ThreadPool;

import java.util.concurrent.Executor;

/**
 * 虚拟线程池
 */
public class VirtualThreadPool implements ThreadPool {

    private Executor executor = new VirtualThreadExecutor();

    @Override
    public Executor getExecutor(URL url) {
        return executor;
    }
}

class VirtualThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        Thread current = Thread.currentThread();
        if (current.isVirtual()) {
            command.run();
            return;
        }
        Thread builder = Thread.builder().name("VirtualThread-" + current.getName()).inheritThreadLocals().virtual().task(command).build();
        builder.start();
    }
}