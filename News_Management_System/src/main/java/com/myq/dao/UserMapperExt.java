package com.myq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myq.model.User;

public interface UserMapperExt {
	//查询所有用户
    List<User> findByUser(User user);
    //根据id查找用户
    User findUserById(Integer userId);
    //根据用户名和密码查找用户
    User findByUsername(@Param("username") String username);
    //通过用户id更改角色
    int updateRoleByUserId(@Param("roleId") Integer roleId,@Param("userId")Integer userId);
}