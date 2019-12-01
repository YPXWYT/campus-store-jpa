package com.tna.campus_store.controller;

import com.tna.campus_store.beans.Apartment;
import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * order/{id}	GET
 * orders		GET
 * order/{id}	DELETE
 * order		POST
 * order		PUT
 *
 */
@RestController
@RequestMapping("/apartment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApartmentController {
    private ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService) {
        super();
        this.apartmentService = apartmentService;
    }

    @RequestMapping(value = "/save_apts", method = RequestMethod.POST)
    public Msg saveApartmentsWithSchool(@RequestBody List<Apartment> apartments,
                                        @RequestParam("school_id") Integer school_id) {
        return apartmentService.saveApartmentsWithSchool(apartments, school_id);
    }
}
