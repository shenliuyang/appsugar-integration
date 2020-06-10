package org.appsugar.integration.projectloom.experimental.embedded;

import java.util.concurrent.Executor;

/**
 * 平台化轻量级线程执行提供者
 * Netty,UnderTow,Tomcat
 */
public interface VirtualThreadExecutorProvider {
    /**
     * 获取当前线程下的执行器,如果未设置,返回null
     */
    static Executor currentExecutor() {
        return ExecutorVariables.platformExecutorThreadLocal.get();
    }

    /**
     * 把执行器设置到当前线程上
     * 如果当前线程为平台线程,将会抛出UnsupportedOperationException
     */
    static void setCurrentExecutor(Executor executor) {
        ExecutorVariables.platformExecutorThreadLocal.set(executor);
    }

    /**
     * 移除当前线程上的执行器
     * 如果当前线程为平台线程,将会抛出UnsupportedOperationException
     */
    static void removeCurrentExecutor() {
        ExecutorVariables.platformExecutorThreadLocal.remove();
    }

    /**
     * 获取当前线程上的平台执行器
     */
    Executor getOrCreateCurrentExecutor();


}

class ExecutorVariables {
    static final ThreadLocal<Executor> platformExecutorThreadLocal = new ThreadLocal<>();
}