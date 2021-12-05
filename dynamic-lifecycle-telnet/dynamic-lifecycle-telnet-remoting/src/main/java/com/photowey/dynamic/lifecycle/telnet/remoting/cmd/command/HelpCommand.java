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
package com.photowey.dynamic.lifecycle.telnet.remoting.cmd.command;

import com.photowey.dynamic.lifecycle.telnet.core.constant.DynamicLifecycleConstants;

/**
 * {@code HelpCommand}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class HelpCommand extends AbstractCommandAdaptor {

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public boolean validate() {
        String[] syntax = this.command.trim().split(DynamicLifecycleConstants.SPACE_SEPARATOR);
        return "dynamic".equals(syntax[0]);
    }

    @Override
    protected String doExecute(DynamicLifecycleCommandLine commandLine) throws Exception {
        return HELP_MESSAGE;
    }

    private static final String HELP_MESSAGE = "DynamicLifecycle Command Tips:\n"
            + "  USAGE: dynamic [option...] [arguments...]\n"
            + "  -h,--help:  Shows the help message.\n"
            + "  -a:  Shows all plugin's table info.\n";
}
