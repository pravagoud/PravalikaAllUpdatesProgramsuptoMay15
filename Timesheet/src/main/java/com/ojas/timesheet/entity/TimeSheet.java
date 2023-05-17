package com.ojas.timesheet.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

//@Author AkshayaKumar.Mahanty
@Entity
@Table(name = "timesheet")
public class TimeSheet {

	private int id;
	private String empName;
	private int empId;
	private String clientName;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private Date modifyDate;
	private String phoneNo;
	private String domain;
	private String email;
	private String status;
	private String shift;
	private String timing;
	private String feedback;
	private String location;
	private String latitude;
	private String longitude;
	private String ipAddress;
	
	private String locDetails;
	
	@Transient
	private String ttid;
	@Transient
	private String ttfn;
	

	public TimeSheet() {
		super();
	}

	@Override
	public String toString() {
		return "TimeSheet [id=" + id + ", empName=" + empName + ", empId=" + empId + ", clientName=" + clientName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + ", phoneNo=" + phoneNo + ", domain=" + domain + ", email=" + email + ", status=" + status
				+ ", shift=" + shift + ", timing=" + timing + "]";
	}

	public TimeSheet(int id, String empName, int empId, String clientName, Date startDate, Date endDate,
			Date createDate, Date modifyDate, String phoneNo, String domain, String email, String status, String shift,
			String timing) {
		super();
		this.id = id;
		this.empName = empName;
		this.empId = empId;
		this.clientName = clientName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.phoneNo = phoneNo;
		this.domain = domain;
		this.email = email;
		this.status = status;
		this.shift = shift;
		this.timing = timing;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "EMP_NAME")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "EMP_ID")
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Column(name = "CLIENT_NAME")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "DOMAIN")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFY_DATE")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "SHIFT")
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	@Column(name = "TIMING")
	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}
	
	public String getTtfn() {
		return ttfn;
	}

	public void setTtfn(String ttfn) {
		this.ttfn = ttfn;
	}

	public String getTtid() {
		return ttid;
	}

	public void setTtid(String ttid) {
		this.ttid = ttid;
	}
	@Column(name = "FEEDBACK")
	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "LATITUDE")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "LONGITUDE")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "IPADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "LOCDETAILS",columnDefinition = "MEDIUMTEXT")
	public String getLocDetails() {
		return locDetails;
	}

	public void setLocDetails(String locDetails) {
		this.locDetails = locDetails;
	}
	
	
	

}
