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
package com.photowey.dynamic.lifecycle.telnet.core.threadpool.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * {@code BlockingQueueFactory}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class BlockingQueueFactory {

    public static BlockingQueue<Runnable> buildQueue(int initialCapacity) {
        return buildQueue(initialCapacity, false);
    }

    public static BlockingQueue<Runnable> buildQueue(int initialCapacity, boolean priority) {
        BlockingQueue<Runnable> queue;
        if (initialCapacity == 0) {
            queue = new SynchronousQueue<>();
        } else {
            if (priority) {
                queue = initialCapacity < 0 ? new PriorityBlockingQueue<>() : new PriorityBlockingQueue<>(initialCapacity);
            } else {
                queue = initialCapacity < 0 ? new LinkedBlockingDeque<>() : new LinkedBlockingDeque<>(initialCapacity);
            }
        }
        return queue;
    }
}
