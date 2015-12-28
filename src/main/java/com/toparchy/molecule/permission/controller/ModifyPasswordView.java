package com.toparchy.molecule.permission.controller;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.toparchy.molecule.service.MemberRegistration;

@Model
public class ModifyPasswordView {
	@Inject
	private MemberRegistration memberRegistration;
	private String passWord;
	private String passWord_;

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord_() {
		return passWord_;
	}

	public void setPassWord_(String passWord_) {
		this.passWord_ = passWord_;
	}

	public void modifyPassword() {
		if (passWord.equals(passWord_)) {
			memberRegistration.modifyPassword(passWord);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "错误", "二次密码输入不一致！");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
}
