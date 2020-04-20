package com.kedacom.keda.manager.impl;

import com.kedacom.keda.config.Constants;
import com.kedacom.keda.manager.TokenManager;
import com.kedacom.keda.model.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<Long, String> redis;

    @Autowired
    public void setRedis (@Qualifier("stringRedisTemplate") RedisTemplate redis) {
        this.redis = redis;
        // Change serialization method after set to Long type
        redis.setKeySerializer (new JdkSerializationRedisSerializer());
    }

    @Override
    public TokenModel createToken (long userId) {
        // Take uuid as source token
        String token = UUID.randomUUID ().toString ().replace ("-", "");
        TokenModel model = new TokenModel (userId, token);
        // store to redis and set expire time
        redis.boundValueOps (userId).set (token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    @Override
    public TokenModel getToken (String authentication) {
        if (authentication == null || authentication.length () == 0) {
            return null;
        }
        String [] param = authentication.split ("_");
        if (param.length != 2) {
            return null;
        }
        // Use userId and source token to concatenate token，available for adding authentication
        long userId = Long.parseLong (param [0]);
        String token = param [1];
        return new TokenModel (userId, token);
    }

    @Override
    public boolean checkToken (TokenModel model) {
        if (model == null) {
            return false;
        }
        String token =redis.boundValueOps (model.getUserId ()).get ();
        if (token == null || !token.equals (model.getToken ())) {
            return false;
        }
        // If success, it proves that the user did an effective operation. So it will extend the
        // expire time of token
        redis.boundValueOps (model.getUserId ()).expire (Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    @Override
    public void deleteToken (long userId) {
        redis.delete (userId);
    }
}
