package org.appsugar.integration.data.redis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.redis
 * @className RedisConfiguration
 * @date 2021-04-23  18:50
 */
@SpringBootApplication
@TestConfiguration
public class RedisConfiguration {
    @Bean
    public RedisScript<List<Integer>> checkValuePresentScript() {
        ResourceScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("META-INF/scripts/check_value_present.lua"));
        RedisScript script = RedisScript.of(scriptSource.getResource(), List.class);
        return script;
    }

    @Bean
    public RedisTemplate<String, byte[]> stringKeyByteArrayValueRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(ByteArrayRedisSerializer.INSTANCE);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(ByteArrayRedisSerializer.INSTANCE);
        return template;
    }

    public static enum ByteArrayRedisSerializer implements RedisSerializer<byte[]> {

        INSTANCE;

        @Nullable
        @Override
        public byte[] serialize(@Nullable byte[] bytes) throws SerializationException {
            return bytes;
        }

        @Nullable
        @Override
        public byte[] deserialize(@Nullable byte[] bytes) throws SerializationException {
            return bytes;
        }
    }
}
