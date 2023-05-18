package com.ojas.Phase2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Task_1_Generate1000RandomPhoneno {
	
	
	    public static void main(String[] args) {
	        try {
	            FileWriter fileWriter = new FileWriter("phonenumbergenerator.txt");
	            PrintWriter printWriter = new PrintWriter(fileWriter);

	            for (int i = 0; i < 1000; i++) {
	                String phoneNumber = generatePhoneNumber();
	                printWriter.println(phoneNumber);
	            }

	            printWriter.close();
	            System.out.println("Phone numbers generated and stored in 'phonenumbergenerator.txt'.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private static String generatePhoneNumber() {
	        StringBuilder phoneNumber = new StringBuilder();
	        phoneNumber.append("91"); // Indian country code

	        // Generate the first digit (6, 7, 8, or 9)
	        int firstDigit = (int) (Math.random() * 4) + 6;
	        phoneNumber.append(firstDigit);

	        // Generate 8 random digits
	        for (int i = 0; i < 8; i++) {
	            int digit = (int) (Math.random() * 10);
	            phoneNumber.append(digit);
	        }

	        return phoneNumber.toString();
	    }
	}


	


