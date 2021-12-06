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

/**
 * {@code InstallCommand}
 *
 * @author photowey
 * @date 2021/12/06
 * @since 1.0.0
 */
public class InstallCommand extends AbstractCommandAdaptor {

    public InstallCommand(String command) {
        this.command = command;
    }

    @Override
    public String name() {
        return "install";
    }

    @Override
    public boolean validate() {
        return this.command.contains(" --install ") || this.command.contains(" -i ");
    }

    @Override
    protected String doExecute(DynamicLifecycleCommandLine commandLine) throws Exception {
        return null;
    }
}
