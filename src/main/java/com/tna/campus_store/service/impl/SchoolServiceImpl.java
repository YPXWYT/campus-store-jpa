package com.tna.campus_store.service.impl;

import com.tna.campus_store.beans.*;
import com.tna.campus_store.repository.SchoolRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.SchoolService;
import com.tna.campus_store.utils.CommonsMethod;
import com.tna.campus_store.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;
    private RedisUtils redisUtils;
    private UserRepository userRepository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository,
                             RedisUtils redisUtils, UserRepository userRepository) {
        this.schoolRepository = schoolRepository;
        this.redisUtils = redisUtils;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Msg saveSchools(List<School> schools) {
        if (schools == null) {
            return Msg.fail("参数不能为空", StatusEnum.HINT.getCode());
        }
        List<School> save = schoolRepository.save(schools);
        if (save == null || save.isEmpty()) {
            return Msg.fail("操作失败...", StatusEnum.HINT.getCode());
        }
        return Msg.success("操作成功");
    }

    @Override
    public Msg findOneWithPros(Integer school_id) {
        if (school_id == null) {
            return Msg.fail("参数不能为空", StatusEnum.HINT.getCode());
        }
        School school = schoolRepository.findOne(school_id);
        if (school == null) {
            return Msg.fail("该学校不存在！", StatusEnum.HINT.getCode());
        }
        return Msg.success("操作成功！").add("school", school);
    }

    @Override
    public Msg findOneWithProsByToken(String token) {
        Msg judgeToken = CommonsMethod.judgeToken(token);
        if (judgeToken.getCode() == StatusEnum.SUCCESS.getCode()) {
            User user = (User) judgeToken.getData().get("user");
            Identification identification = CommonsMethod.getIdentificationRepository()
                    .findOne(user.getId());
            if (identification == null)
                return Msg.fail("该用户还未进行校方认证", StatusEnum.HINT.getCode());
            School school = schoolRepository.findOne(identification.getSchool_id());
        } else {
            return judgeToken;
        }
        return null;
    }
}
