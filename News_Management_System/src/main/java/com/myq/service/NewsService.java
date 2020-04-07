package com.myq.service;

import java.util.List;

import com.myq.model.News;

public interface NewsService {

	//根据新闻标题模糊查询
	 List<News> findNewsByTitle(String newsTitle);
	//查询所有新闻
	List<News> findNews(News news);
	//根据id查询新闻
	News findNewsAndCategoryById(Integer id);
	//根据种类id查询所有新闻
    List<News> findNewsByCategory(Integer categoryId);
    //删除新闻
    void deleteNewsById(Integer id);
    //修改新闻内容
    void updateNewById(News news);
    //添加新闻
    int insertByNews(News news);
    //根据新闻分类获取新闻的总数
    int findCountByCategoryId(int categoryId);
    //获取新闻的总数
    int findByCount();
    //通过分类id查询所有这个类别的新闻
  	List<News> finNewsByCategoryId(Integer categoryId);
  	 //根据新闻Id查询一条新闻信息
    News findNewsById(Integer newsId);
}
