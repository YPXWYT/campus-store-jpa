package com.tna.campus_store.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.ThirdPartService;
import com.tna.campus_store.utils.MessageXsendUtils;
import com.tna.campus_store.utils.RedisUtils;

@Service
public class ThirdPartServiceImpl implements ThirdPartService {

    private RedisUtils redisUtils;
    private UserRepository userRepository;

    @Autowired
    public ThirdPartServiceImpl(RedisUtils redisUtils, UserRepository userRepository) {
        super();
        this.redisUtils = redisUtils;
        this.userRepository = userRepository;
    }

    @Override
    public Msg sendVerificationCode_R(String phone_number, HttpSession session) {
        String verification_code = MessageXsendUtils.getConversionCode();
        if (phone_number == null) {
            return Msg.fail("参数不能为空！", StatusEnum.HINT.getCode());
        }
        if (userRepository.findByPhoneNumber(phone_number) == null) {
//			if(redisUtils.set(conversionCode, conversionCode, 300)&&) {
            if (redisUtils.set(phone_number + verification_code, verification_code, 300)) {
//				if(MessageXsendUtils.sendMessage(phoneNumber, conversionCode)) {
                session.setAttribute("phoneNumber", phone_number);
                System.out.println(verification_code);
                return Msg.success("验证码发送成功！");
//				}else {
//					return Msg.fail("验证码发送失败！");
//				}
            } else {
                return Msg.fail("服务器出错(redis...)！", StatusEnum.HINT.getCode());
            }
        } else {
            return Msg.fail("该手机号已被注册！", StatusEnum.HINT.getCode());
        }
    }

    @Override
    public Msg sendVerificationCode_L(String phone_number, HttpSession session) {
        String verification_code = MessageXsendUtils.getConversionCode();
        if (phone_number == null) {
            return Msg.fail("手机号不能为空！", StatusEnum.HINT.getCode());
        }
        if (userRepository.findByPhoneNumber(phone_number) == null) {
            return Msg.fail("该手机号未注册！", StatusEnum.HINT.getCode());
        }
        if (redisUtils.set(phone_number + verification_code, verification_code, 300)) {
//			if(MessageXsendUtils.sendMessage(phoneNumber, conversionCode)) {
            session.setAttribute("phoneNumber", phone_number);
            System.out.println(verification_code);
            return Msg.success("验证码发送成功！");
//			}else {
//				return Msg.fail("验证码发送失败！");
//			}
        } else {
            return Msg.fail("服务器出错(redis...)！", StatusEnum.HINT.getCode());
        }
    }
}
