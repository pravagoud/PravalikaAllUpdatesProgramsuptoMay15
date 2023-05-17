package com.ojas.timesheet.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "emp_name")
	private String employeeName;
	@Column(name = "employeeId")
	private Integer employeeId;
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "isEnabled")
	private boolean enabled;
	@Column(name = "PhoneNumber")
	private Double phoneNumber;
	@Lob
	@Column(name = "UserAvtarPic")
	private byte[] userAvtarPic;
	@Column(name = "Domain")
	private String domain;
	@Column(name = "Gender")
	private String gender;
	

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Double getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Double phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public User(Long id, String employeeName, Integer employeeId, String username, String email, String password,
			boolean enabled, Double phoneNumber, byte[] userAvtarPic, String domain, String gender, Set<Role> roles) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.employeeId = employeeId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.phoneNumber = phoneNumber;
		this.userAvtarPic = userAvtarPic;
		this.domain = domain;
		this.gender = gender;
		this.roles = roles;
	}
	

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", employeeName=" + employeeName + ", employeeId=" + employeeId + ", username="
				+ username + ", email=" + email + ", password=" + password + ", enabled=" + enabled + ", phoneNumber="
				+ phoneNumber + ", userAvtarPic=" + Arrays.toString(userAvtarPic) + ", domain=" + domain + ", gender="
				+ gender + ", roles=" + roles + "]";
	}

	
	
}
