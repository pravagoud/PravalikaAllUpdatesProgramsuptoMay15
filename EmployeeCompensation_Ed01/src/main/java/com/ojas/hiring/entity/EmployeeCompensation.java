package com.ojas.hiring.entity;

//import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "EmployeeCompensation")
@Table(name = "EmployeeCompensation")
public class EmployeeCompensation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String employeeId;
	private String userName;
	private String plan;
	private String employeeType;
	
	private String projectCategory;
	private String clientName;
	private String projectStatus;
	private String projectStartDate;
	
	private String projectEndDate;
	private double tenure;
	private String department;
	private String billingStatus;
	
	private String ojasEmailID;
	private double billRatesPM;
	private double estimationOfActualBilling;
	
	private double mCtc;
	private int margin;
	private double yctc;
	@Override
	public String toString() {
		return "EmployeeCompensation [id=" + id + ", employeeId=" + employeeId + ", userName=" + userName + ", plan="
				+ plan + ", employeeType=" + employeeType + ", projectCategory=" + projectCategory + ", clientName="
				+ clientName + ", projectStatus=" + projectStatus + ", projectStartDate=" + projectStartDate
				+ ", projectEndDate=" + projectEndDate + ", tenure=" + tenure + ", department=" + department
				+ ", billingStatus=" + billingStatus + ", ojasEmailID=" + ojasEmailID + ", billRatesPM=" + billRatesPM
				+ ", estimationOfActualBilling=" + estimationOfActualBilling + ", mCtc=" + mCtc + ", margin=" + margin
				+ ", yctc=" + yctc + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getProjectCategory() {
		return projectCategory;
	}
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public String getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public double getTenure() {
		return tenure;
	}
	public void setTenure(double tenure) {
		this.tenure = tenure;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getOjasEmailID() {
		return ojasEmailID;
	}
	public void setOjasEmailID(String ojasEmailID) {
		this.ojasEmailID = ojasEmailID;
	}
	public double getBillRatesPM() {
		return billRatesPM;
	}
	public void setBillRatesPM(double billRatesPM) {
		this.billRatesPM = billRatesPM;
	}
	public double getEstimationOfActualBilling() {
		return estimationOfActualBilling;
	}
	public void setEstimationOfActualBilling(double estimationOfActualBilling) {
		this.estimationOfActualBilling = estimationOfActualBilling;
	}
	public double getmCtc() {
		return mCtc;
	}
	public void setmCtc(double mCtc) {
		this.mCtc = mCtc;
	}
	public int getMargin() {
		return margin;
	}
	public void setMargin(int margin) {
		this.margin = margin;
	}
	public double getYctc() {
		return yctc;
	}
	public void setYctc(double yctc) {
		this.yctc = yctc;
	}
	public EmployeeCompensation(String employeeId, String userName, String plan, String employeeType,
			String projectCategory, String clientName, String projectStatus, String projectStartDate,
			String projectEndDate, double tenure, String department, String billingStatus, String ojasEmailID,
			double billRatesPM, double estimationOfActualBilling, double mCtc, int margin, double yctc) {
		super();
		this.employeeId = employeeId;
		this.userName = userName;
		this.plan = plan;
		this.employeeType = employeeType;
		this.projectCategory = projectCategory;
		this.clientName = clientName;
		this.projectStatus = projectStatus;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.tenure = tenure;
		this.department = department;
		this.billingStatus = billingStatus;
		this.ojasEmailID = ojasEmailID;
		this.billRatesPM = billRatesPM;
		this.estimationOfActualBilling = estimationOfActualBilling;
		this.mCtc = mCtc;
		this.margin = margin;
		this.yctc = yctc;
	}
	public EmployeeCompensation() {
	}
	
	
	
	

}
