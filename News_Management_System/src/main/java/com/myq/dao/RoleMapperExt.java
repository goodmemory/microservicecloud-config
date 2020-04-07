package com.myq.dao;

import java.util.List;

import com.myq.model.Role;

public interface RoleMapperExt {
	List<Role> findAllByRole(Role role);
	
	Role findRoleById(Integer roleId);
}
