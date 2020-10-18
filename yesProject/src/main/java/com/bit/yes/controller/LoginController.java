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
	@RequestMapping(value = "/sendEmailTempPW", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String sendEmailTempPW(UserVo user) throws Exception {

		return service.sendEmailTempPW(user);

	}

	@ResponseBody
	@RequestMapping(value = "/pwUpdate", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String updatePassword(UserVo user) throws Exception {

		return service.updatePW(user);
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String login(UserVo user, HttpSession session) throws Exception {

		return service.login(user, session);

	}

	@ResponseBody
	@RequestMapping(value = "/naverLogin", method = RequestMethod.POST)
	public String loginWithNaver(UserVo user, HttpSession session) throws Exception {

		return service.loginWithNaver(user, session);

	}

	@RequestMapping(value = "/naverMain", method = RequestMethod.GET)
	public String naverMain() {

		return "main";
	}

	@ResponseBody
	@RequestMapping(value = "/kakaologin", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String loginWithKakao(UserVo user, HttpSession session) throws Exception {

		return service.loginWithKakao(user, session);

	}

	@ResponseBody
	@RequestMapping(value = "/checkLogined", method = RequestMethod.GET)
	public String checkLogined(String clientID, HttpSession session) throws Exception {

		return service.checkLogined(clientID, session);

	}

}