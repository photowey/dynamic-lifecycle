/*
 * Copyright © 2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.dynamic.lifecycle.telnet.core.threadpool;

import com.photowey.dynamic.lifecycle.telnet.core.threadpool.factory.BlockingQueueFactory;
import com.photowey.dynamic.lifecycle.telnet.core.threadpool.factory.DynamicLifecycleThreadFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code DynamicLifecycleThreadPool}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class DynamicLifecycleThreadPool {

    private transient volatile ThreadPoolExecutor dynamicLifecycleExecutor;

    public static final int NCPU = Runtime.getRuntime().availableProcessors();

    private int corePoolSize = NCPU;
    private int maximumPoolSize = Math.max(NCPU, 1 << 4);
    private int initialQueueCapacity = 0;

    private long keepAliveTime = 3000L;

    private String threadPoolName = "lifecycle";

    private boolean daemon = false;
    private boolean allowCoreThreadTimeOut = false;
    private boolean preStartAllCoreThreads = false;

    private void init() {
        this.dynamicLifecycleExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                BlockingQueueFactory.buildQueue(initialQueueCapacity),
                DynamicLifecycleThreadFactory.create(threadPoolName, daemon)
        );
        if (allowCoreThreadTimeOut) {
            this.dynamicLifecycleExecutor.allowCoreThreadTimeOut(true);
        }
        if (preStartAllCoreThreads) {
            this.dynamicLifecycleExecutor.prestartAllCoreThreads();
        }
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public DynamicLifecycleThreadPool corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public DynamicLifecycleThreadPool maximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public DynamicLifecycleThreadPool keepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public int getInitialQueueCapacity() {
        return initialQueueCapacity;
    }

    public DynamicLifecycleThreadPool setInitialQueueCapacity(int initialQueueCapacity) {
        this.initialQueueCapacity = initialQueueCapacity;
        return this;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public DynamicLifecycleThreadPool threadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
        return this;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public DynamicLifecycleThreadPool daemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public DynamicLifecycleThreadPool allowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }

    public boolean isPreStartAllCoreThreads() {
        return preStartAllCoreThreads;
    }

    public DynamicLifecycleThreadPool preStartAllCoreThreads(boolean preStartAllCoreThreads) {
        this.preStartAllCoreThreads = preStartAllCoreThreads;
        return this;
    }

    public ThreadPoolExecutor getDynamicLifecycleExecutor() {
        if (dynamicLifecycleExecutor == null) {
            synchronized (this) {
                if (dynamicLifecycleExecutor == null) {
                    this.init();
                }
            }
        }

        return dynamicLifecycleExecutor;
    }
}
