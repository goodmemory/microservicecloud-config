package com.myq.service;

import java.util.List;

import com.myq.model.User;
import com.myq.utils.Result;

public interface UserService {
	
	//查询所有用户
    List<User> findByUser(User user);
    //增加用户
    int insertByUser(User user);
    //修改用户信息
    void updateByUser(User user);
    //删除用户
    void deleteByUser(Integer userId);
    //根据id查找用户
    User findUserById(Integer userId);
    //检验用户登录
    Result checkLogin(String username,String password);
    //检验前台用户登录
    Result checkFrontLogin(String username,String password);
    //查询用户是否重复
    public User findByUserNumber(String username);
    //通过用户id更改角色
    int updateRoleByUserId(Integer roleId,Integer userId);
}
