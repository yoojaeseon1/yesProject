package com.bit.yes.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

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
	public int insertOne(UserVo bean) throws Exception {

		return loginDAO.insertOne(bean);

	}

	@Override
	public UserVo selectUserInfo(UserVo bean) throws Exception {

		return loginDAO.selectUserInfo(bean);
	}

	@Override
	public String selectPassword(UserVo currentUser) throws Exception {

		logger.info("selectPassword - currentUser : " + currentUser);
		String password = loginDAO.selectPassword(currentUser);

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
	public String updatePW(UserVo bean) throws Exception {
		
		int result = loginDAO.updatePW(bean);
		

		if (result > 0)
			return "success";
		else
			return "fail";
	}

	@Override
	public String sendEmailTempPW(UserVo bean) throws Exception {

		UserVo userInfo = this.selectUserInfo(bean);
		logger.info("sendEmailTempPW-userInfo : " + userInfo);

		if (userInfo != null) {

			String tempPassword = randomPassword.setPassword();

			bean.setPassword(tempPassword);
			this.updatePW(bean);

			String title = "yes 임시 비밀번호 발급 안내";
			String content = "고객님의 임시 비밀번호는 [" + tempPassword + "] 입니다.";

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
//	@Override
//	public String findID(Map<String, String> params) throws Exception {
//		
//		return loginDAO.findID(params);
//	}

}
