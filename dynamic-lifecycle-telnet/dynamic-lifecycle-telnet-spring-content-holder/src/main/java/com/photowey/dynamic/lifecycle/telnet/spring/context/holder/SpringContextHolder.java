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
package com.photowey.dynamic.lifecycle.telnet.spring.context.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * {@code SpringContextHolder}
 *
 * @author photowey
 * @date 2021/12/06
 * @since 1.0.0
 */
public enum SpringContextHolder {

    /**
     * Instance spring {@link org.springframework.context.ConfigurableApplicationContext} holder.
     */
    INSTANCE;

    /**
     * {@link org.springframework.context.ConfigurableApplicationContext}.
     */
    private ConfigurableApplicationContext applicationContext;

    /**
     * Set application context.
     *
     * @param applicationContext application context
     */
    public void applicationContext(final ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Acquire spring {@link org.springframework.context.ConfigurableApplicationContext}
     *
     * @return {@link org.springframework.context.ConfigurableApplicationContext}
     */
    public ConfigurableApplicationContext applicationContext() {
        return this.applicationContext;
    }

    public void registerBean(final Class<?> type, final Object object) {
        if (Objects.nonNull(this.applicationContext)) {
            this.applicationContext.getBeanFactory().registerSingleton(this.transferBeanName(type), object);
        }
    }

    public <T> T getBean(final Class<T> type) {
        Assert.notNull(type, "the type can't be null!");
        try {
            return this.applicationContext.getBean(type);
        } catch (BeansException e) {
            try {
                return this.getByName(type);
            } catch (BeansException ex) {
                return null;
            }
        }
    }

    public <T> T getBean(final String beanName, final Class<T> beanType) {
        Assert.notNull(beanType, "the type can't be null!");
        try {
            return this.applicationContext.getBean(this.transferBeanName(beanName), beanType);
        } catch (BeansException e) {
            return null;
        }
    }

    private <T> T getByName(final Class<T> beanType) {
        return this.applicationContext.getBean(this.transferBeanName(beanType), beanType);
    }

    public <T> String transferBeanName(Class<T> beanType) {
        return this.transferBeanName(beanType.getSimpleName());
    }

    public String transferBeanName(String beanName) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }
}
