package org.appsugar.integration.projectloom.experimental.spring.web.embedded;

import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.*;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.projectloom.experimental.spring.web.embedded
 * @className VirtualThreadAsync
 * @date 2021-11-29  16:21
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Async(VirtualThreadAsync.VIRTUAL_THREAD_EXECUTE_NAME)
public @interface VirtualThreadAsync {
    public static final String VIRTUAL_THREAD_EXECUTE_NAME = "virtualThreadExecutor";
}
