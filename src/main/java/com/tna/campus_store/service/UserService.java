package com.tna.campus_store.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;

public interface UserService {

	Msg loginByAccount(String account,String password);
	
	Msg loginByMobilePhone(String verification_code,HttpSession session);
	
	Msg registerByMobilePhone(HttpSession session,User user);
	
	Msg registerByMobilePhoneVerify(HttpSession session,String verification_code);
	
	Msg purchaseByAccount(User user, ProductKey pKey) throws CountException, BalanceException;
	
	Msg purchaseMultiByAccount(Integer user_id, List<ProductKey> pKeys);
	
	Msg isExistEmail(String email);
	
	Msg isExistAccount(String account);
	
	Msg findOneByToken(String token);
	
	Msg findAll();
	
	Msg saveWithRole(User user,Integer role_id);
	
	Msg update(User user);	
}
