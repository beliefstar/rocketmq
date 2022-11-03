/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.client.consumer;

import java.util.List;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * 客户端负载均衡策略
 * Strategy Algorithm for message allocating between consumers
 */
public interface AllocateMessageQueueStrategy {

    /**
     * 根据消费者客户端ID进行分配
     *
     * @param consumerGroup 当前的消费组
     * @param currentCID 当前的客户端ID
     * @param mqAll 所有的消息队列
     * @param cidAll 所有的客户端ID
     * @return 分配给当前客户端的队列
     */
    List<MessageQueue> allocate(
        final String consumerGroup,
        final String currentCID,
        final List<MessageQueue> mqAll,
        final List<String> cidAll
    );

    /**
     * 算法名称(日志)
     */
    String getName();
}
