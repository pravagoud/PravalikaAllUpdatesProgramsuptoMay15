package com.ojas.hiring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

//import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.ojas.hiring.entity.EmployeeCompensation;

import com.ojas.hiring.service.EmpoyeeCompServiceImpl;

@RestController
//@Controller
@RequestMapping("/api")
public class Budget_Analysis {
	@Autowired
	private EmpoyeeCompServiceImpl employeeServiceImpl;

	@PostMapping("/save")
	public String saveEmployeeComensation(@RequestParam(value = "employeeId", required = false) String employeeId,
			@RequestParam("userName") String userName,
			@RequestParam("plan") String plan,
			@RequestParam("employeeType") String employeeType,
			@RequestParam("projectCategory") String projectCategory,
			@RequestParam("projectCategory") String clientName,
			@RequestParam("clientName") String projectStatus,
			@RequestParam("projectStartDate") String projectStartDate,
			@RequestParam("projectEndDate") String projectEndDate,
			@RequestParam("tenure") double tenure,
			@RequestParam("department") String department,
			@RequestParam("billingStatus") String billingStatus,
			@RequestParam("ojasEmailID") String ojasEmailID,
			@RequestParam("billRatesPM") double billRatesPM,
			@RequestParam("estimationOfActualBilling") double estimationOfActualBilling,
			@RequestParam("mCtc") double mCtc, @RequestParam("margin") int margin, @RequestParam("yctc") double yctc,
			HttpSession session) {
		System.out.println("-------+----------------------");
		employeeServiceImpl.saveCompansation(employeeId, userName, plan, employeeType, projectCategory, clientName,
				projectStatus, projectStartDate, projectEndDate, tenure, department, billingStatus, ojasEmailID,
				billRatesPM, estimationOfActualBilling, mCtc, margin, yctc);
		session.setAttribute("result",
				employeeServiceImpl.saveCompansation(employeeId, userName, plan, employeeType, projectCategory,
						clientName, projectStatus, projectStartDate, projectEndDate, tenure, department, billingStatus,
						ojasEmailID, billRatesPM, estimationOfActualBilling, mCtc, margin, yctc));

		return "new_employee";

	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@RequestBody EmployeeCompensation employee) {
		System.out.println("==========================");
		employeeServiceImpl.saveEmployee(employee);
		// return "redirect:/api/";
		return "succesfully inserted";

	}

	@GetMapping("/")
	public String viewHomePage(Model model) {
		/*
		 * model.addAttribute("listEmployees", employeeServiceImpl.getAllEmployees());
		 * return "index";
		 */
		return findPaginated(1, "userName", "asc", model);

	}

	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		EmployeeCompensation employee = new EmployeeCompensation();
		model.addAttribute("employee", employee);
		return "new_employee";

	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable int id, Model model) {

		EmployeeCompensation employee = employeeServiceImpl.getEmployeeById(id);
		System.out.println(employee);

		model.addAttribute("employee", employee);

		return "update_employee";

	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") int id) {
		this.employeeServiceImpl.deleteEmployeeById(id);
		return "redirect:/api/";

	}

	@GetMapping("/getByEmail/{email}")
	public EmployeeCompensation getByEmailId(@PathVariable("email") String email) {
		List<EmployeeCompensation> byEmailId = employeeServiceImpl.getByEmailId(email);
		return byEmailId.get(0);
	}

	@GetMapping("/getByEmployeeId/{id}")
	public EmployeeCompensation getByEmployeeId(@PathVariable("id") String id) {
		List<EmployeeCompensation> byEmployeeId = employeeServiceImpl.getByEmployeeId(id);
		return byEmployeeId.get(0);

	}
	
	
	@GetMapping("/getAllEmployeeDetails")
	public List<EmployeeCompensation> getAllEmpComp() {

		return employeeServiceImpl.getAllEmpComp();
	}

	@GetMapping("/getById/{id}")
	public EmployeeCompensation getById(@PathVariable(value = "id") int id) {
		return employeeServiceImpl.getEmployeeById(id);

	}

	@DeleteMapping("/deleteEmployeeByEmployeeId/{employeeId}")
	public String deleteEmployeeByEmployeeId(@PathVariable("employeeId") String employeeId) {
		System.out.println("delete==>" + employeeId);
		employeeServiceImpl.deleteEmployeeByEmployeeId(employeeId);
		return employeeId + " is deleted";

	}

	@PutMapping("/updateEmployee")
	public String updateEmployee(@RequestBody EmployeeCompensation employee) {
		employeeServiceImpl.saveEmployee(employee);
		return "succesFully Updated" + employee.getEmployeeId();

	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 10;
		Page<EmployeeCompensation> page = employeeServiceImpl.findpaginated(pageNo, pageSize, sortField, sortDir);
		List<EmployeeCompensation> listEmployees = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());

		model.addAttribute("totalItems", page.getTotalElements());

		// Sorting
		model.addAttribute("sortField", sortField);
		// Sorting
		model.addAttribute("sortDirection", sortDir);
		// Sorting
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listEmployees", listEmployees);
		return "index";

	}

	@GetMapping("/getAllEmpData/{startDate}/{endDate}")
	public String employeeCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
		List<Double> employeeCount = employeeServiceImpl.employeeCount(startDate, endDate);
