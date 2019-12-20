package com.bit.yes.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.omg.PortableServer.Servant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.UserDao;
import com.bit.yes.model.entity.UserVo;

@Controller
public class LoginController {


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
    	  return "?óê?ü¨:?ùºÏπòÌïò?äî ?ïÑ?ù¥?îîÍ∞? ?óÜ?äµ?ãà?ã§.";
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
         return "?Ñ±Í≥?";
      }else {
         return "?óê?ü¨:?ùºÏπòÌïò?äî ?†ïÎ≥¥Í? ?óÜ?äµ?ãà?ã§";
      }
   }

   @ResponseBody
   @RequestMapping(value="/pwUpdate", method=RequestMethod.POST,produces="application/text; charset=utf-8")
   public String pwUpdate(String id,String password) throws SQLException {
      int result=sqlSession.getMapper(UserDao.class).updatePw(password,id);
      if(result>0)
    	  return "?Ñ±Í≥?";
      else
    	  return "?óê?ü¨";
   }

   //Î°úÍ∑∏?ù∏
   @ResponseBody
   @RequestMapping(value="/check",method=RequestMethod.POST,produces="application/text; charset=utf-8")
   public String loginCheck(String id,String password,HttpSession session) throws SQLException {

     UserVo bean=sqlSession.getMapper(UserDao.class).loginCheck(id,password);

     if(bean!=null)
      { //Î°úÍ∑∏?ù∏ ?Ñ±Í≥?
    	 session.setAttribute("member", bean);
      	 System.out.println("Î°úÍ∑∏?ù∏ ?Ñ±Í≥?");
         return "?Ñ±Í≥?";
      }
      else
      {
    	  return "?ïÑ?ù¥?îî/ÎπÑÎ?Î≤àÌò∏Î•? ?ôï?ù∏?ï¥Ï£ºÏÑ∏?öî";
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
      return "?Ç¥?†ïÎ≥? ?àò?†ï ?ï¥Ï£ºÏÑ∏?öî.";
   }


}