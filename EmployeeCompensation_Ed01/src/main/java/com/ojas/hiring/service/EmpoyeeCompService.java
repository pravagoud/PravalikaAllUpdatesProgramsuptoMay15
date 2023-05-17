package com.ojas.hiring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

//import java.util.Date;

import org.springframework.stereotype.Service;


import com.ojas.hiring.entity.EmployeeCompensation;

@Service
public interface EmpoyeeCompService {

	public String saveCompansation(String employeeId,String userName, String plan, String employeeType, String projectCategory,
			String clientName, String projectStatus, String projectStartDate, String projectEndDate, double tenure,
			String department, String billingStatus, String ojasEmailID, double billRatesPM,
			double estimationOfActualBilling, double mCtc, int margin, double yCtc);

List<EmployeeCompensation> getAllEmpComp();
	
	
	
EmployeeCompensation getEmployeeById(int id);

public List<EmployeeCompensation> getByEmailId(String email);
	
	void deleteEmployeeById(int id);

void saveEmployee(EmployeeCompensation employee);

Page<EmployeeCompensation> findpaginated(int pageNo, int pageSize, String sortField, String sortDirection);

public List<Double>  employeeCount(String startDate,String endDate);

public  List<Double> negativeCtcEmpCount(String startDate,String endDate);

public List<Double> negativeCtcByMonth(int month);

public void deleteEmployeeByEmployeeId(String employeeId);

public String projectCategoryBillings(String startDaate,String endDate);
//Billable
//Buffer
//Non-Billable
public String getBillableProjectCategoryBilling(String startDaate,String endDate);
public String getBufferProjectCategoryBilling(String startDaate,String endDate);
public String getNonBillableProjectCategoryBilling(String startDaate,String endDate);


}
