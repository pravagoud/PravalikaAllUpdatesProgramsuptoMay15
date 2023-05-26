package com.ojas.Phase2;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class Task_5_DateOfBirthGeneratorCurrentYear {
	

	    public static void main(String[] args) {
	        LocalDate minDate = LocalDate.now().minusYears(100);
	        LocalDate maxDate = LocalDate.now().minusYears(18);
	        for (int i = 0; i < 100; i++) {
	            LocalDate randomDate = getRandomDateOfBirth(minDate, maxDate);
	            String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
//	            System.out.println(randomDate);
//	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
//	            System.out.println(formattedDate);
	            Period age = calculateAge(randomDate);
	            //System.out.println("Random Date of Birth:" + formattedDate +","+ age.getYears()+"years,"+ age.getMonths()+ "months,"+ age.getDays() +"days");
	        }
	    }

	    private static LocalDate getRandomDateOfBirth(LocalDate minDate, LocalDate maxDate) {
	        long minEpochDay = minDate.toEpochDay();
	        long maxEpochDay = maxDate.toEpochDay();
	        long randomEpochDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay);
	        return LocalDate.ofEpochDay(randomEpochDay);
	    }

	    private static Period calculateAge(LocalDate dateOfBirth) {
	        LocalDate currentDate = LocalDate.now();
	        return Period.between(dateOfBirth, currentDate);
	    }
	}

