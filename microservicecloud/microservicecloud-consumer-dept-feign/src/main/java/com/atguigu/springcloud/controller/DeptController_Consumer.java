package com.atguigu.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;

@RestController
public class DeptController_Consumer {
	
	@Autowired
	private DeptService service;

	@RequestMapping(value="/consumer/dept/get/{id}",method=RequestMethod.GET)
	public Dept get(@PathVariable("id") Long id) {
		return this.service.get(id);
	}
	@RequestMapping(value="/consumer/dept/list",method=RequestMethod.GET)
	public List<Dept> list(){
		return this.service.list();
	}
	@RequestMapping(value="/consumer/dept/add",method=RequestMethod.POST)
	public Boolean add(Dept dept) {
		return this.service.add(dept);
	}
}
