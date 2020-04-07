package com.myq.dao;

import java.util.List;

import com.myq.model.News;

public interface NewsMapperExt {
	//根据新闻标题模糊查询
    List<News> findNewsByTitle(String newsTitle);
    //查询所有新闻
    List<News> findNews(News news);
    //根据种类id查询所有新闻
    List<News> findNewsByCategory(Integer categoryId);
    //根据id删除新闻
    void deleteNewsById(Integer id);
    //根据新闻分类获取新闻的总数
    int findCountByCategoryId(int categoryId);
    //获取新闻的总数
    int findByCount();
    //根据新闻Id查询一条新闻信息以及种类
    News findNewsAndCategoryById(Integer newsId);
    //根据新闻Id查询一条新闻信息
    News findNewsById(Integer newsId);
}