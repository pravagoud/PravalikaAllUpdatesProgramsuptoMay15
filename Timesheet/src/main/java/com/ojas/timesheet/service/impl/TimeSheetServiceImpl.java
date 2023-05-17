package com.ojas.timesheet.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.timesheet.entity.TimeSheet;
import com.ojas.timesheet.repo.TimeSheetRepo;
import com.ojas.timesheet.service.TimeSheetService;

/*
 * @Author
 * @Akshaya Mahanty
 * */
@Service
public class TimeSheetServiceImpl implements TimeSheetService {

	@Autowired
	private TimeSheetRepo timeSheetRepo;

	@Override
	public TimeSheet addTimeSheetDetails(TimeSheet timeSheet) {
		return timeSheetRepo.save(timeSheet);
	}

	public void inserRecords(String empName, int empId, String email, String clientName, Date startDate, Date endDate,
			String phoneNo, String domain) throws IOException {
		TimeSheet timesheet = new TimeSheet();
		timesheet.setEmpName(empName);
		timesheet.setEmpId(empId);
		timesheet.setEmail(email);
		timesheet.setClientName(clientName);
		timesheet.setDomain(domain);
		timesheet.setStartDate(startDate);
		timesheet.setEndDate(endDate);
		timesheet.setPhoneNo(phoneNo);

		timeSheetRepo.save(timesheet);
//
//		UserRegistarion userRegistarion = new UserRegistarion();
//		userRegistarion.setName(name);
//		userRegistarion.setEmployeeId(employeeId);
//		userRegistarion.setEmail(email);
//		userRegistarion.setUserName(userName);
//		userRegistarion.setPassword(encodedPassword);
//		userRegistarion.setPhoneNumber(phoneNumber);
//		userRegistarion.setUserAvtarPic(photoBytes);
//		userRegistarion.setDomain(domain);
//		userRegistarion.setGender(gender);
//
//		return userRegistarionRepo.save(userRegistarion);

	}

	@Override
	public List<TimeSheet> findByStartDateAndEndDate(Date sDate, Date eDate, String status) {

		return timeSheetRepo.findByStartDateBeforeAndEndDateAfter(sDate, eDate, status);

	}

	@Override
	public List<TimeSheet> findByStartDateAndEndDateOpt(Date sDate, Date eDate, String status) {

		return timeSheetRepo.findByStartDateBeforeAndEndDateAfterOpt(sDate, eDate, status);

	}

	@Override
	public ResponseEntity<Object> timesheetApprove(int id, String feedback) {
		try {
			long millis = System.currentTimeMillis();
			java.sql.Date current_Date = new java.sql.Date(millis);
			TimeSheet existingTimesheet = timeSheetRepo.findById(id).get();
			existingTimesheet.setStatus("PA");
			existingTimesheet.setFeedback(feedback);
			existingTimesheet.setModifyDate(current_Date);
			timeSheetRepo.saveAndFlush(existingTimesheet);
			return new ResponseEntity<Object>(existingTimesheet, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			String msg = " Please Specify A Valid Id...!";
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<TimeSheet> findByStartDateAndEndDate(Date sDate, Date eDate) {
		return timeSheetRepo.findByStartDateAndEndDate(sDate, eDate);
	}

	@Override
	public ResponseEntity<Object> timesheetRejected(int id, String feedback) {
		try {
			long millis = System.currentTimeMillis();
			java.sql.Date current_Date = new java.sql.Date(millis);
			TimeSheet existingTimesheet = timeSheetRepo.findById(id).get();
			existingTimesheet.setStatus("PR");
			existingTimesheet.setFeedback(feedback);
			existingTimesheet.setModifyDate(current_Date);
			timeSheetRepo.saveAndFlush(existingTimesheet);
			return new ResponseEntity<Object>(existingTimesheet, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			String msg = " Please Specify A Valid Id...!";
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> financialApprove(int id, String feedback) {
		try {
			long millis = System.currentTimeMillis();
			java.sql.Date current_Date = new java.sql.Date(millis);
			TimeSheet existingTimesheet = timeSheetRepo.findById(id).get();
			existingTimesheet.setStatus("FA");
			existingTimesheet.setFeedback(feedback);
			existingTimesheet.setModifyDate(current_Date);
			timeSheetRepo.saveAndFlush(existingTimesheet);
			return new ResponseEntity<Object>(existingTimesheet, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			String msg = " Please Specify A Valid Id...!";
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		}
	}
	@Override
	public ResponseEntity<Object> financialRejected(int id, String feedback) {
		try {
			long millis = System.currentTimeMillis();
			java.sql.Date current_Date = new java.sql.Date(millis);
			TimeSheet existingTimesheet = timeSheetRepo.findById(id).get();
			existingTimesheet.setStatus("FR");
			existingTimesheet.setFeedback(feedback);
			existingTimesheet.setModifyDate(current_Date);
			timeSheetRepo.saveAndFlush(existingTimesheet);
			return new ResponseEntity<Object>(existingTimesheet, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			String msg = " Please Specify A Valid Id...!";
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<TimeSheet> findByStartToEndDateFinacePending(Date sDate, Date eDate, String status) {

		return timeSheetRepo.findByStartDateBeforeAndEndDateAfterOpt(sDate, eDate, status);

	}

}
