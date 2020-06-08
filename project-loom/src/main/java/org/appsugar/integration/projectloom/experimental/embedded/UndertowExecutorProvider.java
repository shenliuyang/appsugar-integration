package org.appsugar.integration.projectloom.experimental.embedded;

import org.xnio.XnioIoThread;

import java.util.concurrent.Executor;

public class UndertowExecutorProvider implements ExecutorProvider {

    @Override
    public Executor getOrCreateCurrentExecutor() {
        return XnioIoThread.currentThread();
    }
}
