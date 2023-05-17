package com.ojas.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class UserRegistarion {

	@Id
	@Column(name = "employeeId")
	private int employeeId;

	@Column(name = "emp_Name")
	private String empName;

	@Column(name = "email")
	private String email;

	@Column(name = "userName")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "phoneNumber")
	private double phoneNumber;

	@Column(name = "gender")
	private String gender;

	@Lob
	@Column(name = "userAvtarPic")
	private byte[] userAvtarPic;

	@Column(name = "domain")
	private String domain;

	public UserRegistarion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRegistarion(int employeeId, String empName, String email, String userName, String password,
			double phoneNumber, String gender, byte[] userAvtarPic, String domain) {
		super();
		this.employeeId = employeeId;
		this.empName = empName;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.userAvtarPic = userAvtarPic;
		this.domain = domain;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(double phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getUserAvtarPic() {
		return userAvtarPic;
	}

	public void setUserAvtarPic(byte[] userAvtarPic) {
		this.userAvtarPic = userAvtarPic;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	
	
}
