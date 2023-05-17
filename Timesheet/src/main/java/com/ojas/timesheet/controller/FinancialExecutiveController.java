package com.ojas.timesheet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ojas.timesheet.entity.TimeSheet;
import com.ojas.timesheet.service.TimeSheetService;

/*
 * @Author
 * @Akshaya Mahanty
 * */
@Controller
@RequestMapping("/financialexecutive")
public class FinancialExecutiveController {
	private static final Logger logger = LogManager.getLogger(FinancialExecutiveController.class);

	@Autowired
	private TimeSheetService timeSheetService;

	@GetMapping("/fxDashboard")
	public String dashboard(Model model) {
		return "financialexecutive/fxDashboard";
	}

	@GetMapping("/pmoapproved")
	public String pmoApprovedStatus(Model model) {
		return "financialexecutive/pmoApprovedStatus";
	}

	@GetMapping("/pendingstatus")
	public String pendingStatus(Model model) {
		return "admin/pendingStatus";
	}

	@PostMapping(path = "pmoapproved", consumes = "application/json", produces = "application/json")
	public @ResponseBody String filterApprovedDate(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		String startDate = jobj.get("start").getAsString();
		logger.info("Start Date is  ---" + startDate);
		String endDate = jobj.get("end").getAsString();
		logger.info("End Date is  ---" + endDate);
		logger.info("ajax req is  ---" + ajaxRequest);
		String status = "PA";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// List<TimeSheet> filterDate =
		// timeSheetService.findByStartDateAndEndDate(sDate, eDate, status);
		List<TimeSheet> filterDate = timeSheetService.findByStartDateAndEndDateOpt(sDate, eDate, status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(filterDate);
		System.out.println("syso :" + filterDate);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}

	@RequestMapping(value = "/fxapproval/{id}/{feedback}", method = RequestMethod.GET)
	public ResponseEntity<Object> timesheetApprove(@PathVariable int id, @PathVariable String feedback) {
		ResponseEntity<Object> update = timeSheetService.financialApprove(id, feedback);
		return (ResponseEntity<Object>) new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}

	@RequestMapping(value = "/fxreject/{id}/{feedback}", method = RequestMethod.GET)
	public ResponseEntity<Object> timesheetRejected(@PathVariable int id, @PathVariable String feedback) {
		ResponseEntity<Object> update = timeSheetService.financialRejected(id, feedback);
		return (ResponseEntity<Object>) new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}

	@GetMapping("/financialpendingStatus")
	public String financialpendingStatus(Model model) {
		return "financialexecutive/financialpending";
	}

	@PostMapping(path = "financialpendingStatus", consumes = "application/json", produces = "application/json")
	public @ResponseBody String filterFinacePendingDate(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		String startDate = jobj.get("start").getAsString();
		logger.info("Start Date is  ---" + startDate);
		String endDate = jobj.get("end").getAsString();
		logger.info("End Date is  ---" + endDate);
		logger.info("ajax req is  ---" + ajaxRequest);
		String status = "PA";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TimeSheet> filterDate = timeSheetService.findByStartToEndDateFinacePending(sDate, eDate, status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(filterDate);
		System.out.println("syso :" + filterDate);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}

	@GetMapping("/allrequestapproved")
	public String totalApprovedTimesheetRequest(Model model) {
		return "financialexecutive/allrequestapproved";
	}

	@PostMapping(path = "allrequestapproved", consumes = "application/json", produces = "application/json")
	public @ResponseBody String AfterFinaceApprovedRequest(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		String startDate = jobj.get("start").getAsString();
		logger.info("Start Date is  ---" + startDate);
		String endDate = jobj.get("end").getAsString();
		logger.info("End Date is  ---" + endDate);
		logger.info("ajax req is  ---" + ajaxRequest);
		String status = "FA";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TimeSheet> filterDate = timeSheetService.findByStartToEndDateFinacePending(sDate, eDate, status);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(filterDate);
		System.out.println("syso :" + filterDate);
		logger.info("jsonString is ---" + jsonString);
		return jsonString;
	}
}
