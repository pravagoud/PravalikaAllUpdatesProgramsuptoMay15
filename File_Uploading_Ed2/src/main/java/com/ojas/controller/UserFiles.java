package com.ojas.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojas.entity.FileUploadEntity;

@Controller
public class UserFiles {
	@Value("${user.storage}")
	private String dir;
	@GetMapping("/findUserCloud")
public String userFiles(@RequestParam("userName") String userName,HttpSession session) {
		System.out.println("------------------------------------");
		
		File f=new File(dir+userName);
		
		System.out.println(f);
	if(f.exists()) {
		System.out.println(userName+" this user data is availbale");
		session.setAttribute("result", userName+" Cloud");
		session.setAttribute("result1", userName);
		
		return "userCloud";
	}
	else {
		System.out.println("No such user");
		session.setAttribute("result", "NO such User");
		return "GetUserFiles";
	}
		
	
	
}
}
