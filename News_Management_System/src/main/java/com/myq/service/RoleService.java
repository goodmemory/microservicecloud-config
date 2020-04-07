package com.myq.service;

import java.util.List;

import com.myq.model.Role;

public interface RoleService {
	//显示所有角色
	List<Role> findAllByRole(Role role);
	//添加角色
	int insertByRole(Role role);
	//更新角色信息
	void updateByRole(Role role);
	//删除角色
	void deleteByRole(Integer roleId);
	//通过id查询角色信息
	Role findRoleById(Integer roleId);
}
