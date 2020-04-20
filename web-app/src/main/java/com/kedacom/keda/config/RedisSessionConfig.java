package com.kedacom.keda.config;

import jdk.nashorn.internal.runtime.GlobalConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Start spring session support
 */
@Configuration
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class RedisSessionConfig {
    //Spring Session replace default tomcat httpSession
}
