package com.tna.campus_store.service;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.School;

import java.util.List;

public interface SchoolService {
    Msg saveSchools(List<School> schools);
}
