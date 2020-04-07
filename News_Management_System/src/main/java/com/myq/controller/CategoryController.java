package com.myq.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myq.model.Category;
import com.myq.service.CategoryService;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/list")
	public String list(Category category, Model model) {
		List<Category> catelist = categoryService.findAll(category);
		model.addAttribute("catelist", catelist);
		return "category/category_list";
		
	}
	
	@RequestMapping("/add")
	public String add(Category category,Model model,HttpServletRequest request) {
		categoryService.insertCategory(category);
		model.addAttribute("msg", "种类添加成功！");
		model.addAttribute("url", request.getContextPath()+"/category/list");
		return "success";
	}
	@RequestMapping("/update")
	public String update(Category category,Model model,HttpServletRequest request) {
		categoryService.updateCategory(category);
		model.addAttribute("msg", "种类修改成功！");
		model.addAttribute("url", request.getContextPath()+"/category/list");
		return "success";
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResultBuilder.DELETE_CATEGORY.build();
	}
	@RequestMapping("findCategoryById/{categoryId}")
	public String findCategoryById(@PathVariable("categoryId") Integer categoryId,Model model) {
		Category category = categoryService.findCategoryById(categoryId);
		model.addAttribute("category", category);
		return "category/category_update";
	}
}
