package com.ojas.timesheet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(Model model) {
		return "auth/login";
	}
	@RequestMapping("/")
	public String login1(Model model) {
		return "auth/login";
	}
	@RequestMapping("/logout")
	public String logout(Model model) {
		return "auth/login";
	}

//	@RequestMapping("/logerr")
//	public String error(Model model) {
//		return "common/error";
//	}
}
