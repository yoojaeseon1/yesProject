package com.bit.yes.service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bit.yes.model.LoginDAO;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.tools.RandomPassword;

@Service
public class LoginServiceImpl implements LoginService {

	Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	LoginDAO loginDAO;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private RandomPassword randomPassword;

	private String sendfrom = "wotjs8054@naver.com";

	@Override
	public int insertOne(UserVo user) throws Exception {

		return loginDAO.insertOne(user);

	}

	@Override
	public UserVo selectUserInfo(UserVo user) throws Exception {

		return loginDAO.selectUserInfo(user);
	}

	@Override
	public String selectPassword(UserVo user) throws Exception {

		logger.info("selectPassword - currentUser : " + user);
		String password = loginDAO.selectPassword(user);

		if (password != null)
			return "success";
		else
			return "fail";

	}
//	@Override
//	public String selectPassword(Map<String, String> params) throws Exception {
//		
//		return loginDAO.selectPassword(params);
//	}

	@Override
	public String updatePW(UserVo user) throws Exception {
		
		int result = loginDAO.updatePW(user);
		

		if (result > 0)
			return "success";
		else
			return "fail";
	}

	@Override
	public String sendEmailTempPW(UserVo user) throws Exception {

		UserVo userInfo = this.selectUserInfo(user);
		logger.info("sendEmailTempPW-userInfo : " + userInfo);

		if (userInfo != null) {

			String tempPassword = randomPassword.setPassword();

			user.setPassword(tempPassword);
			this.updatePW(user);

			String title = "yes 임시 비밀번호 발급 안내";
			String content = "고객님의 임시 비밀번호는 [" + tempPassword + "] 입니다.";

			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

				messageHelper.setFrom(sendfrom);
				messageHelper.setTo(user.getEmail());
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

	@Override
	public String selectID(String id) throws Exception {

		return loginDAO.selectID(id);
	}

	@Override
	public String selectEmail(String email) throws Exception {

		return loginDAO.selectEmail(email);

	}

	@Override
	public String findID(UserVo user) throws Exception {

		String id = loginDAO.findID(user);
		
		if (id != null)
			return id;
		else
			return "fail";
	}


	@Override
	public String login(UserVo user, HttpSession session) throws Exception {
		
		UserVo loginedBean = loginDAO.selectUserInfo(user);
		
		if (loginedBean != null) { // login success
			session.setAttribute("member", loginedBean);
			return "success";
		} else
			return "fail";
	}

	@Override
	public String loginWithNaver(UserVo user, HttpSession session) throws Exception {
		
		String[] id = user.getEmail().split("@");
		user.setId("naver_" + id[0]);
		user.setRegistNum("0");

		if (loginDAO.selectID(user.getId()) == null) {
			loginDAO.insertOne(user);
		}

		session.setAttribute("member", user);
		
		return "success";
	}

	@Override
	public String loginWithKakao(UserVo user, HttpSession session) throws Exception {
		
		String name = user.getName();
		
		user.setId("kakao_" + user.getId().toString());
		user.setName(name.substring(1, name.length() - 1));
		user.setRegistNum("0");

		if (loginDAO.selectID(user.getId()) == null)
			loginDAO.insertOne(user);

		session.setAttribute("member", user);
		
		return "success";
	}

	@Override
	public String checkLogined(String clientID, HttpSession session) throws Exception {
		
		UserVo loginedUser = (UserVo) session.getAttribute("member");

		// 1 : success login, 2 : logined(equal writer), 3: no login

		if (loginedUser == null)
			return "3";
		else if (!loginedUser.getId().equals(clientID))
			return "2";
		else
			return "1";
	}

}
