package com.myq.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myq.model.Role;
import com.myq.service.RoleService;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;

@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("list")
	public String list(Role role,Model model) {
		List<Role> rolelist = roleService.findAllByRole(role);
		model.addAttribute("rolelist", rolelist);
		return "role/role_list";
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Role role,Model model,HttpServletRequest request) {
		roleService.insertByRole(role);
		model.addAttribute("msg", "角色添加成功！");
		model.addAttribute("url", request.getContextPath()+"/role/list");
		return "success";
	}
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(Role role,Model model,HttpServletRequest request) {
		roleService.updateByRole(role);
		model.addAttribute("msg", "角色修改成功！");
		model.addAttribute("url", request.getContextPath()+"/role/list");
		return "success";
	}
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(Integer roleId) {
		roleService.deleteByRole(roleId);
		return ResultBuilder.DELETE_ROLE.build();
	}
	@RequestMapping(value= {"findRoleById/{roleId}"})
	public String findRoleById(@PathVariable("roleId") Integer roleId,Model model) {
		Role role = roleService.findRoleById(roleId);
		model.addAttribute("role", role);
		return "role/role_update";
	}
//	@RequestMapping("listRole")
//	public String listRole(Role role,Model model) {
//		List<Role> rolelist = roleService.findAllByRole(role);
//		model.addAttribute("rolelist", rolelist);
//		return "role/role_change";
//	}
}
