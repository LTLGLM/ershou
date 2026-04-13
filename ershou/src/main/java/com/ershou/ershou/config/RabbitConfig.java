package com.ershou.ershou.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ交换机,队列配置
 */
@Configuration
public class RabbitConfig {

    // 设置主题通用交换机名称
    private static final String MANAGER_NOTICE_EXCHANGE = "notice_exchange";

    // 系统通知相关配置
    // 设置主题通知队列名称
    private static final String MANAGER_NOTICE_QUEUE = "manager_notice_queue";
    // 设置主题通知路由键
    private static final String MANAGER_NOTICE_ROUTING_KEY = "manager.notice.*";

    // 更改菜单通知相关配置
    // 设置主题菜单通知队列名称
    private static final String MENU_NOTICE_QUEUE = "menu_notice_queue";
    // 设置主题菜单通知路由键
    private static final String MENU_NOTICE_ROUTING_KEY = "menu.notice.*";


    /**
     * 创建主题交换机
     * durable=true 持久化交换机
     * autoDelete=false 不自动删除
     */
    @Bean
    TopicExchange noticeExchange() {
        return new TopicExchange(MANAGER_NOTICE_EXCHANGE, true, false);
    }


    /**
     * 创建持久化通知队列
     * durable=true 队列持久化
     * exclusive=false 非排他队列
     * autoDelete=false 不自动删除
     */
    @Bean
    public Queue managerNoticeQueue() {
        return new Queue(MANAGER_NOTICE_QUEUE, true, false, false);
    }

    /**
     * 绑定系统通知队列到交换机
     * 使用通配符路由键 manager.notice.*
     */
    @Bean
    Binding bindingManagerNotice() {
        return BindingBuilder
                .bind(managerNoticeQueue())
                .to(noticeExchange())
                .with(MANAGER_NOTICE_ROUTING_KEY);
    }


    // 创建主题菜单通知队列
    @Bean
    public Queue menuNoticeQueue() {
        return new Queue(MENU_NOTICE_QUEUE, true, false, false);
    }

    /**
     * 绑定菜单通知到交换机
     * 使用通配符路由键 menu.notice.*
     */
    @Bean
    Binding bindingMenuNotice() {
        return BindingBuilder
                .bind(menuNoticeQueue())
                .to(noticeExchange())
                .with(MENU_NOTICE_ROUTING_KEY);
    }

}
