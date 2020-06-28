package org.appsugar.integration.projectloom.experimental.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Dispatcher;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.transport.dispatcher.ChannelEventRunnable;
import org.apache.dubbo.remoting.transport.dispatcher.WrappedChannelHandler;

/**
 * 虚拟线程转发器,让dubbo所有操作都执行在非阻塞的虚拟线程上
 */
public class VirtualThreadDispatcher implements Dispatcher {
    @Override
    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new VirtualThreadChannelHandler(handler, url);
    }
}

class VirtualThreadChannelHandler extends WrappedChannelHandler {

    public VirtualThreadChannelHandler(ChannelHandler handler, URL url) {
        super(handler, url);
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        fireInVirtualThread(new ChannelEventRunnable(channel, handler, ChannelEventRunnable.ChannelState.CONNECTED));
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        fireInVirtualThread(new ChannelEventRunnable(channel, handler, ChannelEventRunnable.ChannelState.DISCONNECTED));
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        fireInVirtualThread(new ChannelEventRunnable(channel, handler, ChannelEventRunnable.ChannelState.RECEIVED, message));
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        fireInVirtualThread(new ChannelEventRunnable(channel, handler, ChannelEventRunnable.ChannelState.CAUGHT, exception));
    }

    protected void fireInVirtualThread(Runnable runnable) {
        Thread current = Thread.currentThread();
        if (current.isVirtual()) {
            runnable.run();
            return;
        }
        Thread thread = Thread.builder().virtual().name("VirtualThread-" + current.getName()).inheritThreadLocals().task(runnable).build();
        thread.start();
    }

}