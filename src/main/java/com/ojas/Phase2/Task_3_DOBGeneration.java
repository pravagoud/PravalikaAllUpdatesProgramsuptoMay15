package com.ojas.Phase2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Task_3_DOBGeneration {
	
	    public static void main(String[] args) {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Select Input File");
	        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

	        int result = fileChooser.showOpenDialog(null);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            String inputFileName = fileChooser.getSelectedFile().getAbsolutePath();
	            String outputFileName = "MenData.csv";

	            try {
	                // Read names from the input file
	                String[] names = readNamesFromFile(inputFileName);

	                // Generate email IDs, phone numbers, DOBs, and add country column
	                String[][] data = generateData(names);

	                // Save the names, email IDs, phone numbers, DOBs, and country to a file
	                saveDataToFile(names, data, outputFileName);

	                System.out.println("Names, emails, phone numbers, DOBs, and country generated and saved successfully.");
	            } catch (IOException e) {
	                System.out.println("Error occurred while generating data: " + e.getMessage());
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

	    public static String[][] generateData(String[] names) {
	        String[][] data = new String[names.length][4];

	        for (int i = 0; i < names.length; i++) {
	            String name = names[i];
	            String[] nameParts = name.split(" ");

	            String firstName = nameParts[0];
	            String lastName = nameParts[nameParts.length - 1];

	            char firstInitial = firstName.charAt(0);
	            char lastInitial = lastName.charAt(0);

	            String email = Character.toLowerCase(firstInitial) + "" + Character.toLowerCase(lastInitial) + "@gmail.com";
	            String phone = generatePhoneNumber();
	            String dob = generateDOB();

	            data[i][0] = email;
	            data[i][1] = phone;
	            data[i][2] = dob;
	            data[i][3] = "Indian";
	        }

	        return data;
	    }

	    private static String generateDOB() {
			// TODO Auto-generated method stub
			return null;
		}

		public static void saveDataToFile(String[] names, String[][] data, String fileName) throws IOException {
	        FileWriter writer = new FileWriter(fileName);

	        // Write column headers
	        writer.write("Name,Email,Phone,DOB,Country" + System.lineSeparator());

	        // Write names, email IDs, phone numbers, DOBs, and country in separate columns
	        for (int i = 0; i < names.length; i++) {
	            writer.write(names[i] + "," + data[i][0] + "," + data[i][1] + "," + data[i][2] + "," + data[i][3] + System.lineSeparator());
	        }

	        writer.close();
	    }

	    public static String generatePhoneNumber() {
	        Random random = new Random();

	        // Generate area code
	        int areaCode = random.nextInt(800) + 200; // Random


}
