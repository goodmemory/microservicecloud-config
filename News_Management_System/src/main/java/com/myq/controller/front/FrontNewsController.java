package com.myq.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myq.model.Category;
import com.myq.model.Comment;
import com.myq.model.News;
import com.myq.service.CategoryService;
import com.myq.service.CommentService;
import com.myq.service.NewsService;

@Controller
@RequestMapping("frontNews")
public class FrontNewsController {

	@Autowired
	private NewsService newsService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("show")
	public String showNews(Category category,Integer newsId,Model model) {
		News news = newsService.findNewsById(newsId);
		model.addAttribute("news", news);
		List<Category> catelist = categoryService.findAll(category);
		model.addAttribute("catelist", catelist);
		List<Comment> commentlist = commentService.findCommentByNewsId(newsId);
		model.addAttribute("commentlist", commentlist);
		return "front_end/Content";
	}
}
