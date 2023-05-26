package com.ojas.Phase2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

// generating names, emails, phone numbers, and country
public class Task_8_DataOfSalarytest {
	static double salary;

	public static void main(String[] args) {
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setDialogTitle("Select Input File");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String inputFileName = fileChooser.getSelectedFile().getAbsolutePath();
			String outputFileName = "Uptosalary.txt";

			try {
				// Read names from the input file
				String[] names = readNamesFromFile(inputFileName);

				// Generate email IDs, phone numbers, and add country column
				String[][] emailsAndPhones = generateEmailsAndPhones(names);

				// Save the names, email IDs, phone numbers, and country to a file
				saveDataToFile(names, emailsAndPhones, outputFileName);

				System.out.println("Data saved successfully.");
			} catch (IOException e) {
				System.out.println(
						"Error occurred while generating names, emails, phone numbers, and country: " + e.getMessage());
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
		String[][] emailsAndPhones = new String[names.length][8];

		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			String[] nameParts = name.split(" ");

			String firstName = nameParts[0];
			String lastName = nameParts[nameParts.length - 1];

			char firstInitial = firstName.charAt(0);
			char lastInitial = lastName.charAt(0);

			// String email = Character.toLowerCase(firstInitial) + "" +
			// Character.toLowerCase(lastInitial) + "@gmail.com";
			String phone = generatePhoneNumber();
			String email1 = Character.toLowerCase(firstInitial) + "" + Character.toLowerCase(lastInitial)
					+ "@gmail.com";
			String fullNameEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@yahoo.com";
			LocalDate minDate = LocalDate.now().minusYears(100);
			LocalDate maxDate = LocalDate.now().minusYears(18);

			LocalDate randomDate = getRandomDateOfBirth(minDate, maxDate);
			String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
			// System.out.println("Random Date of Birth: " + formattedDate);
			String dob = formattedDate;
			Period age = calculateAge(randomDate);
			String age1 = age.getYears() + " years" + age.getMonths() + " months" + age.getDays() + " days";
			String randomDepartment1 = "";
			String[] departments = { "Marketing", "Finance", "Human Resources", "Sales", "Information Technology",
					"Operations", "Research and Development", "Customer Service", "Product Management",
					"Quality Assurance", "Supply Chain", "Legal", "Administration", "Training and Development",
					"Business Development", "Public Relations", "Accounting", "Purchasing", "Project Management",
					"Strategic Planning", "Logistics", "Facilities Management", "Risk Management", "Compliance",
					"Internal Audit" };
			// Create an instance of Random class
			Random random = new Random();

			// Generate random index to retrieve a random department
			for (int i1 = 1; i1 <= 101; i1++) {
				int randomIndex = random.nextInt(departments.length);

				// Retrieve random department
				randomDepartment1 = departments[randomIndex];

				// Print the random department
				System.out.println("Random Department: " + randomDepartment1);

				double salary = 0.0; // Initialize salary as double

				if (age.getYears() > 60) {
					salary = 0.0; // Retired
				} else if (age.getYears() < 30) {
					salary = random.nextInt(10000) + 30000; // Random salary between 30000 and 40000
					System.out.println(salary);
				} else {
					salary = random.nextInt(10000) + 50000; // Random salary between 50000 and 60000
				}

				String salaryString;
				if (salary == 0.0) {
					salaryString = "0.0";
				} else {
					salaryString = String.format("%.2f", salary); // Format salary as a string with 2 decimal places
				}

				emailsAndPhones[i][0] = email1;
				emailsAndPhones[i][1] = phone;
				emailsAndPhones[i][2] = "Indian";
				emailsAndPhones[i][3] = fullNameEmail;
				emailsAndPhones[i][4] = dob;
				emailsAndPhones[i][5] = age1;

				emailsAndPhones[i][6] = randomDepartment1;

				emailsAndPhones[i][7] = salaryString;

				// System.out.println(emailsAndPhones[i][6]);
			}

			
		}
		return emailsAndPhones;
	}

	public static void saveDataToFile(String[] names, String[][] emailsAndPhones, String fileName) throws IOException {
		FileWriter writer = new FileWriter(fileName);

		// Write column headers
		writer.write("Name,Email,Country,Phone,Email2,Dob,age,departments,salary" + System.lineSeparator());

		// Write names, email IDs, phone numbers, and country in separate columns
		for (int i = 0; i < names.length; i++) {
			writer.write(
					names[i] + "," + emailsAndPhones[i][0] + "," + emailsAndPhones[i][2] + "," + emailsAndPhones[i][1]
							+ "," + emailsAndPhones[i][3] + "," + emailsAndPhones[i][4] + "," + emailsAndPhones[i][5]
							+ "," + emailsAndPhones[i][6] + "," + emailsAndPhones[i][7] + System.lineSeparator());
		}

		writer.close();
	}

	public static String generatePhoneNumber() {
		Random random = new Random();

		// Generate area code
		int areaCode = random.nextInt(90) + 10; // Random number between 10 and 99

		// Generate prefix
		int prefix = random.nextInt(9000) + 1000; // Random number between 1000 and 9999

		// Generate line number
		int lineNumber = random.nextInt(900000) + 100000; // Random number between 100000 and 999999

		// Format phone number
		return String.format("(%02d) %04d-%06d", areaCode, prefix, lineNumber);
	}

	private static LocalDate getRandomDateOfBirth(LocalDate minDate, LocalDate maxDate) {
		long minEpochDay = minDate.toEpochDay();
		// System.out.println(minEpochDay);

		long maxEpochDay = maxDate.toEpochDay();
		// System.out.println(maxEpochDay);
		long randomEpochDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay);
		return LocalDate.ofEpochDay(randomEpochDay);
	}

	private static Period calculateAge(LocalDate dateOfBirth) {
		LocalDate currentDate = LocalDate.now();
		return Period.between(dateOfBirth, currentDate);
	}

	public static void departMent() {

	}

}
