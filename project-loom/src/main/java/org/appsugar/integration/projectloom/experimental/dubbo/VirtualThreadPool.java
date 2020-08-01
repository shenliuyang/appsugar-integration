package org.appsugar.integration.projectloom.experimental.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadpool.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 虚拟线程池
 */
public class VirtualThreadPool implements ThreadPool {
    @Override
    public Executor getExecutor(URL url) {
        return Executors.newUnboundedVirtualThreadExecutor();
    }
}