//		System.out.println(employeeCount);
//		JSONObject obj = new JSONObject();
//		obj.put("totalRecords", employeeCount.size());
//		obj.put("chartType", "bar");
//		for (int i = 0; i < employeeCount.size(); i++) {
//			obj.put("v" + (i + 1), employeeCount.get(i));
//		}
//
//		String jsonString = obj.toString();

		JSONObject obj = new JSONObject();
		JSONObject data = new JSONObject();
		obj.put("totalRecords", employeeCount.size());
		obj.put("chartType", "bar");
		obj.put("chartTitle", "Some-Title");
		data.put("employeeCount", employeeCount.get(0));
		obj.put("values", data);

		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;

	}

	@GetMapping("/getEmpWithNegativeMargin/{startDate}/{endDate}")
	public String negativeCtcEmpCount(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		List<Double> negativeCtcEmpCount = employeeServiceImpl.negativeCtcEmpCount(startDate, endDate);
//		JSONObject obj = new JSONObject();
//		obj.put("totalRecords", negativeCtcEmpCount.size());
//		obj.put("chartType", "bar");
//		for(int i=0;i<negativeCtcEmpCount.size();i++) {
//			obj.put("v"+(i+1), negativeCtcEmpCount.get(i));
//		}
//		
//		String jsonString = obj.toString();

		JSONObject obj = new JSONObject();

		JSONObject data = new JSONObject();
		obj.put("totalRecords", negativeCtcEmpCount.size());
		obj.put("chartType", "bar");
		obj.put("chartTitle", "Some-Title");
		data.put("employeeCount", negativeCtcEmpCount.get(0));
		obj.put("values", data);

		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;

	}

	@GetMapping("/getEmpWithNegativeMarginByMonth/{month}")
	public String negativeCtcByMonth(@PathVariable("month") int month) {
		System.out.println("--Month--");
		JSONObject obj = new JSONObject();
		List<Double> negativeCtcByMonth = employeeServiceImpl.negativeCtcByMonth(month);

		JSONObject data = new JSONObject();
		obj.put("totalRecords", negativeCtcByMonth.size());
		obj.put("chartType", "bar");
		obj.put("chartTitle", "Some-Title");
		data.put("employeeCount", negativeCtcByMonth.get(0));
		obj.put("values", data);

		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;

	}

	@GetMapping("/empMarginTypesBar/{startDate}/{endDate}")
	public String empMarginTypesBar(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
System.out.println("[----------------llllllllllllllllllllll----------------------]");
		JSONObject obj = new JSONObject();
		List<Double> negativeCtcMargin = employeeServiceImpl.negativeEmpMarginTypes(startDate, endDate);
		List<Double> positiveCtcMargin = employeeServiceImpl.positiveEmpMarginTypes(startDate, endDate);

		JSONObject data = new JSONObject();
		obj.put("totalRecords", negativeCtcMargin.size());
		obj.put("chartType", "bar");
		obj.put("chartTitle", "Some-Title");
		data.put("employeeCountNegative", negativeCtcMargin.get(0));
		data.put("employeeCountPositive", positiveCtcMargin.get(0));
		obj.put("values", data);

		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;

	}

	@GetMapping("/empMarginTypesPie/{startDate}/{endDate}")
	public String empMarginTypesPie(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {

		JSONObject obj = new JSONObject();
		List<Double> negativeCtcMargin = employeeServiceImpl.negativeEmpMarginTypes(startDate, endDate);
		List<Double> positiveCtcMargin = employeeServiceImpl.positiveEmpMarginTypes(startDate, endDate);

		JSONObject data = new JSONObject();
		obj.put("totalRecords", negativeCtcMargin.size());
		obj.put("chartType", "Pie");
		obj.put("chartTitle", "Some-Title");
		data.put("employeeCountNegative", negativeCtcMargin.get(0));
		data.put("employeeCountPositive", positiveCtcMargin.get(0));
		obj.put("values", data);

		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;

	}

	@GetMapping("/allProjectCategoryBillings/{startDate}/{endDate}")
	public String projectCategoryBillings(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		System.out.println("------------billing------------");
		JSONObject obj = new JSONObject();
		String projectCategoryBillings = employeeServiceImpl.projectCategoryBillings(startDate, endDate);
		JSONObject data = new JSONObject();
		obj.put("chartType", "Group_Bar");
		obj.put("chartTitle", "Project Category Billing Between "+startDate+" and "+endDate);
		obj.put("values", projectCategoryBillings);
		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;
	}
	
	@GetMapping("/getBillableProjectCategory/{startDate}/{endDate}")
	public String getBillableProjectCategoryBilling(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		System.out.println("------------billable------------");
		JSONObject obj = new JSONObject();
		String billableProjectCategoryBilling = employeeServiceImpl.getBillableProjectCategoryBilling(startDate, endDate);
		JSONObject data = new JSONObject();
		obj.put("chartType", "Group_Bar");
		obj.put("chartTitle", "Billable Project Category Billing Between "+startDate+" and "+endDate);
		obj.put("values", billableProjectCategoryBilling);
		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;
	}
	
	
	@GetMapping("/getBufferProjectCategory/{startDate}/{endDate}")
	public String getBufferProjectCategoryBilling(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		System.out.println("------------Buffer------------");
		JSONObject obj = new JSONObject();
	String bufferProjectCategoryBilling = employeeServiceImpl.getBufferProjectCategoryBilling(startDate, endDate);
		JSONObject data = new JSONObject();
		obj.put("chartType", "Group_Bar");
		obj.put("chartTitle", "Buffer Project Category Billing Between "+startDate+" and "+endDate);
		obj.put("values", bufferProjectCategoryBilling);
		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;
	}
	
	@GetMapping("/getNonBillableProjectCategory/{startDate}/{endDate}")
	public String getNonBillableProjectCategoryBilling(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		System.out.println("------------non-billable------------");
		JSONObject obj = new JSONObject();
	 String nonBillableProjectCategoryBilling = employeeServiceImpl.getNonBillableProjectCategoryBilling(startDate, endDate);
		JSONObject data = new JSONObject();
		obj.put("chartType", "Group_Bar");
		obj.put("chartTitle", "Non-Billable Project Category Billing Between "+startDate+" and "+endDate);
		obj.put("values", nonBillableProjectCategoryBilling);
		String jsonString = obj.toString();

		System.out.println(jsonString);

		return jsonString;
	}
	
	
	


}
