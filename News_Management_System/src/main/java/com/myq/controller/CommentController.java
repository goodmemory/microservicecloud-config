package com.myq.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myq.model.Comment;
import com.myq.model.User;
import com.myq.service.CommentService;
import com.myq.utils.Constants;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;

@Controller
@RequestMapping("comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	/**
	 * 展示所有评论
	 * @param comment
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(Comment comment,Model model) {
		List<Comment> commentlist = commentService.findComment(comment);
		model.addAttribute("commentlist", commentlist);
		return "comment/comment_list";
	}
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Result add(Comment comment,HttpServletRequest request ) {
		//你要判断下啊
		if(StringUtils.isBlank(comment.getCommentContent())||comment.getNewsId()==null) {
			return ResultBuilder.PARAM_ERROR.build();
		}
		User user=(User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		comment.setUserId(user.getUserId());
		comment.setUsername(user.getUsername());
		comment.setCreateTime(new Date());
		commentService.insertByComment(comment);
		return ResultBuilder.ADD_COMMENT.build();
	}
	/**
	 * 删除评论
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result delete(Integer commentId) {
		commentService.deleteByComment(commentId);
		return ResultBuilder.DELETE_COMMENT.build();
	}
	/**
	 * 根据新闻id查询所有的评论
	 * @param newsId
	 * @param model
	 * @return
	 */
//	@RequestMapping(value="showComByNewsId")
//	public String showComByNewsId(Integer newsId,Model model) {
////		List<Comment> commentlist = commentService.findCommentByNewsId(newsId);
////		model.addAttribute("commentlist", commentlist);
////		return ResultBuilder.SUCCESS.build(commentlist);
//		return "front_end/comment" ;
//	}
}
