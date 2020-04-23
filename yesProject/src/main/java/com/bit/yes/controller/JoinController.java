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
	
	
	@RequestMapping("/join.yes")
	public String join() {
		return "join";
	}
	
	
	@RequestMapping("/branchJoin.yes")
	public String branchJoin() throws SQLException {

		return "branchJoin";
	}
	
	@RequestMapping("/customerJoin.yes")
	public String customerJoin() {
		return "customerJoin";
	}
	
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@ModelAttribute  UserVo bean,Model model,ServletRequest req) throws Exception{
		req.setCharacterEncoding("UTF-8");
//		sqlSession.getMapper(LoginDAO.class).insertOne(bean);
		service.insertOne(bean);
		return "redirect:/";
		
		
	}
	
	@ResponseBody
    @RequestMapping(value = "/test/remote", method = RequestMethod.POST)
    public  String remoteTest(String id,Model model,HttpServletRequest req) throws Exception {
    	
    	//String ref=req.getHeader("Referer").substring(26);
//    	UserVo user=sqlSession.getMapper(LoginDAO.class).login(id);
    	UserVo user= service.checkIDDup(id);
    	
    	if(user!=null) {
    		return "false";	
    	}
    	else {
    		return "true";
    	}
                        
    }
	
	@ResponseBody
	@RequestMapping(value="/checkIDDup", method=RequestMethod.GET)
	public String checkIDDup(String id) throws Exception{
		
		logger.info("into checkIDDup : " + id);
		
		UserVo user = service.checkIDDup(id);
		
		if(user != null)
			return "dup";
		else
			return "no dup";
		
	}
	
	
}
