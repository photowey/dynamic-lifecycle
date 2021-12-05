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
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.io.Serializable;

/**
 * {@code DynamicLifecycleCommand}
 *
 * @author photowey
 * @date 2021/12/05
 * @since 1.0.0
 */
public class DynamicLifecycleCommand extends AbstractCommandAdaptor implements Serializable {

    private static final long serialVersionUID = 9146656012218010465L;

    public DynamicLifecycleCommand(String command) {
        this.command = command;
    }

    @Override
    public String name() {
        return "dynamic";
    }

    @Override
    public boolean validate() {
        String[] syntax = command.trim().split(DynamicLifecycleConstants.SPACE_SEPARATOR);
        return this.name().equals(syntax[0]);
    }

    @Override
    protected String doExecute(DynamicLifecycleCommandLine commandLine) throws Exception {
        // TODO do parse
        return this.makeupProcess();
    }

    String makeupProcess() {
        String[] students = {"小明", "李雷", "韩梅梅"};
        double[] scores = {90.1, 84.3, 99.7};
        double[] chinese = {90.1, 84.3, 99.7};
        double[] data = {90.1, 84.3, 99.7};
        double[] english = {90.1, 84.3, 99.7};
        double[] physical = {90.1, 84.3, 99.7};
        double[] chemistry = {90.1, 84.3, 99.7};
        Table table = Table.create("学生分数统计表-模仿某些操作返回列表").addColumns(
                StringColumn.create("姓名", students),
                DoubleColumn.create("综合", scores),
                DoubleColumn.create("语文", chinese),
                DoubleColumn.create("数据", data),
                DoubleColumn.create("英语", english),
                DoubleColumn.create("物理", physical),
                DoubleColumn.create("化学", chemistry)
        );
        String tableStr = table.print().replaceAll("\r\n", "\n");
        return tableStr;
    }
}
