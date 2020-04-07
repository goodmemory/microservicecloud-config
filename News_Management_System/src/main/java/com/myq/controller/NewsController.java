package com.myq.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myq.model.Category;
import com.myq.model.News;
import com.myq.model.User;
import com.myq.service.CategoryService;
import com.myq.service.NewsService;
import com.myq.utils.Constants;
import com.myq.utils.Result;
import com.myq.utils.ResultBuilder;

@Controller
@RequestMapping("news")
public class NewsController {

	@Autowired
	private NewsService newsService;
	@Autowired
	CategoryService categoryService;

	public static final String BASEPATH = "/static/";
	public static final String IMAGEPATH = "/images/";

	/**
	 * 模糊查询新闻
	 * @param newsTitle
	 * @return
	 */
	@RequestMapping(value = "findNewsByTitle", method = RequestMethod.POST)
	public Result findNewsTitle(@RequestParam String newsTitle) {
		List<News> list = newsService.findNewsByTitle(newsTitle);
		return ResultBuilder.SUCCESS.build(list);
	}

	@RequestMapping(value = "list")
	public String find(News news, Model model,HttpSession session) {
		
		User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
		if(!user.getUsername().equals("admin")) {
			news.setUserId(user.getUserId());
		}
		List<News> newslist = newsService.findNews(news);
		model.addAttribute("newslist", newslist);
		return "news/news_list";
	}
	/**
	 * 通过新闻Id
	 * @param newsId
	 * @param model
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "findNewsById/{newsId}")
	public String findNewsById(@PathVariable("newsId") Integer newsId,Model model,Category category) {
		News news = newsService.findNewsAndCategoryById(newsId);
		List<Category> categorylist = categoryService.findAll(category);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("news", news);
		return "news/news_update";
	}

	@RequestMapping(value = "findNewsByCategory", method = RequestMethod.POST)
	public Result findNewsByCategory(Integer categoryId) {
		List<News> list = newsService.findNewsByCategory(categoryId);
		return ResultBuilder.SUCCESS.build(list);
	}
	/**
	 * 删除新闻
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(Integer newsId) {
		newsService.deleteNewsById(newsId);
		return ResultBuilder.DELETE_NEWS.build();
	}
	/**
	 * 修改新闻
	 * @param file
	 * @param model
	 * @param news
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam("file") MultipartFile file,Model model,
						News news, HttpServletRequest request)
			throws IllegalStateException, IOException {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		news.setUserId(user.getUserId());
		if (file != null) {
			// String path=ClassUtils.getDefaultClassLoader().getResource("").getPath();
			String path = request.getServletContext().getRealPath(IMAGEPATH);
			// 获取图片完整名称
			String fileStr = file.getOriginalFilename();
			// 使用随机生成的字符+原图片扩展名组成新图片名称
			String newfileStr = UUID.randomUUID().toString() + System.currentTimeMillis()
					+ fileStr.substring(fileStr.lastIndexOf("."));
			file.transferTo(new File(path + newfileStr));
			// 将图片命保存到数据库
			news.setNewsImage(IMAGEPATH + newfileStr);
			news.setLastEditTime(new Date());
		}
		newsService.updateNewById(news);
		model.addAttribute("msg", "新闻修改成功！");
		model.addAttribute("url", request.getContextPath() + "/news/list");
		return "success";
	}
	/**
	 * 添加新闻
	 * @param news
	 * @param model
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add")
	public String add(News news, Model model, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		news.setUserId(user.getUserId());
		try {
			if (!file.isEmpty()) {
				// String path=ClassUtils.getDefaultClassLoader().getResource("").getPath();
				String path = request.getServletContext().getRealPath(IMAGEPATH);
				File upload = new File(path);
				if (!upload.exists()) {
					upload.mkdirs();
				}
				// 获取原名称
				String oldFile = file.getOriginalFilename();
				// 新的文件名
				String newFile = UUID.randomUUID().toString() + System.currentTimeMillis()
						+ oldFile.substring(oldFile.lastIndexOf("."));
				file.transferTo(new File(path + newFile));
				// 将图片保存到数据库
				news.setNewsImage(IMAGEPATH + newFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		newsService.insertByNews(news);
//		categoryService.insertCategory(category);
		model.addAttribute("msg", "新闻添加成功！");
		model.addAttribute("url", request.getContextPath() + "/news/list");
		return "success";
	}

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

}
