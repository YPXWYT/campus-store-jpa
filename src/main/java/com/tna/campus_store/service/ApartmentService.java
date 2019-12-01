package com.tna.campus_store.service;

import com.tna.campus_store.beans.Apartment;
import com.tna.campus_store.beans.Msg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApartmentService {
    Msg saveApartmentsWithSchool(List<Apartment> apartments, Integer school_id);
}
