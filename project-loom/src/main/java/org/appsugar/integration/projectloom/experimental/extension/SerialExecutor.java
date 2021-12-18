package org.appsugar.integration.projectloom.experimental.extension;

import io.netty.util.internal.PlatformDependent;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 串行执行器,保证所有任务不会被并发
 *
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.projectloom.experimental.extension
 * @className SerialExecutor
 * @date 2021-11-19  13:44
 */
public class SerialExecutor implements Executor {
    private Queue<Runnable> task_queue = PlatformDependent.newMpscQueue(65536);

    private AtomicBoolean running = new AtomicBoolean(false);

    private Executor realExecutor;

    public SerialExecutor(Executor realExecutor) {
        this.realExecutor = realExecutor;
    }

    @Override
    public void execute(Runnable command) {
        boolean result = task_queue.offer(command);
        if (!result) {
            //TODO  
        }
        if (running.compareAndSet(false, true)) {
            realExecutor.execute(this::realRun);
        }
    }

    protected void realRun() {
        while (true) {
            Runnable task = task_queue.poll();
            if (task == null) {
                //解除运行状态
                running.set(false);
                //感知边界
                if (!task_queue.isEmpty() && running.compareAndSet(false, true)) {
                    continue;
                }
                return;
            }
            try {
                task.run();
            } catch (Throwable e) {
            }
        }
    }
}
