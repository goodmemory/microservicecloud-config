package com.myq.service;

import java.util.List;

import com.myq.model.Comment;

public interface CommentService {
	
	//通过新闻id返回所有评论
    List<Comment> findComment(Comment comment);
    //添加评论 
    int insertByComment(Comment comment);
    //删除评论
    void deleteByComment(Integer commentId);
    //通过newsId查询所有评论
    List<Comment> findCommentByNewsId(Integer newsId);
}
