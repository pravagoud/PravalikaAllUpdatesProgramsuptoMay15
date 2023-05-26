package com.ojas.Phase2;

import java.util.Random;

public class Task_7_DataOfDepartmentsTest {
	public static void departMent() {
		String[] departments = {
			    "Marketing",
			    "Finance",
			    "Human Resources",
			    "Sales",
			    "Information Technology",
			    "Operations",
			    "Research and Development",
			    "Customer Service",
			    "Product Management",
			    "Quality Assurance",
			    "Supply Chain",
			    "Legal",
			    "Administration",
			    "Training and Development",
			    "Business Development",
			    "Public Relations",
			    "Accounting",
			    "Purchasing",
			    "Project Management",
			    "Strategic Planning",
			    "Logistics",
			    "Facilities Management",
			    "Risk Management",
			    "Compliance",
			    "Internal Audit"
			};
		 // Create an instance of Random class
        Random random = new Random();

        // Generate random index to retrieve a random department
        for(int i=1;i<=101;i++) {
        int randomIndex = random.nextInt(departments.length);

        // Retrieve random department
        String randomDepartment = departments[randomIndex];

        // Print the random department
        System.out.println("Random Department: " + randomDepartment);
        }

	}
	public static void main(String[] args) {
		departMent();
		
	}

}
