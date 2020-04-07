package com.myq.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myq.dao.NewsMapper;
import com.myq.dao.NewsMapperExt;
import com.myq.model.News;
import com.myq.redis.JedisPoolUtils;
import com.myq.redis.RedisKeysPre;
import com.myq.service.NewsService;
import com.myq.utils.JsonUtil;
@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsMapperExt newsMapperExt;
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private JedisPoolUtils jedisPoolUtils;
	
	/**
	 * 根据新闻标题模糊查询
	 */
	@Override
	public List<News> findNewsByTitle(String newsTitle) {
		List<News> list=null;
		String json = jedisPoolUtils.get(RedisKeysPre.NEWS_TITLE);
		if(StringUtils.isNotBlank(json)) {
			list=JsonUtil.parseArray(json, News.class);
		}else {
			list = newsMapperExt.findNewsByTitle(newsTitle);
			jedisPoolUtils.set(RedisKeysPre.NEWS_TITLE, JsonUtil.toJSONString(list));
		}
		return list;
	}
	/**
	 * 查询所有新闻,排序
	 */
	@Override
	public List<News> findNews(News news) {
		List<News> list = newsMapperExt.findNews(news);
		return list;
	}
	/**
	 * 根据id查询新闻信息
	 */
	@Override
	public News findNewsAndCategoryById(Integer newsId) {
		String json = jedisPoolUtils.get(RedisKeysPre.NEWS+newsId);
		if(StringUtils.isNotBlank(json)) {
			return JsonUtil.parseObject(json, News.class);
		}
		News news = newsMapperExt.findNewsAndCategoryById(newsId);
		if(news!=null) {
			jedisPoolUtils.set(RedisKeysPre.NEWS+newsId, JsonUtil.toJSONString(news));
		}
		return news;
	}
	/**
	 * 根据分类查询新闻
	 */
	@Override
	public List<News> findNewsByCategory(Integer categoryId) {
		List<News> list=null;
		String json = jedisPoolUtils.get(RedisKeysPre.NEWS_CATEGORY_ID);
		if(StringUtils.isNotBlank(json)) {
			list=JsonUtil.parseArray(json, News.class);
		}else {
			list=newsMapperExt.findNewsByCategory(categoryId);
			jedisPoolUtils.set(RedisKeysPre.NEWS+categoryId, JsonUtil.toJSONString(list));
		}
		return list;
	}
	/**
	 * 根据id删除新闻
	 */
	@Override
	public void deleteNewsById(Integer newsId) {
		jedisPoolUtils.del(RedisKeysPre.NEWS+newsId);
		jedisPoolUtils.del(RedisKeysPre.NEWS_CATEGORY_ID);
		jedisPoolUtils.del(RedisKeysPre.NEWS_LIST);
		jedisPoolUtils.del(RedisKeysPre.NEWS_TITLE);
		newsMapper.deleteByPrimaryKey(newsId);
	}
	/**
	 * 修改新闻信息
	 */
	@Override
	public void updateNewById(News news) {
		jedisPoolUtils.del(RedisKeysPre.NEWS_CATEGORY_ID);
		jedisPoolUtils.del(RedisKeysPre.NEWS_LIST);
		jedisPoolUtils.del(RedisKeysPre.NEWS_TITLE);
		jedisPoolUtils.del(RedisKeysPre.NEWS+news.getNewsId());
		newsMapper.updateByPrimaryKeySelective(news);
	}
	@Override
	public int insertByNews(News news) {
		int selective = newsMapper.insertSelective(news);
		if(selective!=0) {
			jedisPoolUtils.del(RedisKeysPre.NEWS_CATEGORY_ID);
			jedisPoolUtils.del(RedisKeysPre.NEWS_LIST);
			jedisPoolUtils.del(RedisKeysPre.NEWS_TITLE);
			jedisPoolUtils.set(RedisKeysPre.NEWS+news.getNewsId(), JsonUtil.toJSONString(news));
		}
		return selective;
	}
	@Override
	public int findCountByCategoryId(int categoryId) {
		return newsMapperExt.findCountByCategoryId(categoryId);
	}
	@Override
	public int findByCount() {
		return newsMapperExt.findByCount();
	}
	@Override
	public List<News> finNewsByCategoryId(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public News findNewsById(Integer newsId) {
		News news = newsMapperExt.findNewsById(newsId);
		return news;
	}
	
	
}
