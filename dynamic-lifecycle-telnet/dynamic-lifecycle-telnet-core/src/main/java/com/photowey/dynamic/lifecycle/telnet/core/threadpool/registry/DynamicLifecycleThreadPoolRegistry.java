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
package com.photowey.dynamic.lifecycle.telnet.core.threadpool.registry;

import com.photowey.dynamic.lifecycle.telnet.core.threadpool.DynamicLifecycleThreadPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DynamicLifecycleThreadPoolRegistry}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class DynamicLifecycleThreadPoolRegistry {

    private static ConcurrentHashMap<String, DynamicLifecycleThreadPool> threadPoolMap = null;

    public static void registerThreadPool(String threadPoolName, DynamicLifecycleThreadPool threadPool) {
        if (threadPoolMap == null) {
            synchronized (DynamicLifecycleThreadPoolRegistry.class) {
                if (threadPoolMap == null) {
                    threadPoolMap = new ConcurrentHashMap<>(DynamicLifecycleThreadPool.NCPU);
                }
            }
        }

        threadPoolMap.putIfAbsent(threadPoolName, threadPool);
    }

    public static synchronized void unregisterThreadPool(String threadPoolName) {
        if (threadPoolMap != null) {
            threadPoolMap.remove(threadPoolName);
        }
    }

    public static DynamicLifecycleThreadPool getThreadPool(String threadPoolName) {
        return threadPoolMap == null ? null : threadPoolMap.get(threadPoolName);
    }
}
