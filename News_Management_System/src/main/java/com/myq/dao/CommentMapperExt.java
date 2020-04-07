package com.myq.dao;

import java.util.List;

import com.myq.model.Comment;

public interface CommentMapperExt {
	//通过新闻id返回所有评论
    List<Comment> findComment(Comment comment);
    //通过newsId查询所有评论
    List<Comment> findCommentByNewsId(Integer newsId);
}