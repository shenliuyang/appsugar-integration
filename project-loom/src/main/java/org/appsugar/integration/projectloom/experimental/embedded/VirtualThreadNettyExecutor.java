package org.appsugar.integration.projectloom.experimental.embedded;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ThreadExecutorMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * 把netty eventLoop 包装成普通的 Executor
 * 提交任务时,如果是当前eventLoop那么直接执行.
 * 减少把任务入队列的操作提高性能.
 */
public class VirtualThreadNettyExecutor implements Executor {
    private static final Logger logger = LoggerFactory.getLogger(VirtualThreadNettyExecutor.class);
    private static final FastThreadLocal<VirtualThreadNettyExecutor> mappings = new FastThreadLocal<>();

    /**
     * 获取当前线程绑定的netty event loop(如果当前线程非 eventLoop 返回空)
     * 其它线程要想获取必需先设置 NettyWithoutEnqueuEventExecutor.setCurrentExecutor 关闭时remove
     */
    public static VirtualThreadNettyExecutor currentExecutor() {
        VirtualThreadNettyExecutor current = mappings.get();
        if (Objects.nonNull(current)) {
            return current;
        }
        EventExecutor ee = ThreadExecutorMap.currentExecutor();
        if (Objects.isNull(ee)) {
            return null;
        }
        current = new VirtualThreadNettyExecutor(ee);
        mappings.set(current);
        return current;
    }

    /***
     * 把executor设置到当前线程上下文中(netty event loop设置后无需remove)
     *
     * @param executor
     */
    public static void setCurrentExecutor(VirtualThreadNettyExecutor executor) {
        VirtualThreadNettyExecutor nee = currentExecutor();
        if (Objects.nonNull(nee) && nee.eventExecutor.inEventLoop() && executor.eventExecutor != nee.eventExecutor) {
            String msg = "Trying to bind another executor  to netty FastThreadLocalThread was forbidden ,current executor is " + nee.eventExecutor + " param executor is " + executor.eventExecutor;
            logger.error(msg);
            throw new UnsupportedOperationException(msg);
        }
        mappings.set(executor);
    }

    public static void removeCurrentExecutor() {
        VirtualThreadNettyExecutor nee = currentExecutor();
        if (Objects.nonNull(nee) && nee.eventExecutor.inEventLoop()) {
            String msg = "Current thread is netty event loop thread " + Thread.currentThread().getName() + " did not allowed to remove Executor ";
            logger.error(msg);
            throw new UnsupportedOperationException(msg);
        }
        mappings.remove();
    }


    protected EventExecutor eventExecutor;

    public VirtualThreadNettyExecutor(EventExecutor eventExecutor) {
        this.eventExecutor = eventExecutor;
    }

    @Override
    public void execute(Runnable command) {
        EventExecutor executor = getEventExecutor();
        Thread thread = VirtualThreadBuilder.buildVirtualThread(executor, command);
        thread.start();
    }

    public EventExecutor getEventExecutor() {
        return this.eventExecutor;
    }

    public static class NettyPlatformEventExecutorProvider implements VirtualThreadExecutorProvider {
        @Override
        public Executor getOrCreateCurrentExecutor() {
            return VirtualThreadNettyExecutor.currentExecutor();
        }
    }

}
