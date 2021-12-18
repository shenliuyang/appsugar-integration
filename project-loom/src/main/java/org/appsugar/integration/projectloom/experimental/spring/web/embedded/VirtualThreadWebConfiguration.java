package org.appsugar.integration.projectloom.experimental.spring.web.embedded;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 支持mvc级别虚拟线程异步
 * 在Controller上或者对应方法上添加VirtualThreadAsync注解,派发到虚拟线程中去执行.
 * 要求方法返回值是spring mvc规范的异步返回.如 CompletableFuture,  DeferredResult
 */
@ConditionalOnClass(value = {EnableWebMvc.class})
@Configuration
public class VirtualThreadWebConfiguration {

    /**
     * 异步执行器, 通过虚拟线程来执行任务.  现有虚拟线程有一定的限制. 需要符合他的规范.  确保系统内核线程不被挂起.
     */
    @Bean(name = VirtualThreadAsync.VIRTUAL_THREAD_EXECUTE_NAME)
    public Executor virtualThreadExecutor() {

        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
