
package com.ojas.timesheet.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.timesheet.entity.Role;
import com.ojas.timesheet.entity.User;
import com.ojas.timesheet.entity.UserRegistarion;
import com.ojas.timesheet.repo.UserRegistarionRepo;
import com.ojas.timesheet.repo.UserRepository;

@Service
public class UserRegistarionService {

	@Autowired
	private UserRegistarionRepo userRegistarionRepo;

	@Autowired
	private UserRepository userRepository;

	public UserRegistarion inserRecords(String name, Integer employeeId, String email, String password,
			double phoneNumber, String domain, String gender, MultipartFile userAvtarPic) throws IOException {

		byte[] photoBytes = userAvtarPic.getBytes();
		User user = new User();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = password;
		String encodedPassword = encoder.encode(rawPassword);
		
	//	System.out.println(encodedPassword);
		
		user.setEmployeeName(name);
		user.setEmployeeId(employeeId);
		user.setUsername(name);
		String extension = "@ojas-it.com";
        if (email != null) {
            String tostring = email.toString();
            int index = tostring.lastIndexOf('@');
            if (email.contains("@")) {
                String emailId = email.substring(0, index);
                user.setEmail(emailId+extension);
                System.out.println(name + extension);
            } else {
            	user.setEmail(email+extension);
                System.out.println(email.concat(extension));
            }
 
        }
		user.setPassword(encodedPassword);
		user.setDomain(domain);
		user.setPhoneNumber(phoneNumber);
		user.setGender(gender);
		user.setUserAvtarPic(photoBytes);
		user.setEnabled(true);

		Set<Role> addRole = new HashSet<>();
		Role addRoleData = new Role();
	//	addRoleData.setId(2);
		addRoleData.setName("USER");
		addRole.add(addRoleData);

		user.setRoles(addRole);

		userRepository.save(user);

		UserRegistarion userRegistarion = new UserRegistarion();
		userRegistarion.setEmpName(name);
		userRegistarion.setEmployeeId(employeeId);
//		userRegistarion.setEmail(email);
        if (email != null) {
            String tostring = email.toString();
            int index = tostring.lastIndexOf('@');
            if (email.contains("@")) {
                String emailId = email.substring(0, index);
                userRegistarion.setEmail(emailId+extension);
                System.out.println(name + extension);
            } else {
            	userRegistarion.setEmail(email+extension);
                System.out.println(email.concat(extension));
            }
 
        }
		userRegistarion.setUserName(name);
		userRegistarion.setPassword(encodedPassword);
		userRegistarion.setPhoneNumber(phoneNumber);
		userRegistarion.setUserAvtarPic(photoBytes);
		userRegistarion.setDomain(domain);
		userRegistarion.setGender(gender);

		return userRegistarionRepo.save(userRegistarion);

	}

	public UserRegistarion adminInserRecords(String name, int employeeId, String email, String password,
			double phoneNumber, String domain, String gender, String role, MultipartFile userAvtarPic) throws IOException {

		byte[] photoBytes = userAvtarPic.getBytes();
		User user = new User();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = password;
		String encodedPassword = encoder.encode(rawPassword);

		System.out.println(encodedPassword);

		user.setEmployeeId(employeeId);
		user.setEmployeeName(name);
		user.setUsername(name);
		user.setEmail(email);
		user.setPassword(encodedPassword);
		user.setDomain(domain);
		user.setPhoneNumber(phoneNumber);
		user.setGender(gender);
		user.setUserAvtarPic(photoBytes);
		user.setEnabled(true);

		Set<Role> addRole = new HashSet<>();
		Role addRoleData = new Role();
	//	addRoleData.setId(2);
		addRoleData.setName(role);
		addRole.add(addRoleData);

		user.setRoles(addRole);

		userRepository.save(user);

		UserRegistarion userRegistarion = new UserRegistarion();
		userRegistarion.setEmpName(name);
		userRegistarion.setEmployeeId(employeeId);
		userRegistarion.setEmail(email);
		userRegistarion.setUserName(name);
		userRegistarion.setPassword(encodedPassword);
		userRegistarion.setPhoneNumber(phoneNumber);
		userRegistarion.setUserAvtarPic(photoBytes);
		userRegistarion.setDomain(domain);
		userRegistarion.setGender(gender);

		return userRegistarionRepo.save(userRegistarion);
		
	}
	
	
	//default login
	public void register() throws IOException {

	//	byte[] photoBytes = userAvtarPic.getBytes();
		User user = new User();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "Ojas@admin";
		String encodedPassword = encoder.encode(rawPassword);

	//	System.out.println(encodedPassword);

		user.setEmployeeId(1111);
		user.setEmployeeName("Admin");
		user.setUsername("Admin");
		user.setEmail("admin@ojas-it.com");
		user.setPassword(encodedPassword);
		user.setDomain("Admin");
		user.setPhoneNumber(9988776655d);
		user.setGender("Male");
	//	user.setUserAvtarPic(photoBytes);
		user.setEnabled(true);

		Set<Role> addRole = new HashSet<>();
		Role addRoleData = new Role();
	//	addRoleData.setId(2);
		addRoleData.setName("ADMIN");
		addRole.add(addRoleData);
		user.setRoles(addRole);
		userRepository.save(user);

		UserRegistarion userRegistarion = new UserRegistarion();
		userRegistarion.setEmpName("Admin");
		userRegistarion.setEmployeeId(1111);
		userRegistarion.setEmail("admin@ojas-it.com");
		userRegistarion.setUserName("Admin");
		userRegistarion.setPassword(encodedPassword);
		userRegistarion.setPhoneNumber(9988776655d);
	//	userRegistarion.setUserAvtarPic(photoBytes);
		userRegistarion.setDomain("Admin");
		userRegistarion.setGender("Male");

		userRegistarionRepo.save(userRegistarion);
		
	}

}
