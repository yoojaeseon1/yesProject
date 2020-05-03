package com.bit.yes.model.entity;

import java.sql.Date;


public class UserVo {

	private String id;
	private String password;
	private String name;
	private String nickName;
	private String email;
	private String phoneNum;
	private String pwQuestion;
	private Date birthDate;
	private String registNum; //사업자 등록번호
	private String acceptState;     //branchInfo의 매장 등록 여부

	
	public UserVo() {
		// TODO Auto-generated constructor stub
	}
	
	public UserVo(String id, String password, String name, String nickName, String email, String phoneNum, String pwQuestion, Date birthDate, String registNum, String acceptState) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.email = email;
		this.phoneNum = phoneNum;
		this.pwQuestion = pwQuestion;
		this.birthDate = birthDate;
		this.registNum = registNum;
		this.acceptState = acceptState;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


  public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPwQuestion() {
		return pwQuestion;
	}

	public void setPwQuestion(String pwQuestion) {
		this.pwQuestion = pwQuestion;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getRegistNum() {
		return registNum;
	}

	public void setRegistNum(String registNum) {
		this.registNum = registNum;
	}

	public String getacceptState() {
		return acceptState;
	}

	public void setacceptState(String acceptState) {
		this.acceptState = acceptState;
	}


	
	@Override
	public String toString() {
		return "UserVo{" +
				"id='" + id + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", nickName='" + nickName + '\'' +
				", email='" + email + '\'' +
				", phoneNum='" + phoneNum + '\'' +
				", pwQuestion='" + pwQuestion + '\'' +
				", birthDate=" + birthDate +
				", registNum='" + registNum + '\'' +
				", acceptState='" + acceptState + '\'' +
				'}';
	}
}
