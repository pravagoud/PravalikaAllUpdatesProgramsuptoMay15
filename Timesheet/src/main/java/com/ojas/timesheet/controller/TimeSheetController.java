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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ojas.timesheet.entity.Client;
import com.ojas.timesheet.entity.Location;
import com.ojas.timesheet.entity.Technology;
import com.ojas.timesheet.entity.TimeSheet;
import com.ojas.timesheet.entity.User;
import com.ojas.timesheet.entity.UserRegistarion;
import com.ojas.timesheet.repo.ClientRepo;
import com.ojas.timesheet.repo.LocationRepo;
import com.ojas.timesheet.repo.TechnologyRepo;
import com.ojas.timesheet.repo.TimeSheetRepo;
import com.ojas.timesheet.repo.UserRegistarionRepo;
import com.ojas.timesheet.repo.UserRepository;
import com.ojas.timesheet.service.TimeSheetService;
import com.ojas.timesheet.service.impl.FileDataServiceImpl;

@Controller
@RequestMapping("/user")
public class TimeSheetController {

	private static final Logger logger = LogManager.getLogger(TimeSheetController.class);
	@Autowired
	private TimeSheetService timeSheetService;
	@Autowired
	private FileDataServiceImpl fileDataService;
	@Autowired
	private TimeSheetRepo timeSheetRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserRegistarionRepo userRegRepo;
	
	@Autowired
	private ClientRepo clientRepo;
	
	@Autowired
	private TechnologyRepo techRepo;
	
	@Autowired
	private LocationRepo locationRepo;


	@RequestMapping("/addtimesheet")
	public String userDashboard(Model model) {
		return "user/userDashboard";
	}
	@RequestMapping("/timesheet")
	public String addTimeSheet(Model model) {
		List<Client> clientDetails = clientRepo.findAll();
		List<Technology> techDetails = techRepo.findAll();
		List<Location> location = locationRepo.findAll();
		model.addAttribute("clientDetails", clientDetails);
		model.addAttribute("techDetails", techDetails);
		model.addAttribute("location", location);
		return "user/timesheet";
	}
	
	@GetMapping(path = "/getUserTimeSheetDetails/{userName}", produces = "application/json")
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
	
	@PostMapping(path = "timesheetdetails", consumes = "application/json", produces = "application/json")
	public @ResponseBody String addSalNegDetails(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		String empName = jobj.get("empName").getAsString();
		int empId = jobj.get("empId").getAsInt();
		String clientName = jobj.get("clientName").getAsString();
		String startDate = jobj.get("startDate").getAsString();
		String email = jobj.get("email").getAsString();
		String endDate = jobj.get("endDate").getAsString();
		String phNo = jobj.get("phNo").getAsString();
		String domain = jobj.get("domain").getAsString();
		String shift = jobj.get("shift").getAsString();
		String timings = jobj.get("timings").getAsString();
		String location = jobj.get("location").getAsString();
		String lat = jobj.get("lat").getAsString();
		String lon = jobj.get("lon").getAsString();
		String ip = jobj.get("ip").getAsString();
		String locDetails = jobj.get("locDetails").getAsString();

		logger.info("ajax req is  ---" + ajaxRequest);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		TimeSheet timesheet = new TimeSheet();
		timesheet.setEmpName(empName);
		timesheet.setEmpId(empId);
		timesheet.setClientName(clientName);
		timesheet.setEmail(email);
		timesheet.setPhoneNo(phNo);
		timesheet.setDomain(domain);
		timesheet.setStartDate(sDate);
		timesheet.setEndDate(eDate);
		timesheet.setCreateDate(date);
		timesheet.setStatus("P");
		timesheet.setShift(shift);
		timesheet.setTiming(timings);
		timesheet.setLocation(location);
		timesheet.setLatitude(lat);
		timesheet.setLongitude(lon);
		timesheet.setIpAddress(ip);
		timesheet.setLocDetails(locDetails);
		TimeSheet save = timeSheetService.addTimeSheetDetails(timesheet);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(timesheet);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}
	
	@GetMapping(path = "approvedstatuscountuser")
	public Object approvedStatusCount() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User usernameDetails = userRepo.findByEmail(username);
		int empId = usernameDetails.getEmployeeId();
		System.out.println("empId : " + empId);
//		YearMonth startYearMonth = YearMonth.now();
//		java.time.LocalDate startOfMonthDate = startYearMonth.atDay(1);    
//		java.time.LocalDate endOfMonthDate   = startYearMonth.atEndOfMonth();
//		System.out.println("startOfMonthDate :"+startOfMonthDate);
//		System.out.println("endOfMonthDate :"+endOfMonthDate);
		String status = "A";
//		Integer currentMonthApprovedCountUser = timeSheetRepo.findByCurrentMonthApprovedStatusEmpId(startOfMonthDate,endOfMonthDate,status,empId);
		Integer currentYearApprovedCountUser = timeSheetRepo.findByCurrentYearApprovedStatusEmpId(status,empId);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(currentYearApprovedCountUser);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}
	
	@GetMapping(path = "pendingstatuscountuser")
	public Object pendingStatusCount() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User usernameDetails = userRepo.findByEmail(username);
		int empId = usernameDetails.getEmployeeId();
		System.out.println("empId : " + empId);
		YearMonth startYearMonth = YearMonth.now();
		java.time.LocalDate startOfMonthDate = startYearMonth.atDay(1);    
		java.time.LocalDate endOfMonthDate   = startYearMonth.atEndOfMonth();
		System.out.println("startOfMonthDate :"+startOfMonthDate);
		System.out.println("endOfMonthDate :"+endOfMonthDate);
		String status = "P";
		Integer currentMonthPendingCountUser = timeSheetRepo.findByCurrentMonthPendingStatusEmpId(startOfMonthDate,endOfMonthDate,status,empId);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(currentMonthPendingCountUser);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}
}
