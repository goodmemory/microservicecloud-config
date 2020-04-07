package com.myq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myq.dao.RoleMapper;
import com.myq.dao.RoleMapperExt;
import com.myq.model.Role;
import com.myq.redis.JedisPoolUtils;
import com.myq.redis.RedisKeysPre;
import com.myq.service.RoleService;
import com.myq.utils.JsonUtil;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapperExt roleMapperExt;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private JedisPoolUtils jedisPoolUtils;

	@Override
	public List<Role> findAllByRole(Role role) {
		List<Role> list = roleMapperExt.findAllByRole(role);
		return list;
	}
	@Override
	public int insertByRole(Role role) {
		int selective = roleMapper.insertSelective(role);
		if(selective!=0) {
			jedisPoolUtils.del(RedisKeysPre.ROLE);
			jedisPoolUtils.set(RedisKeysPre.ROLE+role.getRoleId(), JsonUtil.toJSONString(role));
		}
		return selective;
	}

	@Override
	public void updateByRole(Role role) {
		jedisPoolUtils.del(RedisKeysPre.ROLE+role.getRoleId());
		jedisPoolUtils.del(RedisKeysPre.ROLE_LIST);
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void deleteByRole(Integer roleId) {
		jedisPoolUtils.del(RedisKeysPre.ROLE_LIST);
		jedisPoolUtils.del(RedisKeysPre.ROLE+roleId);
		roleMapper.deleteByPrimaryKey(roleId);
	}
	@Override
	public Role findRoleById(Integer roleId) {
		return roleMapperExt.findRoleById(roleId);
	}


}
