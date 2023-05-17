package com.ojas.timesheet;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ojas.timesheet.service.impl.UserRegistarionService;

@SpringBootApplication
public class TimesheetApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private UserRegistarionService userRegistarionService;
	
	public static void main(String[] args) {
		SpringApplication.run(TimesheetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		//userRegistarionService.register();  only once required
	}
}
