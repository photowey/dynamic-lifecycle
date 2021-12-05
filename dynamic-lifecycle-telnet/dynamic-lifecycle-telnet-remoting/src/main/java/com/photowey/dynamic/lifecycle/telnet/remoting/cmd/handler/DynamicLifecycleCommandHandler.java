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
package com.photowey.dynamic.lifecycle.telnet.remoting.cmd.handler;

import com.photowey.dynamic.lifecycle.telnet.core.constant.DynamicLifecycleConstants;
import com.photowey.dynamic.lifecycle.telnet.core.threadpool.DynamicLifecycleThreadPool;
import com.photowey.dynamic.lifecycle.telnet.core.threadpool.registry.DynamicLifecycleThreadPoolRegistry;
import com.photowey.dynamic.lifecycle.telnet.core.util.StringUtils;
import com.photowey.dynamic.lifecycle.telnet.remoting.cmd.command.Command;
import com.photowey.dynamic.lifecycle.telnet.remoting.cmd.command.DynamicLifecycleCommand;
import com.photowey.dynamic.lifecycle.telnet.remoting.cmd.command.HelpCommand;

/**
 * {@code DynamicLifecycleCommandHandler}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class DynamicLifecycleCommandHandler {

    private final TelnetCommandHandler commandHandler;

    static {
        init();
    }

    public DynamicLifecycleCommandHandler() {
        this.commandHandler = new TelnetCommandHandler();
    }

    private static void init() {
        DynamicLifecycleThreadPool namedThreadPool = populateCommandThreadPool();
        DynamicLifecycleThreadPoolRegistry.registerThreadPool(DynamicLifecycleConstants.TELNET_COMMAND_THREAD_POOL_NAME, namedThreadPool);
    }

    private static DynamicLifecycleThreadPool populateCommandThreadPool() {
        DynamicLifecycleThreadPool namedThreadPool = new DynamicLifecycleThreadPool()
                .allowCoreThreadTimeOut(true)
                .threadPoolName(DynamicLifecycleConstants.TELNET_COMMAND_THREAD_POOL_NAME)
                .daemon(true);

        return namedThreadPool;
    }

    // ====================================================================== request

    public String handleRequest(String cmd) {

        String commandResult = this.handleCommand(cmd);

        commandResult = commandResult.replace("\n", DynamicLifecycleConstants.TELNET_STRING_END);
        if (StringUtils.isEmpty(commandResult)) {
            commandResult = DynamicLifecycleConstants.TELNET_STRING_END;
        } else if (!commandResult.endsWith(DynamicLifecycleConstants.TELNET_STRING_END)) {
            commandResult = commandResult + DynamicLifecycleConstants.TELNET_STRING_END + DynamicLifecycleConstants.TELNET_STRING_END;
        } else if (!commandResult.endsWith(DynamicLifecycleConstants.TELNET_STRING_END.concat(DynamicLifecycleConstants.TELNET_STRING_END))) {
            commandResult = commandResult + DynamicLifecycleConstants.TELNET_STRING_END;
        }

        commandResult = commandResult + this.handlePrompt();

        return commandResult;
    }

    // ====================================================================== command

    public String handleCommand(String cmd) {
        if (StringUtils.isEmpty(cmd)) {
            return DynamicLifecycleConstants.EMPTY_STRING;
        }
        Command command = new DynamicLifecycleCommand(cmd);
        if (this.commandHandler.validate(command)) {
            return this.commandHandler.handleCommand(command);
        }

        return this.handleHelp(cmd);
    }

    // ====================================================================== help

    public String handleHelp(String command) {
        String help = new HelpCommand(command).execute();
        StringBuilder helpBuilder = new StringBuilder();

        return helpBuilder.append(String.format("The cmd:[%s] error,read the help please!", command)).append("\n").append(help).toString();
    }

    // ====================================================================== prompt

    public String handlePrompt() {
        return DynamicLifecycleConstants.TELNET_SESSION_PROMPT;
    }

}