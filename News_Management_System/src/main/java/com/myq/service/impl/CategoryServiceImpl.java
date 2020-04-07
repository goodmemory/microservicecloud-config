package com.myq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myq.dao.CategoryMapper;
import com.myq.dao.CategoryMapperExt;
import com.myq.model.Category;
import com.myq.redis.JedisPoolUtils;
import com.myq.redis.RedisKeysPre;
import com.myq.service.CategoryService;
import com.myq.utils.JsonUtil;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private CategoryMapperExt categoryMapperExt;
	@Autowired
	private JedisPoolUtils jedisPoolUtils;
	
	@Override
	public List<Category> findAll(Category category) {
		List<Category> list = categoryMapperExt.findAll(category);
		return list;
	}

	@Override
	public int insertCategory(Category category) {
		int selective = categoryMapper.insertSelective(category);
		if(selective!=0) {
			jedisPoolUtils.del(RedisKeysPre.CATEGORY);
			//缓存一天
			jedisPoolUtils.set(RedisKeysPre.CATEGORY+category.getCategoryId(), JsonUtil.toJSONString(category),86400);
		}
		return selective;
	}

	@Override
	public int updateCategory(Category category) {
		jedisPoolUtils.del(RedisKeysPre.CATEGORY_LIST);
		jedisPoolUtils.del(RedisKeysPre.CATEGORY+category.getCategoryId());
		return categoryMapper.updateByPrimaryKeySelective(category);
	}

	@Override
	public int deleteCategory(Integer categoryId) {
		jedisPoolUtils.del(RedisKeysPre.CATEGORY_LIST);
		jedisPoolUtils.del(RedisKeysPre.CATEGORY+categoryId);
		return categoryMapper.deleteByPrimaryKey(categoryId);
	}

	@Override
	public Category findCategoryById(Integer categoryId) {
		return categoryMapperExt.findCategoryById(categoryId);
	}
}
