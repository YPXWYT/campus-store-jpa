package com.tna.campus_store.test;

import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.RoleRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserControllerTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;

	@Test
	public void testLogin() {
//		System.out.println(userRepository.findByAccountAndPassword("ypx", "admin"));
//		System.out.println(userRepository.findByEmailAndPassword("1259606820@qq.com", "admin"));
//		System.out.println(userRepository.findByPhoneNumberAndPassword("18785920905", "admin"));
//		System.out.println(userRepository.findByPhoneNumber("18785920905"));
	}
	
	@Test
	public void save() {
		User user = new User();
		user.setId(1);
		user.setAccount("ypx");
		user.setPassword("admin");
		user.setMoney(100.0);
		user.setEmail("1259606820@qq.com");
		user.setPhoneNumber("18785920905");
		System.out.println(userRepository.save(user));
		
		User user1 = new User();
		user1.setId(2);
		user1.setAccount("ljq");
		user1.setPassword("123456");
		user1.setMoney(100.0);
		user1.setEmail("ljq@qq.com");
		user1.setPhoneNumber("18888888888");
		System.out.println(userRepository.save(user1));
		
	}

	@Test
	public void purchase() {
		Product pro = productRepository.findOne(1);
		User user = userRepository.findOne(1);
		userRepository.purchaseByAccount(pro,user,2);
//		userRepository.generatorOrder(1,1);		
	}
	
	@Test
	public void purchaseMultiByAccount() {
		List<ProductKey> pKeys = new ArrayList<ProductKey>();
		pKeys.add(new ProductKey(1, 2));
		pKeys.add(new ProductKey(2, 2));
		User user = userRepository.findOne(1);
		System.out.println(userService.purchaseMultiByAccount(1,pKeys).getMsg());
//		userRepository.generatorOrder(1,1);		
	}
}
