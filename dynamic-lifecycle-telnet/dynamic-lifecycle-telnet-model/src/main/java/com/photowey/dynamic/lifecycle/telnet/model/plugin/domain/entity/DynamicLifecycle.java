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
package com.photowey.dynamic.lifecycle.telnet.model.plugin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code DynamicLifecycle}
 *
 * @author photowey
 * @date 2021/12/06
 * @since 1.0.0
 */
@TableName("dynamic_lifecycle_plugin")
public class DynamicLifecycle implements Serializable {

    private static final long serialVersionUID = -6968168451280822332L;

    @TableId
    private Long id;

    private String className;

    private String pluginCode;

    private String pluginName;

    private String pluginVersion;
    /**
     * local@file:///Users/photowey/plugin/jar/HelloPlugin-1.0.0.jar
     * local@file://D:/Users/photowey/plugin/jar/HelloPlugin-1.0.0.jar
     * remote@https://www.github.com/photowey/dynamic-lifecycle/HelloPlugin-1.0.0.jar
     */
    private String jarPath;

    private String activeState;

    private LocalDateTime activeTime;

    @TableField("gmt_create")
    private LocalDateTime createTime;

    @TableField("gmt_modified")
    private LocalDateTime modifiedTime;
}
