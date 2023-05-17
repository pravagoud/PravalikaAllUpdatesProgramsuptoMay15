package com.ojas.hiring;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "123";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println("password is : "+encodedPassword);
		
		String enPassword = "$2a$10$r1THhw7oC7bE6IwyHNFA1.gjKPGp.OWQtC5xmyjvP4OlIb.mZGPFe";
		
		boolean isPasswordMatch = encoder.matches(rawPassword, enPassword);
		System.out.println("Password : " + rawPassword + "   isPasswordMatch    : " + isPasswordMatch);
		
	}

}
