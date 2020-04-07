package com.myq.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myq.model.Category;
import com.myq.model.News;
import com.myq.model.User;
import com.myq.service.CategoryService;
import com.myq.service.NewsService;
@Controller
public class CommonContoller {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value="bootm",method=RequestMethod.GET)
	public String bootm() {
		return "bootm";
	}
//	@RequestMapping(value="register",method=RequestMethod.GET)
//	public String register() {
//		return "register";
//	}
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	@RequestMapping(value="roleIndex",method=RequestMethod.GET)
	public String roleIndex() {
		return "role/role_add";
	}
	@RequestMapping(value="categoryAdd",method=RequestMethod.GET)
	public String categoryAdd() {
		return "category/category_add";
	}
	@RequestMapping(value="newsAdd",method=RequestMethod.GET)
	public String newsAdd(Category category,Model model) {
		List<Category> categorylist = categoryService.findAll(category);
		model.addAttribute("categorylist", categorylist);
		return "news/news_add";
	}
	@RequestMapping(value="information",method=RequestMethod.GET)
	public String information(Model model,HttpServletRequest request,Integer userId) {
		User user = (User) request.getSession().getAttribute("user");
//		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		return "information";
	}

	//跳转至前端主页面
	@RequestMapping(value="/front_index",method=RequestMethod.GET)
	public String front_index( Category category, Model model) {
		List<Category> catelist = categoryService.findAll(category);
		model.addAttribute("catelist", catelist);
		return "front_end/index";
	}
	//跳转至前端主页面
	@RequestMapping(value="front_information",method=RequestMethod.GET)
	public String frontInformation(Integer categoryId,Category category, Model model) {
		List<Category> catelist = categoryService.findAll(category);
		model.addAttribute("catelist", catelist);
		List<News> listNews = newsService.findNewsByCategory(categoryId);
		model.addAttribute("listNews", listNews);
		return "front_end/information";
	}
	/**
	 * 前台跳转到注册界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register() {
		return "front_end/register";
	}
}
