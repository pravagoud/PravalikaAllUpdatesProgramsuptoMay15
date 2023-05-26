package com.ojas.Phase2;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
//genretaing only date of birth
public class Task_3_DateOfBirthGenerator {
    public static void main(String[] args) {
        LocalDate minDate = LocalDate.now().minusYears(100);
        LocalDate maxDate = LocalDate.now().minusYears(18);
        for (int i = 0; i < 100; i++) {
        LocalDate randomDate = getRandomDateOfBirth(minDate, maxDate);
        String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        System.out.println("Random Date of Birth: " + formattedDate);}
    }

    private static LocalDate getRandomDateOfBirth(LocalDate minDate, LocalDate maxDate) {
        long minEpochDay = minDate.toEpochDay();
        long maxEpochDay = maxDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}

