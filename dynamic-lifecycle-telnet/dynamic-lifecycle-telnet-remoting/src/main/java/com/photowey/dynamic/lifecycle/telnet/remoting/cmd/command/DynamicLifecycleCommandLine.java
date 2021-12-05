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

import com.google.common.collect.Lists;
import com.photowey.dynamic.lifecycle.telnet.core.exception.DynamicLifecycleException;
import com.photowey.dynamic.lifecycle.telnet.core.util.StringUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.TypeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DynamicLifecycleCommandLine}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class DynamicLifecycleCommandLine {

    private CommandLine commandLine;

    public DynamicLifecycleCommandLine() {
    }

    public DynamicLifecycleCommandLine(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public boolean hasOption(String opt) {
        return this.commandLine.hasOption(opt);
    }

    public List<Option> getOptions() throws DynamicLifecycleException {
        Option[] options = this.commandLine.getOptions();
        return Lists.newArrayList(options);
    }

    public <T> List<T> getOptions(String opt, List<T> value) throws DynamicLifecycleException {
        List<T> options = this.getOption(opt);
        return null == options || options.isEmpty() ? value : options;
    }

    public <T> List<T> getOptions(String opt) throws DynamicLifecycleException {
        String[] results = this.commandLine.getOptionValues(opt);
        Option option = this.resolveOption(opt);
        if (null == option || null == results) {
            return new ArrayList<>();
        }

        Class<?> clazz = (Class<?>) option.getType();
        List<T> findOptions = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            T val = this.getOption(results[i], clazz);
            findOptions.add(val);
        }

        return findOptions;
    }

    public <T> T getOption(String opt, T value) throws DynamicLifecycleException {
        T option = this.getOption(opt);

        return null == option ? value : option;
    }

    public <T> T getOption(String opt) throws DynamicLifecycleException {
        List<T> options = this.getOptions(opt);
        if (options.isEmpty()) {
            return null;
        }

        return options.get(0);
    }

    private <T> T getOption(String res, Class<?> clazz) throws DynamicLifecycleException {
        try {
            return (T) TypeHandler.createValue(res, clazz);
        } catch (ParseException e) {
            throw new DynamicLifecycleException(e);
        }
    }

    public List<String> getArgList() {
        return this.commandLine.getArgList();
    }

    private Option resolveOption(String opt) {
        String optInner = StringUtils.stripPrefixIfNecessary(opt);
        for (Option option : this.commandLine.getOptions()) {
            if (optInner.equals(option.getOpt())) {
                return option;
            }

            if (optInner.equals(option.getLongOpt())) {
                return option;
            }
        }

        return null;
    }

    public static DynamicLifecycleCommandLineBuilder builder() {
        return new DynamicLifecycleCommandLineBuilder();
    }

    public static class DynamicLifecycleCommandLineBuilder {
        private CommandLine commandLine;

        DynamicLifecycleCommandLineBuilder() {
        }

        public DynamicLifecycleCommandLineBuilder commandLine(CommandLine commandLine) {
            this.commandLine = commandLine;
            return this;
        }

        public DynamicLifecycleCommandLine build() {
            return new DynamicLifecycleCommandLine(this.commandLine);
        }

        @Override
        public String toString() {
            return "NamedCommandLine.NamedCommandLineBuilder(commandLine=" + this.commandLine + ")";
        }
    }
}
