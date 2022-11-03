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
package org.apache.rocketmq.client.consumer.rebalance;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * Cycle average Hashing queue algorithm
 */
public class AllocateMessageQueueAveragelyByCircle extends AbstractAllocateMessageQueueStrategy {

    public AllocateMessageQueueAveragelyByCircle() {
        log = ClientLogger.getLog();
    }

    public AllocateMessageQueueAveragelyByCircle(InternalLogger log) {
        super(log);
    }

    @Override
    public List<MessageQueue> allocate(String consumerGroup, String currentCID, List<MessageQueue> mqAll,
        List<String> cidAll) {

        List<MessageQueue> result = new ArrayList<MessageQueue>();
        if (!check(consumerGroup, currentCID, mqAll, cidAll)) {
            return result;
        }

        int index = cidAll.indexOf(currentCID);
        for (int i = index; i < mqAll.size(); i++) {
            if (i % cidAll.size() == index) {
                result.add(mqAll.get(i));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "AVG_BY_CIRCLE";
    }


    public static void main(String[] args) {
        List<MessageQueue> list = new ArrayList<>();
        list.add(new MessageQueue("t1", "b1", 0));
        list.add(new MessageQueue("t1", "b1", 1));
        list.add(new MessageQueue("t1", "b1", 2));
        list.add(new MessageQueue("t1", "b2", 0));
        list.add(new MessageQueue("t1", "b2", 1));
        list.add(new MessageQueue("t1", "b2", 2));
        list.add(new MessageQueue("t1", "b3", 0));

        List<String> cidAll = new ArrayList<>();
        cidAll.add("c1");
        cidAll.add("c2");

        AllocateMessageQueueAveragelyByCircle averagely = new AllocateMessageQueueAveragelyByCircle();
        List<MessageQueue> l1 = averagely.allocate("g", "c1", list, cidAll);
        System.out.println("c1: " + l1);
        List<MessageQueue> l2 = averagely.allocate("g", "c2", list, cidAll);
        System.out.println("c2: " + l2);
    }
}
