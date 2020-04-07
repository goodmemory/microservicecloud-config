package com.myq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myq.model.User;
import com.myq.service.UserService;
import com.myq.utils.Constants;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = {"login","/"}, method = RequestMethod.GET)
	public String login(HttpSession session) {
		//如果已经登录了，访问登录页面的话还是进入到index页面
		if(session.getAttribute("username")!=null) {
			return "redirect:/index";
		}
		return "login";
	}
	@RequestMapping(value = {"front_login"}, method = RequestMethod.GET)
	public String front_login(HttpSession session) {
		return "front_login";
	}

	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Result doLogin(User user, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(username)) {
			return ResultBuilder.USERNAME_NULL.build();
		}
		if (StringUtils.isBlank(password)) {
			return ResultBuilder.PASSWORD_NULL.build();
		}
		Result res = userService.checkLogin(username, password);
		if (!res.isSuccess()) {
			return res;
		}
		request.getSession().setAttribute(Constants.SESSION_USERNAME_KEY, username);
		request.getSession().setAttribute(Constants.SESSION_USER_KEY, res.getData());
		return ResultBuilder.SUCCESS.build();
	}
	/**
	 * 前台登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "doFrontLogin", method = RequestMethod.POST)
	@ResponseBody
	public Result doFrontLogin(User user, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(username)) {
			return ResultBuilder.USERNAME_NULL.build();
		}
		if (StringUtils.isBlank(password)) {
			return ResultBuilder.PASSWORD_NULL.build();
		}
		Result res = userService.checkFrontLogin(username, password);
		if (!res.isSuccess()) {
			return res;
		}
		request.getSession().setAttribute(Constants.SESSION_USERNAME_KEY, username);
		request.getSession().setAttribute(Constants.SESSION_USER_KEY, res.getData());
		return ResultBuilder.SUCCESS.build();
	}
	/**
	 * 后台退出代码
	 * @param request
	 * @return
	 */
	@RequestMapping("loginout")
	public String loginOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/login";
	}
	/**
	 * 前台退出代码
	 * @param request
	 * @return
	 */
	@RequestMapping("front_loginout")
	public String front_loginout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/front_index";
	}
}
