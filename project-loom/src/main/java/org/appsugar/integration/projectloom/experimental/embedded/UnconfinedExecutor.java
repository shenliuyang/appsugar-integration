package org.appsugar.integration.projectloom.experimental.embedded;

import java.util.concurrent.Executor;

/**
 * 任意的executor
 */
public class UnconfinedExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static class UncondfinedExecutorProvider implements ExecutorProvider {
        private static UnconfinedExecutor executor = new UnconfinedExecutor();

        @Override
        public boolean useDefaultDispatcher() {
            return true;
        }

        @Override
        public Executor getOrCreateCurrentExecutor() {
            return executor;
        }
    }
}
