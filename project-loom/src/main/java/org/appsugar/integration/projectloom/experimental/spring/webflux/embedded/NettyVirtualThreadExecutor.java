package org.appsugar.integration.projectloom.experimental.spring.webflux.embedded;

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ThreadExecutorMap;
import org.springframework.http.client.reactive.ReactorResourceFactory;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * 基于netty的 轻量级线程执行器
 * 执行command时,如果inEventLoop  那么直接执行.减少再入队列带来的性能损耗
 */
public class NettyVirtualThreadExecutor extends VirtualThreadExecutor {
    private static final FastThreadLocal<FastExecutor> mappings = new FastThreadLocal<>();

    private EventLoopGroup eventLoopGroup;

    public NettyVirtualThreadExecutor(ReactorResourceFactory reactorResourceFactory) {
        this.eventLoopGroup = reactorResourceFactory.getLoopResources().onServer(true);
    }


    @Override
    public Executor getExecutor() {
        FastExecutor fastExecutor = mappings.get();
        if (Objects.isNull(fastExecutor)) {
            EventExecutor executor = ThreadExecutorMap.currentExecutor();
            if (Objects.isNull(executor)) {
                return this.eventLoopGroup.next();
            }
            fastExecutor = new FastExecutor();
            fastExecutor.eventExecutor = executor;
            mappings.set(fastExecutor);
        }
        return fastExecutor;
    }

    private class FastExecutor implements Executor {
        EventExecutor eventExecutor;

        @Override
        public void execute(Runnable command) {
            if (eventExecutor.inEventLoop()) {
                command.run();
                return;
            }
            eventExecutor.execute(command);
        }
    }
}
