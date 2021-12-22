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
import com.photowey.dynamic.lifecycle.telnet.core.util.StringUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code AbstractCommandAdaptor}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public abstract class AbstractCommandAdaptor implements Command {

    private static final Logger log = LoggerFactory.getLogger(AbstractCommandAdaptor.class);

    protected static final String HELP_KEY = "help";
    private static final Option HELP = Option.builder("h").longOpt("help").required(false).hasArg(false).desc("Shows the help message.").build();
    private static final Option ALL = Option.builder("a").longOpt("all").required(false).hasArg(false).desc("Shows all plugin's table info.").build();
    private static final Option QUERY = Option.builder("q").longOpt("query").required(false).hasArg(false).desc("Shows the plugin's query info.").build();
    private static final Option INSTALL = Option.builder("i").longOpt("install").required(false).hasArg(false).desc("Install the plugin.").build();
    private static final Option UN_INSTALL = Option.builder("ui").longOpt("uninstall").required(false).hasArg(false).desc("Uninstall the plugin.").build();
    private static final Option UPDATE = Option.builder("up").longOpt("update").required(false).hasArg(false).desc("Update the plugin info.").build();

    protected Options options = new Options();
    protected String command;

    protected AbstractCommandAdaptor() {
        this.addOption(HELP);
        this.addOption(ALL);
        this.addOption(QUERY);
        this.addOption(INSTALL);
        this.addOption(UN_INSTALL);
        this.addOption(UPDATE);
    }

    @Override
    public void addOption(Option option) {
        this.options.addOption(option);
    }

    @Override
    public String execute() {
        String[] syntax = this.command.trim().split(DynamicLifecycleConstants.SPACE_SEPARATOR);
        return this.execute(syntax);
    }

    @Override
    public String execute(String[] args) {
        String response = "";
        try {
            CommandLine commandLine = new DefaultParser().parse(this.options, args);
            DynamicLifecycleCommandLine command = DynamicLifecycleCommandLine.builder().commandLine(commandLine).build();
            if (commandLine.hasOption(HELP_KEY)) {
                return new HelpCommand(this.command).doExecute(command);
            } else {
                response = this.doExecute(command);
            }
        } catch (Exception e) {
            if (StringUtils.isNotEmpty(e.getMessage())) {
                response = e.getMessage();
                log.error("handle the command:[{}] exception", this.command, e);
            }
        }

        return response;
    }

    @Override
    public void write(String message, Object... params) throws Exception {
    }

    @Override
    public void writeLine(String message, Object... params) throws Exception {
    }

    @Override
    public void writeError(String message, Object... params) throws Exception {
    }

    protected abstract String doExecute(DynamicLifecycleCommandLine commandLine) throws Exception;
}
