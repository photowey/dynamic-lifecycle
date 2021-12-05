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
package com.photowey.dynamic.lifecycle.telnet.remoting.netty.session;

import com.photowey.dynamic.lifecycle.telnet.core.constant.DynamicLifecycleConstants;
import com.photowey.dynamic.lifecycle.telnet.core.exception.DynamicLifecycleException;
import com.photowey.dynamic.lifecycle.telnet.core.threadpool.DynamicLifecycleThreadPool;
import com.photowey.dynamic.lifecycle.telnet.core.threadpool.registry.DynamicLifecycleThreadPoolRegistry;
import com.photowey.dynamic.lifecycle.telnet.remoting.netty.handler.NettyTelnetServer;
import com.photowey.dynamic.lifecycle.telnet.remoting.netty.port.PortSelector;
import com.photowey.dynamic.lifecycle.telnet.remoting.netty.server.TelnetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code StandardDynamicLifecycleService}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class StandardDynamicLifecycleService implements DynamicLifecycleService, SmartInitializingSingleton, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(StandardDynamicLifecycleService.class);

    private static final int WORKER_THREAD_POOL_SIZE = 2;

    private int port = -1;

    private AtomicBoolean shutdown = new AtomicBoolean(false);

    private boolean enableTelnetServer = false;

    private TelnetServer telnetServer;

    public StandardDynamicLifecycleService() {
        this.walk();
    }

    public void walk() {
        this.enableTelnetServer = System.getProperty(DynamicLifecycleConstants.TELNET_SERVER_ENABLE, "true").equalsIgnoreCase("true");
        if (this.enableTelnetServer) {
            try {
                this.port = PortSelector.selectAvailablePort(DynamicLifecycleConstants.DEFAULT_TELNET_PORT, DynamicLifecycleConstants.DEFAULT_SELECT_PORT_SIZE);
            } catch (NumberFormatException e) {
                throw new DynamicLifecycleException(e);
            }
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.init();
    }

    @Override
    public void destroy() throws Exception {
        this.dispose();
    }

    @Override
    public void run() {
        try {
            log.info("the telnet server listening on port: {}", this.port);
            DynamicLifecycleThreadPool workerPool = this.populateWorkerThreadPool();
            this.telnetServer = new NettyTelnetServer(port, workerPool.getDynamicLifecycleExecutor());
            this.telnetServer.open();
        } catch (InterruptedException e) {
            throw new DynamicLifecycleException(e);
        }
    }

    @Override
    public void shutdown() {
        if (this.shutdown.compareAndSet(false, true)) {
            try {
                if (this.telnetServer != null) {
                    this.telnetServer.shutdown();
                    this.telnetServer = null;
                }
            } catch (Throwable t) {
                throw new DynamicLifecycleException(t);
            }
        }
    }

    @Override
    public void init() throws DynamicLifecycleException {
        if (this.enableTelnetServer) {
            this.run();
        } else {
            log.error("init action:the current telnet server not enabled");
        }
    }

    @Override
    public void dispose() throws DynamicLifecycleException {
        if (this.enableTelnetServer) {
            this.shutdown();
        } else {
            log.error("shutdown action:the current telnet server not enabled");
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    public DynamicLifecycleThreadPool populateWorkerThreadPool() {
        DynamicLifecycleThreadPool workerPool = new DynamicLifecycleThreadPool()
                .corePoolSize(WORKER_THREAD_POOL_SIZE)
                .daemon(true)
                .threadPoolName(DynamicLifecycleConstants.TELNET_SERVER_WORKER_THREAD_POOL_NAME);

        DynamicLifecycleThreadPoolRegistry.registerThreadPool(DynamicLifecycleConstants.TELNET_SERVER_WORKER_THREAD_POOL_NAME, workerPool);

        return workerPool;
    }
}
