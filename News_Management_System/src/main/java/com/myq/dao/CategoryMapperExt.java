package com.myq.dao;

import java.util.List;

import com.myq.model.Category;

public interface CategoryMapperExt {
    //查询所有新闻分类
	List<Category> findAll(Category category);
	//通过id查询种类信息
	Category findCategoryById(Integer categoryId);
}