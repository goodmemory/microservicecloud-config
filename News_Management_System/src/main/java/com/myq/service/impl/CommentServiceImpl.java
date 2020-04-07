package com.myq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myq.dao.CommentMapper;
import com.myq.dao.CommentMapperExt;
import com.myq.model.Comment;
import com.myq.redis.JedisPoolUtils;
import com.myq.redis.RedisKeysPre;
import com.myq.service.CommentService;
import com.myq.utils.JsonUtil;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapperExt commentMapperExt;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private JedisPoolUtils jedisPoolUtils;

	@Override
	public List<Comment> findComment(Comment comment) {
		List<Comment> list = commentMapperExt.findComment(comment);
		return list;
	}

	@Override
	public int insertByComment(Comment comment) {
		int selective = commentMapper.insertSelective(comment);
		if(selective!=0) {
			jedisPoolUtils.del(RedisKeysPre.COMMENT+comment.getNewsId());
			//缓存一天
			jedisPoolUtils.set(RedisKeysPre.COMMENT+comment.getCommentId(), JsonUtil.toJSONString(comment));
		}
		return selective;
	}

	@Override
	public void deleteByComment(Integer commentId) {
		jedisPoolUtils.del(RedisKeysPre.COMMENT+commentId);
		commentMapper.deleteByPrimaryKey(commentId);
	}

	@Override
	public List<Comment> findCommentByNewsId(Integer newsId) {
		return commentMapperExt.findCommentByNewsId(newsId);
	}


}
