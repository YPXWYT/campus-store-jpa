package com.tna.campus_store.controller;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.School;
import com.tna.campus_store.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * user/{id}	GET
 * users		GET
 * user/{id}	DELETE
 * user			POST
 * user			PUT
 *
 * user/login  	GET
 * user/register POST
 * user/purchase GET
 */
@RestController
@RequestMapping("/school")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SchoolController {
    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        super();
        this.schoolService = schoolService;
    }

    @PostMapping("/save_schools")
    public Msg saveSchools(@RequestBody List<School> schools){
        return schoolService.saveSchools(schools);
    }

}