package com.bit.yes.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.LoginDAO;
import com.bit.yes.model.entity.UserVo;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	SqlSession sqlSession;
	@Autowired
	LoginDAO service;

//   @RequestMapping("/login.yes")
//   public String login() {
//      return "login";
//   }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {

		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {

		logger.info("LOGOUT!!");
		logger.info("URI : " + request.getRequestURI());
		logger.info("URL : " + request.getRequestURL());
		logger.info("contextPath : " + request.getContextPath());
		logger.info("servletPath : " + request.getServletPath());
		logger.info("before page : " + request.getHeader("referer"));

		session.invalidate();

		logger.info("session end");
//      return "redirect:/"+request.getHeader("referer").substring(22);
		return "success";
	}

	// 아이디 찾기

	@ResponseBody
	@RequestMapping(value = "/find", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String find(String name, String email, String birth) throws Exception {
		String id = sqlSession.getMapper(LoginDAO.class).findId(name, email, birth);
		if (id != null)
			return id;
		else
			return "error";
	}

	@RequestMapping(value = "/findPw.yes", method = RequestMethod.GET)
	public String findPw() {
		return "findPw";
	}

	// 비밀번호 찾기

	@ResponseBody
	@RequestMapping(value = "/find2", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String find2(String id, String name, String email, String birth, String answer) throws Exception {

		logger.info("into find2");
//		String pw = sqlSession.getMapper(LoginDAO.class).findPw(id, name, birth, email, answer);
		
		logger.info("id : " + id);
		logger.info("name : " + name);
		logger.info("birth : " + birth);
		logger.info("email : " + email);
		logger.info("answer : " + answer);
		
		Map<String,String> params = new HashMap<>();
		
		params.put("id", id);
		params.put("name", name);
		params.put("birth", birth);
		params.put("email", email);
		params.put("answer", answer);
		
		String password = service.findPw(params);
		
		if (password != null) {
			return "success";
		} else {
			return "error";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/pwUpdate", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String pwUpdate(String id, String password) throws Exception {
//		int result = sqlSession.getMapper(LoginDAO.class).updatePw(password, id);
		
		Map<String, String> params = new HashMap<>();
		
		logger.info("id : "+ id);
		logger.info("password : "+ password);
		
		params.put("id", id);
		params.put("password", password);
		
		int result = service.updatePW(params);
		
		logger.info("result : " + result);
		
		if (result > 0)
			return "success";
		else
			return "fail";
	}

	@ResponseBody
	@RequestMapping(value = "/naverLogin", method = RequestMethod.POST)
	public String naverlogin(String email, String name, HttpSession session) throws Exception {
//	   public String naverlogin(String email,String name,String birthDate,HttpSession session) throws ParseException, SQLException {
		String[] id = email.split("@");

		UserVo bean = new UserVo();
		bean.setId("naver:" + id[0]);
		bean.setName(name);
		bean.setEmail(email);
		bean.setRegistNum("0");
//	  Date date=java.sql.Date.valueOf("0000-"+birthDate);
//	  bean.setBirthDate(date);

		logger.info(bean.toString());

		if (sqlSession.getMapper(LoginDAO.class).login(bean.getId()) == null) {
			sqlSession.getMapper(LoginDAO.class).insertOne(bean);
		}

//	  System.out.println("before set session statement : " + session.isNew());
		session.setAttribute("member", bean);
//	  System.out.println("after set session statement : " + session.isNew());
		return "success";
	}

	@RequestMapping(value = "/naverMain", method = RequestMethod.GET)
	public String naverMain() {

		logger.info("start naverMain");

		return "main";
	}

	// 일반 로그인
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String checkLogin(String id, String password, HttpSession session) throws Exception {

//     UserVo bean=sqlSession.getMapper(LoginDAO.class).loginCheck(id,password);
		
		Map<String, String> params = new HashMap<>();
		
		logger.info("loginCheck");
		logger.info("id : " + id);
		logger.info("password : " + password);
		
		
		params.put("id", id);
		params.put("password", password);
		UserVo bean = service.loginCheck(params);

		if (bean != null) { // login success
			session.setAttribute("member", bean);
//      	 System.out.println("로그인 성공");
			logger.info("login success");
			return "success";
		} else {
			return "fail";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkLogined", method = RequestMethod.GET)
	public String checkLogined(String clientID, HttpSession session) {

//	   System.out.println("into chekcLogined");
//	   System.out.println("hahahoho : " + hahahoho);
//	   System.out.println("reviewIndex : " + reviewIndex);
		UserVo loginedUser = (UserVo) session.getAttribute("member");
//	   System.out.println("session id : " + loginedUser.getId());
//	   System.out.println("writer's id : " + clientID);

		// 1 : success login, 2 : logined(equal writer), 3: no login

		if (loginedUser == null)
			return "3";
		else if (!loginedUser.getId().equals(clientID))
			return "2";
		else
			return "1";
	}

	@ResponseBody
	@RequestMapping(value = "/kakaologin", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String kakaologin(String id, String name, HttpSession session) throws Exception {

		logger.info("into kakaologin : " + id + ",  " + name);
		UserVo bean = new UserVo();

		bean.setId("kakao" + id.toString());
		bean.setName(name.substring(1, name.length() - 1));
		bean.setRegistNum("0");

		if (sqlSession.getMapper(LoginDAO.class).login(bean.getId()) == null)
			sqlSession.getMapper(LoginDAO.class).insertOne(bean);

		session.setAttribute("member", bean);
		return "내정보 수정 해주세요.";
	}
}