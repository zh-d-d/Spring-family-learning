package com.lucky.spring.controller;

import com.lucky.spring.entity.QueueConfig;
import com.lucky.spring.util.RabbitUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by zhangdd on 2020/10/7
 */
@RestController("/config")
public class RabbitController {
    @Autowired
    private RabbitUtil rabbitUtil;

    /**
     * 创建交换器
     *
     * @param config
     */
    @PostMapping("addExchange")
    public void addExchange(@RequestBody QueueConfig config) {
        rabbitUtil.addExchange(config.getExchangeType(), config.getExchangeName());
    }

    /**
     * 删除交换器
     *
     * @param config
     */
    @PostMapping("deleteExchange")
    public void deleteExchange(@RequestBody QueueConfig config) {
        rabbitUtil.deleteExchange(config.getExchangeName());
    }

    /**
     * 添加队列
     *
     * @param config
     */
    @PostMapping("addQueue")
    public void addQueue(@RequestBody QueueConfig config) {
        rabbitUtil.addQueue(config.getQueueName());
    }

    /**
     * 删除队列
     *
     * @param config
     */
    @PostMapping("deleteQueue")
    public void deleteQueue(@RequestBody QueueConfig config) {
        rabbitUtil.deleteQueue(config.getQueueName());
    }

    /**
     * 清空队列数据
     *
     * @param config
     */
    @PostMapping("purgeQueue")
    public void purgeQueue(@RequestBody QueueConfig config) {
        rabbitUtil.purgeQueue(config.getQueueName());
    }

    /**
     * 添加绑定
     *
     * @param config
     */
    @PostMapping("addBinding")
    public void addBinding(@RequestBody QueueConfig config) {
        rabbitUtil.addBinding(config.getExchangeType(), config.getExchangeName(), config.getQueueName(), config.getRoutingKey(), false, null);
    }

    /**
     * 解除绑定
     *
     * @param config
     */
    @PostMapping("removeBinding")
    public void removeBinding(@RequestBody QueueConfig config) {
        rabbitUtil.removeBinding(config.getExchangeType(), config.getExchangeName(), config.getQueueName(), config.getRoutingKey(), false, null);
    }

    /**
     * 创建头部类型的交换器
     * 判断条件是所有的键值对都匹配成功才发送到队列
     *
     * @param config
     */
    @PostMapping("andExchangeBindingQueueOfHeaderAll")
    public void andExchangeBindingQueueOfHeaderAll(@RequestBody QueueConfig config) {
        HashMap<String, Object> header = new HashMap<>();
        header.put("queue", "queue");
        header.put("bindType", "whereAll");
        rabbitUtil.addExchangeBindingQueue(ExchangeTypes.HEADERS, config.getExchangeName(), config.getQueueName(), null, true, header);
    }

    /**
     * 创建头部类型的交换器
     * 判断条件是只要有一个键值对匹配成功就发送到队列
     *
     * @param config
     */
    @PostMapping("andExchangeBindingQueueOfHeaderAny")
    public void andExchangeBindingQueueOfHeaderAny(@RequestBody QueueConfig config) {
        HashMap<String, Object> header = new HashMap<>();
        header.put("queue", "queue");
        header.put("bindType", "whereAny");
        rabbitUtil.addExchangeBindingQueue(ExchangeTypes.HEADERS, config.getExchangeName(), config.getQueueName(), null, false, header);
    }
}
