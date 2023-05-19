package com.ojas.Phase2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DataOfbirthgebration {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String inputFileName = fileChooser.getSelectedFile().getAbsolutePath();
            String outputFileName = "menData.csv";

            try {
                // Read names from the input file
                String[] names = readNamesFromFile(inputFileName);

                // Generate email IDs, phone numbers, and add country column
                String[][] emailsAndPhones = generateEmailsAndPhones(names);

                // Save the names, email IDs, phone numbers, and country to a file
                saveDataToFile(names, emailsAndPhones, outputFileName);

                System.out.println("Data saved successfully.");
            } catch (IOException e) {
                System.out.println("Error occurred while generating names, emails, phone numbers, and country: " + e.getMessage());
            }
        }
    }

    public static String[] readNamesFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
        }

        bufferedReader.close();

        String namesString = stringBuilder.toString().trim();
        String[] names = namesString.split(System.lineSeparator());

        return names;
    }

    public static String[][] generateEmailsAndPhones(String[] names) {
        String[][] emailsAndPhones = new String[names.length][5];

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String[] nameParts = name.split(" ");

            String firstName = nameParts[0];
            String lastName = nameParts[nameParts.length - 1];

            char firstInitial = firstName.charAt(0);
            char lastInitial = lastName.charAt(0);

            //String email = Character.toLowerCase(firstInitial) + "" + Character.toLowerCase(lastInitial) + "@gmail.com";
            String phone = generatePhoneNumber();
            String email1 = Character.toLowerCase(firstInitial) + "" + Character.toLowerCase(lastInitial) + "@gmail.com";
            String fullNameEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@yahoo.com";
            LocalDate minDate = LocalDate.now().minusYears(100);
            LocalDate maxDate = LocalDate.now().minusYears(18);

            LocalDate randomDate = getRandomDateOfBirth(minDate, maxDate);
            String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            //System.out.println("Random Date of Birth: " + formattedDate);
            String dob = formattedDate;

            emailsAndPhones[i][0] = email1;
            emailsAndPhones[i][1] = phone;
            emailsAndPhones[i][2] = "America";
            emailsAndPhones[i][3] = fullNameEmail;
            emailsAndPhones[i][4] = dob;

        }

        return emailsAndPhones;
    }

    public static void saveDataToFile(String[] names, String[][] emailsAndPhones, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        // Write column headers
        writer.write("Name,Email,Country,Phone,Email2,Dob" + System.lineSeparator());

        // Write names, email IDs, phone numbers, and country in separate columns
        for (int i = 0; i < names.length; i++) {
            writer.write(names[i] + "," + emailsAndPhones[i][0] + "," + emailsAndPhones[i][2]+ "," +  emailsAndPhones[i][1]+","+emailsAndPhones[i][3]+","+emailsAndPhones[i][4] + System.lineSeparator());
        }

        writer.close();
    }

    public static String generatePhoneNumber() {
        Random random = new Random();

        // Generate area code
        int areaCode = random.nextInt(800) + 200; // Random number between 200 and 999

        // Generate prefix
        int prefix = random.nextInt(800) + 200; // Random number between 200 and 999

        // Generate line number
        int lineNumber = random.nextInt(9000) + 1000; // Random number between 1000 and 9999

        // Format phone number
        return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNumber);
    }
    private static LocalDate getRandomDateOfBirth(LocalDate minDate, LocalDate maxDate) {
        long minEpochDay = minDate.toEpochDay();
        //System.out.println(minEpochDay);

        long maxEpochDay = maxDate.toEpochDay();
       //System.out.println(maxEpochDay);
        long randomEpochDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}

