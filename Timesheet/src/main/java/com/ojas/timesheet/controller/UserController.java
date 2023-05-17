package com.ojas.timesheet.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ojas.timesheet.entity.TimeSheet;
import com.ojas.timesheet.entity.User;
import com.ojas.timesheet.entity.UserRegistarion;
import com.ojas.timesheet.repo.TimeSheetRepo;
import com.ojas.timesheet.repo.UserRegistarionRepo;
import com.ojas.timesheet.repo.UserRepository;
import com.ojas.timesheet.service.TimeSheetService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LogManager.getLogger(UserController.class);
	@Autowired
	private TimeSheetRepo timeSheetRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TimeSheetService timeSheetService;
	@Autowired
	private UserRegistarionRepo useRegRepo;

	@GetMapping(path = "/usertimesheet")
	public Object userTimesheet() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User usernameDetails = userRepo.findByEmail(username);
		int empId = usernameDetails.getEmployeeId();
		System.out.println("empId : " + empId);
		List<TimeSheet> timesheetListWithFileData = timeSheetRepo.findByDefaultValue(empId);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(timesheetListWithFileData);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}

	@GetMapping("/")
	public String showHomePage(Model model) {
		return "auth/login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		return "user/UserRegistarionPage";
	}

	@GetMapping("/UploadHistory")
	public String userUploadHistory(Model model) {
		return "user/viewHistory";
	}

	@GetMapping("/sidebar")
	public String sidebar(Model model) {
		return "user/sidebar";
	}

	@GetMapping("/check")
	public String ojas(Model model) {
		return "user/timesheet";
	}
	@GetMapping("/getAllReg")
	public List<UserRegistarion> getAll(){
		return useRegRepo.findAll();
		
	}

}
