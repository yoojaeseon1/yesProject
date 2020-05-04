package com.bit.yes.controller;

import java.sql.SQLException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.entity.UserVo;
import com.bit.yes.service.LoginService;


@Controller
public class JoinController {


	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	LoginService service;
	
	private final Logger logger = LoggerFactory.getLogger(JoinController.class);
	
	
	
	@RequestMapping(value="/signUp",method=RequestMethod.POST)
	public String signUp(@ModelAttribute  UserVo bean,Model model,ServletRequest req) throws Exception{
		req.setCharacterEncoding("UTF-8");
//		sqlSession.getMapper(LoginDAO.class).insertOne(bean);
		service.insertOne(bean);
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value="/checkIDDup", method=RequestMethod.POST)
		public String checkIDDup(String id) throws Exception{
		
		logger.info("into checkIDDup : " + id);
		
//		UserVo user = service.checkIDDup(id);
		String selectedID = service.selectID(id);
		
		if(selectedID == null)
			return "true";
		else
			return "false";
		
	}
	
	@ResponseBody
	@RequestMapping(value="/checkEmailDup", method=RequestMethod.GET)
	public String checkEmailDup(String email) throws Exception{
		
		logger.info("into checkEmailDup : " + email);
		
//		UserVo user = service.checkEmailDup(email);
		
		String selectedEmail = service.selectEmail(email);
		
		if(selectedEmail == null)
			return "true";
		else
			return "false";
		
	}
	
	
}
