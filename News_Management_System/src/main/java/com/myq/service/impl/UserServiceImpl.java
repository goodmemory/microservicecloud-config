package com.myq.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myq.dao.UserMapper;
import com.myq.dao.UserMapperExt;
import com.myq.model.User;
import com.myq.model.UserExample;
import com.myq.redis.JedisPoolUtils;
import com.myq.redis.RedisKeysPre;
import com.myq.service.UserService;
import com.myq.utils.JsonUtil;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserMapperExt userMapperExt;
	@Autowired
	private JedisPoolUtils jedisPoolUtils;

	@Override
	public List<User> findByUser(User user) {
		List<User> list = userMapperExt.findByUser(user);
		return list;
	}
	@Override
	public User findByUserNumber(String username) {
		UserExample example=new UserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<User> list = userMapper.selectByExample(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public int insertByUser(User user) {
		int insertSelective = userMapper.insertSelective(user);
		if(insertSelective!=0) {
			jedisPoolUtils.del(RedisKeysPre.USER_LIST);
			jedisPoolUtils.set(RedisKeysPre.USER+user.getUserId(), JsonUtil.toJSONString(user));
		}
		return insertSelective;
	}

	@Override
	public void updateByUser(User user) {
		jedisPoolUtils.del(RedisKeysPre.USER_LIST);
		jedisPoolUtils.del(RedisKeysPre.USER+user.getUserId());
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void deleteByUser(Integer userId) {
		jedisPoolUtils.del(RedisKeysPre.USER_LIST);
		jedisPoolUtils.del(RedisKeysPre.USER+userId);
		userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public User findUserById(Integer userId) {
		String json=jedisPoolUtils.get(RedisKeysPre.USER+userId);
		if(StringUtils.isNotBlank(json)) {
			return JsonUtil.parseObject(json, User.class);
		}
		User user=userMapperExt.findUserById(userId);
		if(user!=null) {
			jedisPoolUtils.set(RedisKeysPre.USER+userId, JsonUtil.toJSONString(user),7200);//一个小时
		}
		return user;
	}
	@Override
	public Result checkLogin(String username, String password) {
		//调用dao方法
        User user = userMapperExt.findByUsername(username);
        //对user进行判
        if(user == null || !user.getPassword().equals(password)){
            return ResultBuilder.USER_ERROR.build();
        }
        if(user.getRoleId()!=1&&user.getRoleId()!=2) {
        	return ResultBuilder.USER_NO_AUTHORITY.build();
        }
        return ResultBuilder.SUCCESS.build(user);
	}
	@Override
	public int updateRoleByUserId(Integer roleId, Integer userId) {
		jedisPoolUtils.del(RedisKeysPre.USER+userId);
		return userMapperExt.updateRoleByUserId(roleId, userId);
	}
	@Override
	public Result checkFrontLogin(String username, String password) {
		//调用dao方法
        User user = userMapperExt.findByUsername(username);
        //对user进行判
        if(user == null || !user.getPassword().equals(password)){
            return ResultBuilder.USER_ERROR.build();
        }
        if(user.getRoleId()!=1&&user.getRoleId()!=2&&user.getRoleId()!=4) {
        	return ResultBuilder.USER_NO_AUTHORITY.build();
        }
        return ResultBuilder.SUCCESS.build(user);
	}
}
