package com.bit.yes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.entity.UserVo;
import com.bit.yes.service.LoginService;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService service;
	
	

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {

		session.invalidate();

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/findID", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String findID(UserVo user) throws Exception {
		
		return service.findID(user);

	}

	@ResponseBody
	@RequestMapping(value = "/findPW", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String findPW(UserVo user) throws Exception {

		return service.selectPassword(user);
	}

	@ResponseBody
	@RequestMapping(value = "/findPWTempPW", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String sendEmailTempPW(UserVo user) throws Exception {
		

		return service.sendEmailTempPW(user);

	}

	@ResponseBody
	@RequestMapping(value = "/pwUpdate", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String updatePassword(UserVo user) throws Exception {

		return service.updatePW(user);
	}

	@ResponseBody
	@RequestMapping(value = "/naverLogin", method = RequestMethod.POST)
	public String loginWithNaver(String email, String name, HttpSession session) throws Exception {

		String[] id = email.split("@");

		UserVo user = new UserVo();
		user.setId("naver_" + id[0]);
		user.setName(name);
		user.setEmail(email);
		user.setRegistNum("0");

		if (service.selectID(user.getId()) == null) {
			service.insertOne(user);
		}

		session.setAttribute("member", user);
		return "success";
	}

	@RequestMapping(value = "/naverMain", method = RequestMethod.GET)
	public String naverMain() {

		return "main";
	}

	// 일반 로그인
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String login(String id, String password, HttpSession session) throws Exception {

		UserVo user = new UserVo();

		user.setId(id);
		user.setPassword(password);
//		logger.info(user.toString());
		UserVo selectedBean = service.selectUserInfo(user);


		if (selectedBean != null) { // login success
			session.setAttribute("member", selectedBean);
			return "success";
		} else {
			return "fail";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkLogined", method = RequestMethod.GET)
	public String checkLogined(String clientID, HttpSession session) {

		UserVo loginedUser = (UserVo) session.getAttribute("member");

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
	public String loginWithKakao(String id, String name, HttpSession session) throws Exception {

		UserVo user = new UserVo();

		user.setId("kakao_" + id.toString());
		user.setName(name.substring(1, name.length() - 1));
		user.setRegistNum("0");

		if (service.selectID(user.getId()) == null)
			service.insertOne(user);

		session.setAttribute("member", user);
		return "success";
	}
}