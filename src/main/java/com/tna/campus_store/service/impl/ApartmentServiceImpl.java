package com.tna.campus_store.service.impl;

import com.tna.campus_store.beans.Apartment;
import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.School;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.repository.ApartmentRepository;
import com.tna.campus_store.repository.SchoolRepository;
import com.tna.campus_store.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApartmentServiceImpl implements ApartmentService {

    private SchoolRepository schoolRepository;
    private ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentServiceImpl(SchoolRepository schoolRepository, ApartmentRepository apartmentRepository) {
        this.schoolRepository = schoolRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public Msg saveApartmentsWithSchool(List<Apartment> apartments, Integer school_id) {
        if (school_id == null) {
            return Msg.fail("参数不能为空", StatusEnum.HINT.getCode());
        }
        School school = schoolRepository.findOne(school_id);
        if (school == null) {
            return Msg.fail("该学校不存在！", StatusEnum.HINT.getCode());
        }
        for (Apartment apt : apartments
        ) {
            apt.setSchool(school);
        }
        schoolRepository.save(school);
        apartmentRepository.save(apartments);
        return Msg.success("操作成功！");
    }
}
