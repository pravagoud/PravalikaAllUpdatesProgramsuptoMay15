package com.ojas.timesheet.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ojas.timesheet.entity.TimeSheet;
import com.ojas.timesheet.entity.User;
import com.ojas.timesheet.entity.UserRegistarion;
import com.ojas.timesheet.repo.TimeSheetRepo;
import com.ojas.timesheet.repo.UserRegistarionRepo;
import com.ojas.timesheet.repo.UserRepository;
import com.ojas.timesheet.service.TimeSheetService;
import com.ojas.timesheet.service.impl.UserRegistarionService;
/*
 * @Author
 * @Akshaya Mahanty
 * */
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LogManager.getLogger(AdminController.class);
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserRegistarionRepo userRegRepo;
	@Autowired
	private TimeSheetRepo timeSheetRepo;
	@Autowired
	private TimeSheetService timeSheetService;
	@Autowired
	private UserRegistarionRepo userRegistarionRepo;
	@Autowired
	private UserRegistarionService userRegistarionService;
	
	@GetMapping("/sidebar")
	public String ojasFormat(Model model) {
		return "admin/sidebar";
	}
	@GetMapping("/navbar")
	public String ojasNavbar(Model model) {
		return "admin/navbar";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "admin/adminDashboard";
	}
	
	@GetMapping("/pendingstatus")
	public String pendingStatus(Model model) {
		return "admin/pendingStatus";
	}
	@GetMapping("/approvedstatus")
	public String approvedStatus(Model model) {
		return "admin/approvedStatus";
	}
	
	@GetMapping("/allemptimesheet")
	public String userTimesheet(Model model) {
		List<TimeSheet> timesheetList = timeSheetRepo.findAll();
		model.addAttribute("timesheetList", timesheetList);
		return "admin/checkAll";
	}
	
	@PostMapping(path = "filterdate", consumes = "application/json", produces = "application/json")
	public @ResponseBody String filterDate(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis=System.currentTimeMillis();  
	    java.sql.Date date = new java.sql.Date(millis); 

		String startDate = jobj.get("start").getAsString();
		String endDate = jobj.get("end").getAsString();
		String status = "P";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	//	List<TimeSheet> filterDate = timeSheetService.findByStartDateAndEndDate(sDate, eDate, status);
		List<TimeSheet> filterDate = timeSheetService.findByStartDateAndEndDateOpt(sDate, eDate, status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(filterDate);
		System.out.println("syso :"+filterDate);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}
	@PostMapping(path = "filterapproveddate", consumes = "application/json", produces = "application/json")
	public @ResponseBody String filterApprovedDate(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis=System.currentTimeMillis();  
	    java.sql.Date date = new java.sql.Date(millis); 

		String startDate = jobj.get("start").getAsString();
		logger.info("Start Date is  ---" + startDate);
		String endDate = jobj.get("end").getAsString();
		logger.info("End Date is  ---" + endDate);
		logger.info("ajax req is  ---" + ajaxRequest);
		String status = "A";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	//	List<TimeSheet> filterDate = timeSheetService.findByStartDateAndEndDate(sDate, eDate, status);
		List<TimeSheet> filterDate = timeSheetService.findByStartDateAndEndDateOpt(sDate, eDate, status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(filterDate);
		System.out.println("syso :"+filterDate);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String home(Model model) {
		return "admin/adminRegister";
	}
	
	@PostMapping("/registration")
	public String save(@RequestParam("name") String name, @RequestParam("employeeId") int employeeId,
			@RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("phoneNumber") double phoneNumber,
			@RequestParam("domain") String domain, @RequestParam("gender") String gender,@RequestParam("role") String role,
			@RequestParam("userAvtarPic") MultipartFile userAvtarPic, ModelMap model) {

		List<UserRegistarion> findall = userRegistarionRepo.findAll();
		for (UserRegistarion find : findall) {

			if (find.getEmployeeId() == employeeId) {
				model.addAttribute("error", "user id already exists...! " + employeeId);
				return "admin/adminRegister";
			}
			
			if (find.getEmail().equalsIgnoreCase(email) || find.getEmail().contentEquals(email)) {
				model.addAttribute("error", "email already exists...! " + email);
				return "admin/adminRegister";
			}
			if (find.getPhoneNumber() == phoneNumber) {
				model.addAttribute("error", "phone number already exists...! " + phoneNumber);
				return "admin/adminRegister";

			}
			String fileName = userAvtarPic.getOriginalFilename().toString();
			int index = fileName.lastIndexOf('.');
			String extension;
			if (index > 0) {
				extension = fileName.substring(index + 1);
				System.out.println("File extension is " + extension);
				if (extension.equalsIgnoreCase("jpg")) {
				} else {
					model.addAttribute("error", "please upload jpg... ");
					return "admin/adminRegister";
				}

			}

		}

		try

		{
			userRegistarionService.adminInserRecords(name, employeeId, email, password, phoneNumber, domain,
					gender, role, userAvtarPic);
		} catch (Exception e) {
			model.addAttribute("error", "failed...!");
			return "admin/adminRegister";
		}

		model.addAttribute("message", "successfully registered...!");
		return "admin/adminRegister";
	}
	
	@RequestMapping(value = "/approval/{id}/{feedback}", method = RequestMethod.GET)
	public ResponseEntity<Object> timesheetApprove(@PathVariable int id, @PathVariable String feedback) {
		ResponseEntity<Object> update = timeSheetService.timesheetApprove(id,feedback);
		return (ResponseEntity<Object>) new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}
	
	
	@RequestMapping(value = "/reject/{id}/{feedback}", method = RequestMethod.GET)
	public ResponseEntity<Object> timesheetRejected(@PathVariable int id, @PathVariable String feedback) {
		ResponseEntity<Object> update = timeSheetService.timesheetRejected(id,feedback);
		return (ResponseEntity<Object>) new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}
	
	
	@PostMapping(path = "filterdate1", consumes = "application/json", produces = "application/json")
	public @ResponseBody String filterDate1(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis=System.currentTimeMillis();  
	    java.sql.Date date = new java.sql.Date(millis); 

		String startDate = jobj.get("start").getAsString();
		logger.info("Start Date is  ---" + startDate);
		String endDate = jobj.get("end").getAsString();
		logger.info("End Date is  ---" + endDate);
		logger.info("ajax req is  ---" + ajaxRequest);
	//	String status = "P";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TimeSheet> filterDate1 = timeSheetService.findByStartDateAndEndDate(sDate, eDate);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(filterDate1);
		System.out.println("syso :"+filterDate1);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}
	
	@GetMapping("/resource")
	public String userHistory(Model model) {
		return "admin/resourceStatus";
	}
	@GetMapping("/resourcestatus")
	public Object resStatus() {
		List<User> user = userRepo.findAll();
		user.forEach(obj -> obj.setUserAvtarPic(null));
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(user);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}
	
	@GetMapping("/disableuser/{id}")
	public ResponseEntity<Object> userdisable(@PathVariable("id") long id) {
		Optional<User> userid = userRepo.findById(id);
		User userDb = userRepo.findById(id).get();
		if (userid.isEmpty()) {
			return ResponseEntity.badRequest().body("Given id is not exist.");
		}else {
			userDb.setEnabled(false);
			userRepo.saveAndFlush(userDb);
		}
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(userDb);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}

	@GetMapping("/enableuser/{id}")
	public ResponseEntity<Object> userEnable(@PathVariable("id") long id) {
		Optional<User> userid = userRepo.findById(id);
		User userDb = userRepo.findById(id).get();
		if (userid.isEmpty()) {
			return ResponseEntity.badRequest().body(" Given id is not exist.");
		}else {
			userDb.setEnabled(true);
			userRepo.saveAndFlush(userDb);
		}
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(userDb);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}

	
	@GetMapping(path = "/getUserDetails/{userName}", produces = "application/json")
	public Object getUserTimeSheetDetails(@PathVariable String userName) {
		System.out.println("hii : "+userName);
		User usernameDetails = userRepo.findByEmail(userName);
		logger.info("usernameDetails is  ---" + usernameDetails);
		System.out.println(usernameDetails);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(usernameDetails);
		return new ResponseEntity<Object>(jsonString,HttpStatus.OK);
	}
	@GetMapping(path = "/imageDownloader")
	public void imageDownloader(HttpServletResponse response) throws IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();	
		Optional<UserRegistarion> userReg = userRegRepo.findByEmail(username);
		byte[] cover = userReg.get().getUserAvtarPic();
		InputStream in = new ByteArrayInputStream(cover);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(in, response.getOutputStream());
	}
	
	@GetMapping("/pendingstatuscount")
	public Object pendingStatusCount() {
		YearMonth startYearMonth = YearMonth.now();
		java.time.LocalDate startOfMonthDate = startYearMonth.atDay(1);    
		java.time.LocalDate endOfMonthDate   = startYearMonth.atEndOfMonth();
		System.out.println("startOfMonthDate :"+startOfMonthDate);
		System.out.println("endOfMonthDate :"+endOfMonthDate);
		String status = "P";
		Integer currentMonthPendingCount = timeSheetRepo.findByCurrentMonthPendingStatus(startOfMonthDate,endOfMonthDate,status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(currentMonthPendingCount);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}
	@GetMapping("/approvedstatuscount")
	public Object approvedStatusCount() {
		YearMonth startYearMonth = YearMonth.now();
		java.time.LocalDate startOfMonthDate = startYearMonth.atDay(1);    
		java.time.LocalDate endOfMonthDate   = startYearMonth.atEndOfMonth();
		System.out.println("startOfMonthDate :"+startOfMonthDate);
		System.out.println("endOfMonthDate :"+endOfMonthDate);
		String status = "A";
		Integer currentMonthApprovedCount = timeSheetRepo.findByCurrentMonthApprovedStatus(startOfMonthDate,endOfMonthDate,status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(currentMonthApprovedCount);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}
}
