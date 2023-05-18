package com.ojas;

import java.io.File;
import java.io.IOException;

public class Task_14_Alpha_And_Numeros_Rounds_Usage {
	public static void main(String[] args) {
		// Creating a File object for the directory
		File directoryPath = new File("SynthDat_R");

		// List of all files and directories
		String contents[] = directoryPath.list();
		System.out.println("List of files and directories in the specified directory:");

		// Initialize variables to track the first and second largest files
		long firstMaxAlphabetFileSize = 0;
		String firstLargestAlphabetFileName = null;

		long secondMaxNumberFileSize = 0;
		String secondLargestNumberFileName = null;

		for (int i = 0; i < contents.length; i++) {
			// Print the name of each file
			System.out.println(contents[i]);

			// Create a File object for the current file
			File file = new File(directoryPath.getPath() + File.separator + contents[i]);

			// Check if it is a file and compare its size with the current first and second
			// maximum
			if (file.isFile()) {
				long fileSize = file.length();
				if (fileSize > firstMaxAlphabetFileSize) {
					// Update the first largest file
					firstMaxAlphabetFileSize = fileSize;
					firstLargestAlphabetFileName = file.getName();
				}
			}
		}

		for (int i = 0; i < contents.length; i++) {
			// Print the name of each file
			System.out.println(contents[i]);

			// Create a File object for the current file
			File file = new File(directoryPath.getPath() + File.separator + contents[i]);

			// Check if it is a file and compare its size with the current first and second
			// maximum
			if (file.isFile()) {
				long fileSize = file.length();
				if (firstLargestAlphabetFileName != null && !file.getAbsolutePath().contains("AlphabetOutput_")) {
					if (fileSize > secondMaxNumberFileSize) {
						// Update the second largest file
						secondMaxNumberFileSize = fileSize;
						secondLargestNumberFileName = file.getName();
					}
				}
			}
		}

		// Print the first largest file name and size
		if (firstLargestAlphabetFileName != null) {
			System.out.println("First largest file: " + firstLargestAlphabetFileName);
			System.out.println("File size: " + firstMaxAlphabetFileSize + " bytes");
		} else {
			System.out.println("No files found in the specified directory.");
		}

		// Print the second largest file name and size
		if (secondLargestNumberFileName != null) {
			System.out.println("Second largest file: " + secondLargestNumberFileName);
			System.out.println("File size: " + secondMaxNumberFileSize + " bytes");
		} else {
			System.out.println("No second largest file found in the specified directory.");
		}
	}
}
