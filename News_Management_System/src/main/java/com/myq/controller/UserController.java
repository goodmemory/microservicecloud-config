package com.myq.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myq.model.Role;
import com.myq.model.User;
import com.myq.service.RoleService;
import com.myq.service.UserService;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 展示所有用户信息
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(Model model,User user) {
		List<User> userlist = userService.findByUser(user);
		//return ResultBuilder.SUCCESS.build(userlist);
		model.addAttribute("userlist", userlist);
		return "user/user_list";
	}
	/**
	 * 注册用户
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="registeruser",method=RequestMethod.POST)
	@ResponseBody
	public Result register(@RequestBody User user,Model model,HttpServletRequest request) {
		if(userService.findByUserNumber(user.getUsername())!=null) {
			return ResultBuilder.USER_EXISTS.build();
		}
		int i = userService.insertByUser(user);
		if(i>0) {
			return ResultBuilder.USER_REGISTER_SUCCESS.build();
		}else {
			return ResultBuilder.USER_REGISTER_FAIL.build();
		}
	}
	/**
	 * 修改用户信息
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="update")
	public String update(User user,Model model,HttpServletRequest request) {
		userService.updateByUser(user);
		model.addAttribute("msg", "用户修改成功！");
		model.addAttribute("url", request.getContextPath()+"/user/list");
		return "success";
	}
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(Integer userId) {
		userService.deleteByUser(userId);
		return ResultBuilder.DELETE_USER.build();
	}
	/**
	 * 根据id查询用户信息
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findById/{userId}")
	public String findUserById(@PathVariable("userId") Integer userId,Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		return "user/user_update";
	}
	/**
	 * 跳转到更改角色页面
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(value="listUserRole")
	public String listUserRole(Model model,User user) {
		List<User> userlist = userService.findByUser(user);
		model.addAttribute("userlist", userlist);
		return "role/user_role_list";
	}
	@RequestMapping("changeRole/{userId}")
	public String changeRole(@PathVariable("userId") Integer userId,Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		List<Role> rolelist = roleService.findAllByRole(new Role());
		model.addAttribute("rolelist", rolelist);
		return "role/role_change";
	}
	@RequestMapping("updateRoleByUserId")
	public String updateRoleByUserId(Model model,User user,HttpServletRequest request) {
		int i = userService.updateRoleByUserId(user.getRoleId(), user.getUserId());
		if(i>0) {
			model.addAttribute("msg", "角色变更成功！");
			model.addAttribute("url", request.getContextPath()+"/user/listUserRole");
			return "success";
		}else {
			model.addAttribute("msg", "角色变更失败！");
			model.addAttribute("url", request.getContextPath()+"/user/listUserRole");
			return "error";
		}
	}
}
