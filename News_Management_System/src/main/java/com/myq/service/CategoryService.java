package com.myq.service;

import java.util.List;

import com.myq.model.Category;
import com.myq.model.News;

public interface CategoryService {
	
	//返回所有新闻种类
	List<Category> findAll(Category category);
	//增加新闻种类
	int insertCategory(Category category);
	//更新新闻种类
	int updateCategory(Category category);
	//删除新闻种类
	int deleteCategory(Integer categoryId);
	//通过id查询种类信息
	Category findCategoryById(Integer categoryId);
}
