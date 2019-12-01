package com.tna.campus_store.utils;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.repository.IdentificationRepository;
import com.tna.campus_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonsMethod {
    private static UserRepository userRepository;
    private static RedisUtils redisUtils;
    private static IdentificationRepository identificationRepository;

    @Autowired
    private CommonsMethod(UserRepository userRepository, RedisUtils redisUtils,
                          IdentificationRepository identificationRepository) {
        CommonsMethod.userRepository = userRepository;
        CommonsMethod.redisUtils = redisUtils;
        CommonsMethod.identificationRepository = identificationRepository;
    }

    public static Msg judgeToken(String token) {
        if (token == null)
            return Msg.fail("未授权...", StatusEnum.UNAUTHORIZED.getCode());
        Integer user_id = (Integer) redisUtils.get(token);
        if (user_id == null)
            return Msg.fail("授权已过期或不存在...", StatusEnum.AUTH_DUE.getCode());
        User user = userRepository.findOne(user_id);
        if (user == null)
            return Msg.fail("该用户不存在", StatusEnum.HINT.getCode());
        return Msg.success("授权存在！").add("user", user);
    }

//    public static Msg getIdentification(String token){
//        if (token == null) {
//            return Msg.fail("未授权...", StatusEnum.UNAUTHORIZED.getCode());
//        }
//        Integer user_id = (Integer) redisUtils.get(token);
//        if (user_id == null) {
//            return Msg.fail("授权已过期或不存在...", StatusEnum.AUTH_DUE.getCode());
//        }
//        User user = userRepository.findOne(user_id);
//        if (user==null){
//            return Msg.fail("该用户不存在", StatusEnum.HINT.getCode());
//        }
//        return Msg.success("授权存在！").add("user",user);
//    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static RedisUtils getRedisUtils() {
        return redisUtils;
    }

    public static IdentificationRepository getIdentificationRepository() {
        return identificationRepository;
    }
}
