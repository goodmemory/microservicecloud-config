package com.myq.redis;
/**
 * redis的所有键
 * @author wen
 *
 */
public class RedisKeysPre {
	
	public static final String CATEGORY="category:";
	public static final String CATEGORY_LIST="category:list";
	public static final String USER="user:";
	//user的所有数据的KEY
	public static final String USER_LIST="user:list";
	public static final String COMMENT="comment:";
	public static final String ROLE="role:";
	public static final String ROLE_LIST="role:list";
	public static final String NEWS="news:";
	//查询所有的新闻KEY
	public static final String NEWS_LIST="news:list";
	//根据分类查询新闻的KEY
	public static final String NEWS_CATEGORY_ID="news:category_id";
	//根据新闻标题模糊查询的KEY
	public static final String NEWS_TITLE="news:title";
}
