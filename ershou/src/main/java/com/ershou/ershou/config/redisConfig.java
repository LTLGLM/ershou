package com.ershou.ershou.config;

import com.ershou.ershou.utils.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class redisConfig {

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 配置 Jackson2JsonRedisSerializer
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        template.setKeySerializer(new StringRedisSerializer());//key序列化
        template.setValueSerializer(serializer);//value序列化

        template.setHashKeySerializer(new StringRedisSerializer());//Hash key序列化
        template.setHashValueSerializer(serializer);//Hash value序列化
        return template;
    }

}
