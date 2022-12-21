package com.yg.shop.server.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 *
 * @author bxx
 */
@Configuration
public class RedisConfig {

    @Bean(name = "jsonRedisTemplate")
    public RedisTemplate<String, JSONObject> jsonRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, JSONObject> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        FastJsonRedisSerializer<JSONObject> fastJsonRedisSerializer
                = new FastJsonRedisSerializer<>(JSONObject.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
