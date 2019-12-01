package com.tna.campus_store.service.impl;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.School;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.repository.SchoolRepository;
import com.tna.campus_store.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;
    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    @Transactional
    public Msg saveSchools(List<School> schools) {
        if (schools==null){
            return Msg.fail("参数不能为空", StatusEnum.HINT.getCode());
        }
        List<School> save = schoolRepository.save(schools);
        if(save==null||save.isEmpty()){
            return Msg.fail("操作失败...", StatusEnum.HINT.getCode());
        }
        return Msg.success("操作成功");
    }
}
