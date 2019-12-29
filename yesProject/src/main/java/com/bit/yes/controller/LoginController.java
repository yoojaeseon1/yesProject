package com.bit.yes.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.UserDao;
import com.bit.yes.model.entity.UserVo;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

   @Autowired
   SqlSession sqlSession;


   @RequestMapping("/login.yes")
   public String login() {
      return "login";
   }

   @RequestMapping(value="/logout",method=RequestMethod.GET)
   public String logout(HttpSession session) {
      session.invalidate();
      return "redirect:/";
   }


   @ResponseBody
   @RequestMapping(value="/find",method=RequestMethod.POST,produces="application/text; charset=utf-8")
   public String find(String name, String email,String birth) throws SQLException {
      String id=sqlSession.getMapper(UserDao.class).findId(name, email,birth);
      if(id!=null)
    	  return id;
      else
    	  return "?��?��:?��치하?�� ?��?��?���? ?��?��?��?��.";
   }
   
   @RequestMapping(value="/findPw.yes",method=RequestMethod.GET)
   public String findPw() {
      return "findPw";
   }


   @ResponseBody
   @RequestMapping(value="/find2",method=RequestMethod.POST,produces="application/text; charset=utf-8")
   public String find2(String id,String name, String email,String birth, String answer) throws SQLException {
      String pw=sqlSession.getMapper(UserDao.class).findPw(id, name, birth, email, answer);
      if(pw!=null) {
         return "?���?";
      }else {
         return "?��?��:?��치하?�� ?��보�? ?��?��?��?��";
      }
   }

   @ResponseBody
   @RequestMapping(value="/pwUpdate", method=RequestMethod.POST,produces="application/text; charset=utf-8")
   public String pwUpdate(String id,String password) throws SQLException {
      int result=sqlSession.getMapper(UserDao.class).updatePw(password,id);
      if(result>0)
    	  return "?���?";
      else
    	  return "?��?��";
   }

   //로그?��
   @ResponseBody
   @RequestMapping(value="/check",method=RequestMethod.POST,produces="application/text; charset=utf-8")
   public String loginCheck(String id,String password,HttpSession session) throws SQLException {

     UserVo bean=sqlSession.getMapper(UserDao.class).loginCheck(id,password);

     if(bean!=null)
      { // login success
    	 session.setAttribute("member", bean);
//      	 System.out.println("로그인 성공");
    	 logger.info("login success");
         return "success";
      }
      else
      {
    	  return "fail";
      }
   }


   @ResponseBody
   @RequestMapping(value = "/naverlogin", method = RequestMethod.POST)
   public String naverlogin(String email,String name,String birthDate,HttpSession session) throws ParseException, SQLException {
	  String[] id=email.split("@");

	  UserVo bean=new UserVo();
	  bean.setId("naver"+id[0]);
	  bean.setName(name);
	  bean.setEmail(email);
	  bean.setRegistNum("0");
	  Date date=java.sql.Date.valueOf("0000-"+birthDate);
	  bean.setBirthDate(date);
	  
	  
	  if(sqlSession.getMapper(UserDao.class).login(bean.getId())==null)
		  {sqlSession.getMapper(UserDao.class).insertOne(bean);
		  }
	  session.setAttribute("member", bean);
      return "success";
   }


   @ResponseBody
   @RequestMapping(value = "/kakaologin", method = RequestMethod.POST,produces="application/text; charset=utf8")
   public String kakaologin(String id,String name,HttpSession session) throws SQLException {
	   UserVo bean=new UserVo();
	   
	   bean.setId("kakao"+id.toString());
	   bean.setName(name.substring(1, name.length()-1));
	   bean.setRegistNum("0");
	   
	   if(sqlSession.getMapper(UserDao.class).login(bean.getId())==null)
		   sqlSession.getMapper(UserDao.class).insertOne(bean); 

	  session.setAttribute("member", bean);
      return "?��?���? ?��?�� ?��주세?��.";
   }


}