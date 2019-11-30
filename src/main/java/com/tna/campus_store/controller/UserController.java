package com.tna.campus_store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.UserService;

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
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	private UserService userService;
	private UserRepository userRepository;
	
	@Autowired
	public UserController(UserService userService, UserRepository userRepository) {
		super();
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@RequestMapping("/login/account")
	public Msg loginByAccount(@RequestParam("account")String account,@RequestParam("password")String password) {
		return userService.loginByAccount(account, password);
	}
	
	@RequestMapping("/login/mobile_phone")
	public Msg loginByMobilePhone(@RequestParam("verification_code")String verification_code,HttpSession session) {
		return userService.loginByMobilePhone(verification_code, session);
	}
	
	@RequestMapping("/register")
	public Msg registerByMobilePhone(HttpSession session,@RequestBody User user) {
		return userService.registerByMobilePhone(session, user);
	}	
	
	@RequestMapping("/register/verify")
	public Msg registerByMobilePhoneVerify(HttpSession session,@RequestParam("verification_code") String verification_code) {
		return userService.registerByMobilePhoneVerify(session,verification_code);
	}
	@RequestMapping(value = "/purchaseByAccount",method = RequestMethod.POST)
	public Msg purchaseByAccount(@RequestParam("user_id") Integer user_id, @RequestBody ProductKey pKey) {
		User user = userRepository.findOne(user_id);
		if(user!=null) {
			System.out.println(user_id);
			System.out.println(pKey.getProductId()+"--"+pKey.getCount());
			try {
				return userService.purchaseByAccount(user, pKey);
			} catch (CountException | BalanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Msg.fail(e.getMessage(),StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("该用户不存在!",StatusEnum.HINT.getCode());
		}
	}
	
	@RequestMapping(value = "/purchaseMultiByAccount",method = RequestMethod.POST)
	public Msg purchaseMultiByAccount(@RequestParam("user_id") Integer user_id, @RequestBody List<ProductKey> pKeys) {
		return userService.purchaseMultiByAccount(user_id, pKeys);
	}
	
	@RequestMapping(value = "/is_exist_e",method = RequestMethod.GET)
	public Msg isExistEmail(@RequestParam("email")String email) {
		return userService.isExistEmail(email);
	}
	
	@RequestMapping(value = "/is_exist_a",method = RequestMethod.GET)
	public Msg isExistAccount(@RequestParam("account")String account) {
		return userService.isExistAccount(account);
	}
	@RequestMapping(value = "/find_u_one",method = RequestMethod.GET)
	public Msg findOneByToken(@RequestHeader("token")String token) {
		return userService.findOneByToken(token);
	}
	@RequestMapping(value = "/find_u_all",method = RequestMethod.GET)
	public Msg findAll() {
		return userService.findAll();
	}
	@RequestMapping(value = "/save_user",method = RequestMethod.POST)
	public Msg saveWithRole(@RequestBody User user,@RequestParam(value = "role_id",defaultValue = "2")Integer role_id) {
		return userService.saveWithRole(user, role_id);
	}
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public Msg update(@RequestBody User user) {
		return userService.update(user);
	}
}
