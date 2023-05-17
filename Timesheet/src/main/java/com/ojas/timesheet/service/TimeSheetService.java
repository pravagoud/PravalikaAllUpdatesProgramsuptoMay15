package com.ojas.timesheet.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.timesheet.entity.TimeSheet;

public interface TimeSheetService {

	public TimeSheet addTimeSheetDetails(TimeSheet timeSheet);

	public void inserRecords(String empName, int empId, String email, String clientName, Date startDate, Date endDate,
			String phoneNo, String domain) throws IOException;

	public List<TimeSheet> findByStartDateAndEndDate(Date sDate, Date eDate);

	public List<TimeSheet> findByStartDateAndEndDate(Date sDate, Date eDate, String status);

	public ResponseEntity<Object> timesheetApprove(int id, String feedback);

	public List<TimeSheet> findByStartDateAndEndDateOpt(Date sDate, Date eDate, String status);

	public ResponseEntity<Object> timesheetRejected(int id, String feedback);

	public ResponseEntity<Object> financialApprove(int id, String feedback);

	public ResponseEntity<Object> financialRejected(int id, String feedback);

	public List<TimeSheet> findByStartToEndDateFinacePending(Date sDate, Date eDate, String status);

}
