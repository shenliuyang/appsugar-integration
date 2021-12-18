package org.appsugar.integration.data.redis;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.redis
 * @className RedisScriptTest
 * @date 2021-04-23  18:49
 */
@ExtendWith(SpringExtension.class)
@DataRedisTest
@Slf4j
public class RedisScriptTest {

    @Autowired
    private RedisTemplate<String, byte[]> template;
    @Autowired
    @Qualifier("checkValuePresentScript")
    private RedisScript<List<Integer>> script;

    @Test
    @SneakyThrows
    public void testCheck() {
        List<String> urls = Lists.list("1", "2", "3", "4", "5", "6");
        MessageDigest md = MessageDigest.getInstance("MD5");
        Object[] args = urls.stream().map(e -> md.digest(e.getBytes())).toArray();
        List<Integer> result = template.execute(script, Collections.singletonList("urls_bulk_2"), args);
        log.debug("result is {}", result);
    }
}
