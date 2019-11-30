package com.tna.campus_store.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Order;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.Role;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.beans.TokenMsg;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.RoleRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.UserService;
import com.tna.campus_store.utils.RedisUtils;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private ProductRepository productRepository;
	private RedisUtils redisUtils;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RedisUtils redisUtils,
			ProductRepository productRepository,RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.redisUtils = redisUtils;
		this.productRepository = productRepository;
		this.roleRepository = roleRepository;
	}
	
	public Msg loginByAccount(String account,String password) {
		User u1 = null;
		User u2 = null;
		User u3 = null;
		String uuid=null;
		if(account==null||password==null) {
			return Msg.fail("登录失败！请检查用户名或密码是否正确！",StatusEnum.HINT.getCode());
		}
		if(((u1 = userRepository.findByAccountAndPassword(account, password))!=null)||
				((u2 = userRepository.findByEmailAndPassword(account, password))!=null)||
				(u3 = userRepository.findByPhoneNumberAndPassword(account, password))!=null) {
			if(u1!=null) {
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, u1.getId(), 7200);
				return TokenMsg.success("登录成功！",uuid);
			}else if(u2!=null){
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, u2.getId(), 7200);
				return TokenMsg.success("登录成功！",uuid);
			}else {
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, u3.getId(), 7200);
				return TokenMsg.success("登录成功！",uuid);
			}
		}else {
			return Msg.fail("登录失败！请检查用户名或密码是否正确！",StatusEnum.HINT.getCode());
		}
	}
	
	public Msg loginByMobilePhone(String verification_code,HttpSession session) {
		String uuid=null;
		User user = null;
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		if(verification_code==null) {
			return Msg.fail("验证码不能为空！",StatusEnum.HINT.getCode());
		}
		if(redisUtils.hasKey(phoneNumber+verification_code)) {
			redisUtils.del(phoneNumber+verification_code);
			if(phoneNumber==null) {
				return Msg.fail("电话号码为空，请刷新后重发验证码！",StatusEnum.HINT.getCode());
			}
			if((user = userRepository.findByPhoneNumber(phoneNumber))!=null) {
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, user.getId(), 7200);
				return TokenMsg.success("登录成功！",uuid);
			}else {
				return Msg.fail("该号码还未注册！",StatusEnum.HINT.getCode());
			}			
		}else {
			return Msg.fail("验证码输入错误或者已过期！",StatusEnum.HINT.getCode());
		}
	}
	
	public Msg registerByMobilePhone(HttpSession session,User user) {
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		if(user.getAccount()==null||user.getEmail()==null||user.getPassword()==null) {
			return Msg.fail("参数不能为空！",StatusEnum.HINT.getCode());
		}
		if(phoneNumber!=null) {			
			if(userRepository.findByEmail(user.getEmail())==null) {
				if(userRepository.findByAccount(user.getAccount())==null) {
					if(userRepository.findByPhoneNumber(phoneNumber)==null) {
						user.setPhoneNumber(phoneNumber);
						userRepository.save(user);
						return Msg.success("操作成功！");
					}else {
						return Msg.fail("该手机号已被注册！",StatusEnum.HINT.getCode());
					}
				}else {
					return Msg.fail("该用户名已被注册！",StatusEnum.HINT.getCode());
				}
			}else {
				return Msg.fail("该邮箱已被注册！",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("电话号码不能为空！",StatusEnum.HINT.getCode());
		}		
	}	
	
	public Msg registerByMobilePhoneVerify(HttpSession session,String verification_code) {
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		if(redisUtils.hasKey(phoneNumber+verification_code)) {
			redisUtils.del(phoneNumber+verification_code);
			return Msg.success("验证成功！");
		}else {
			return Msg.fail("验证失败，验证码输入错误或者已过期！",StatusEnum.HINT.getCode());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {
			CountException.class,BalanceException.class
	})
	public Msg purchaseByAccount(User user, ProductKey pKey) throws CountException, BalanceException {
		Product product = productRepository.findOne(pKey.getProductId());
			if(product!=null) {
				if(product.getCount()>0) {
					if(user.getMoney()>=(product.getSellPrice()*pKey.getCount())) {
						Order order = userRepository.purchaseByAccount(product, user,pKey.getCount());
						return Msg.success("购买成功！").add("order", order);
					}else {
						throw new BalanceException("您的余额不足!");
					}
				}else {
					throw new CountException("您来晚啦，该商品已售完!");
				}
			}else {
				return Msg.fail("该商品不存在!",StatusEnum.HINT.getCode());
			}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {
			CountException.class,BalanceException.class
	})
	public Msg purchaseMultiByAccount(Integer user_id, List<ProductKey> pKeys) {
		User user = userRepository.findOne(user_id);
		if(user!=null) {
			for (ProductKey pKey : pKeys) {
				try {
					System.out.println(purchaseByAccount(user,pKey).getMsg());
				} catch (CountException | BalanceException e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return Msg.fail(e.getMessage(),StatusEnum.HINT.getCode());
				}
			}
			return Msg.success("购买成功！");
		}else {
			return Msg.fail("该用户不存在!",StatusEnum.HINT.getCode());
		}
	}

	@Override
	public Msg isExistEmail(String email) {
		if(email==null) {
			return Msg.fail("参数不能为空!",StatusEnum.HINT.getCode());
		}
		if(userRepository.findByEmail(email)!=null) {
			return Msg.fail("该邮箱已被注册",StatusEnum.HINT.getCode());
		}else {
			return Msg.success("该邮箱可用");
		}
	}

	@Override
	public Msg isExistAccount(String account) {
		if(account==null) {
			return Msg.fail("参数不能为空!",StatusEnum.HINT.getCode());
		}
		if(userRepository.findByAccount(account)!=null) {
			return Msg.fail("该账号已被注册",StatusEnum.HINT.getCode());
		}else {
			return Msg.success("该账号可用");
		}
	}

	@Override
	public Msg findOneByToken(String token) {
		User user = null;
		if(token==null) {
			return Msg.fail("参数不能为空!",StatusEnum.HINT.getCode());
		}
		Integer user_id = (Integer) redisUtils.get(token);
		if(user_id!=null) {
			if((user = userRepository.findOne(user_id))!=null) {
				return Msg.success("操作成功").add("user", user);
			}else {
				return Msg.fail("该用户不存在!",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("授权过期...",StatusEnum.AUTH_DUE.getCode());
		}
	}

	@Override
	public Msg findAll() {
		return Msg.success("操作成功").add("users", userRepository.findAll());
	}

	@Override
	@Transactional
	public Msg saveWithRole(User user,Integer role_id) {
		if(user==null||role_id==null) {
			return Msg.fail("参数不能为空!",StatusEnum.HINT.getCode());
		}
		if(user.getEmail()==null) {
			return Msg.fail("Email不能为空!",StatusEnum.HINT.getCode());
		}
		if(user.getAccount()==null) {
			return Msg.fail("Account不能为空!",StatusEnum.HINT.getCode());
		}
		if(user.getPhoneNumber()==null) {
			return Msg.fail("PhoneNumber不能为空!",StatusEnum.HINT.getCode());
		}
		if(userRepository.findByEmail(user.getEmail())==null) {
			if(userRepository.findByAccount(user.getAccount())==null) {
				if(userRepository.findByPhoneNumber(user.getPhoneNumber())==null) {
					Role role = roleRepository.findOne(role_id);
					if(role==null) {
						return Msg.fail("该角色不存在!",StatusEnum.HINT.getCode());
					}
					user.getRoles().add(role);
					roleRepository.save(role);
					userRepository.save(user);
					return Msg.success("操作成功");
				}else {
					return Msg.fail("该手机号已被注册！",StatusEnum.HINT.getCode());
				}
			}else {
				return Msg.fail("该用户名已被注册！",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("该邮箱已被注册！",StatusEnum.HINT.getCode());
		}	
	}

	@Override
	public Msg update(User user) {
		if(user==null) {
			return Msg.fail("参数不能为空!",StatusEnum.HINT.getCode());
		}
		if(user.getId()==null) {
			return Msg.fail("用户id不能为空!",StatusEnum.HINT.getCode());
		}
		if(userRepository.findOne(user.getId())==null) {
			return Msg.fail("该用户不存在!",StatusEnum.HINT.getCode());
		}
		userRepository.save(user);
		return Msg.success("操作成功");
	}
}
