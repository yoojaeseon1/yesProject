package com.bit.yes.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.entity.UserVo;
import com.bit.yes.service.LoginService;
import com.bit.yes.tools.RandomPassword;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService service;

	@Autowired
	private JavaMailSender mailSender;

	private String sendfrom = "wotjs8054@naver.com";

	private RandomPassword randomPassword = new RandomPassword();

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {

		session.invalidate();

		return "success";
	}

	// 아이디 찾기

	@ResponseBody
	@RequestMapping(value = "/findID", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String findID(String name, String email, String birth) throws Exception {

		logger.info("into findID");
		logger.info("name : " + name);
		logger.info("email : " + email);
		logger.info("birthDate : " + birth);

		Map<String, String> params = new HashMap<>();

		params.put("name", name);
		params.put("email", email);
		params.put("birthDate", birth);

		String id = service.findID(params);

		if (id != null)
			return id;
		else
			return "error";

	}

	// 비밀번호 찾기

	@ResponseBody
	@RequestMapping(value = "/findPW", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String findPW(String id, String name, String email, String birth, String answer) throws Exception {

		Map<String, String> params = new HashMap<>();

		params.put("id", id);
		params.put("name", name);
		params.put("birth", birth);
		params.put("email", email);
		params.put("answer", answer);

		logger.info(birth);

		String password = service.selectPassword(params);

		if (password != null) {
			return "success";
		} else {
			return "error";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/findPWTempPW", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String findPWTemPW(String id, String name, String email) throws Exception {

		logger.info("findPWTemporarily");
		logger.info(id);
		logger.info(name);
		logger.info(email);

		UserVo bean = new UserVo();

		bean.setId(id);
		bean.setName(name);
		bean.setEmail(email);

		UserVo userInfo = service.selectUserInfo(bean);

		if (userInfo != null) {

			String tempPassword = randomPassword.setPassword();
			Map<String, String> params = new HashMap<>();

			params.put("id", bean.getId());
			params.put("password", tempPassword);

			service.updatePW(params);

			String title = "yes 임시 비밀번호 발급 안내";
			String content = "귀하의 임시 비밀번호는 [" + tempPassword + "] 입니다.";
			
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

				messageHelper.setFrom(sendfrom);
				messageHelper.setTo(bean.getEmail());
				messageHelper.setSubject(title);
				messageHelper.setText(content);

				mailSender.send(message);
			} catch (Exception e) {
				System.out.println(e);
			}

			return "success";
		} else {
			return "error";
		}

	}

	@ResponseBody
	@RequestMapping(value = "/pwUpdate", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String updatePassword(String id, String password) throws Exception {

		Map<String, String> params = new HashMap<>();

		params.put("id", id);
		params.put("password", password);

		int result = service.updatePW(params);

		if (result > 0)
			return "success";
		else
			return "fail";
	}

	@ResponseBody
	@RequestMapping(value = "/naverLogin", method = RequestMethod.POST)
	public String loginWithNaver(String email, String name, HttpSession session) throws Exception {

		String[] id = email.split("@");

		UserVo bean = new UserVo();
		bean.setId("naver_" + id[0]);
		bean.setName(name);
		bean.setEmail(email);
		bean.setRegistNum("0");

		if (service.selectID(bean.getId()) == null) {
			service.insertOne(bean);
		}

		session.setAttribute("member", bean);
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

		UserVo bean = new UserVo();

		bean.setId(id);
		bean.setPassword(password);
		logger.info(bean.toString());
		UserVo selectedBean = service.selectUserInfo(bean);


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

		UserVo bean = new UserVo();

		bean.setId("kakao_" + id.toString());
		bean.setName(name.substring(1, name.length() - 1));
		bean.setRegistNum("0");

		if (service.selectID(bean.getId()) == null)
			service.insertOne(bean);

		session.setAttribute("member", bean);
		return "success";
	}
}