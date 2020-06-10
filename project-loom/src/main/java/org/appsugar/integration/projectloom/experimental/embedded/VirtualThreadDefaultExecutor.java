package org.appsugar.integration.projectloom.experimental.embedded;

import java.util.concurrent.Executor;

/**
 * 任意的executor
 */
public class VirtualThreadDefaultExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }


    public static class UncondfinedExecutorProvider implements VirtualThreadExecutorProvider {
        private static VirtualThreadDefaultExecutor executor = new VirtualThreadDefaultExecutor();

        @Override
        public Executor getOrCreateCurrentExecutor() {
            return new Executor() {

                @Override
                public void execute(Runnable command) {
                    VirtualThreadBuilder.buildVirtualThread(null, command).start();
                }
            };
        }
    }
}
