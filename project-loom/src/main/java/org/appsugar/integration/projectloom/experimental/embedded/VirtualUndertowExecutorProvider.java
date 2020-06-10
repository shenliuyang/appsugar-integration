package org.appsugar.integration.projectloom.experimental.embedded;

import org.xnio.XnioIoThread;

import java.util.concurrent.Executor;

public class VirtualUndertowExecutorProvider implements VirtualThreadExecutorProvider {

    @Override
    public Executor getOrCreateCurrentExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                VirtualThreadBuilder.buildVirtualThread(XnioIoThread.currentThread(), command).start();
            }
        };
    }
}
