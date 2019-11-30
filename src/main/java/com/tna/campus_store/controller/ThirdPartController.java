package com.tna.campus_store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.service.ThirdPartService;

@RestController
@RequestMapping("/third-part")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class ThirdPartController {

    private ThirdPartService thirdPartService;

    @Autowired
    public ThirdPartController(ThirdPartService thirdPartService) {
        super();
        this.thirdPartService = thirdPartService;
    }

    @RequestMapping("/submail/send_r")
    public Msg registerByMobilePhoneSendCode_R(@RequestParam(value = "phone_number") String phone_number, HttpSession session) {
        return thirdPartService.sendVerificationCode_R(phone_number, session);
    }

    @RequestMapping("/submail/send_l")
    public Msg registerByMobilePhoneSendCode_L(@RequestParam(value = "phone_number") String phone_number, HttpSession session) {
        return thirdPartService.sendVerificationCode_L(phone_number, session);
    }
}
