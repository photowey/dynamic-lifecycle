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
package com.photowey.dynamic.lifecycle.telnet.core.constant;

/**
 * {@code DynamicLifecycleConstants}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public interface DynamicLifecycleConstants {

    String TELNET_STRING_END = new String(new byte[]{(byte) 13, (byte) 10});
    String EMPTY_STRING = "";

    String SPACE_SEPARATOR = "\\s+";

    String TELNET_SERVER_ENABLE = "dynamic.lifecycle.telnet.server.enabled";
    String TELNET_SERVER_SECURITY_ENABLE = "dynamic.lifecycle.telnet.security.enabled";

    String TELNET_SERVER_WORKER_THREAD_POOL_NAME = "worker";

    String TELNET_COMMAND_THREAD_POOL_NAME = "dynamic";

    String TELNET_SESSION_PROMPT = "dynamic-lifecycle>";

    int DEFAULT_TELNET_PORT = 7923;
    int DEFAULT_SELECT_PORT_SIZE = 256;


}
