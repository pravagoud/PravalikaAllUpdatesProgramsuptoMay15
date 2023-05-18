package com.ojas;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class App {

	private static void printFiles(File[] files) {
		for (File file : files) {

			System.out.println(file.length() + "\t" + file.getName());
		}
		System.out.println("\n************************\n");
	}

	public static File[] sortByLengthApproach1(String directoryPath) {
		File directory = new File(directoryPath);

		File[] files = directory.listFiles();

		Arrays.sort(files, new FileSizeComparator());
		return files;
	}

	public static File[] sortByLengthApproach2(String directoryPath) {
		File directory = new File(directoryPath);

		File[] files = directory.listFiles();
		Arrays.sort(files, Comparator.comparingLong(File::length));
		return files;
	}

	public static void main(String[] args) {
		String directoryPath = "SynthDat_R";

		File[] files1 = sortByLengthApproach1(directoryPath);
		// File[] files2 = sortByLengthApproach2(directoryPath);

		printFiles(files1);
		// printFiles(files2);

		String alphaFile = null;
		String numFile = null;

		// chandra optimization for rounds greater than 3
		for (int i = files1.length - 1; i > 0; i--) {
			//System.out.println(files1[i]);
			if (files1[i].getAbsolutePath().contains("AlphabetOutput_")) {
				if (alphaFile == null) {
					alphaFile = files1[i].getAbsolutePath();
				}
			}
			if (files1[i].getAbsolutePath().contains("NumericOutput_")) {
				numFile = files1[i].getAbsolutePath();
			}
			if (alphaFile != null && numFile != null) {
				break;
			}
		}

		System.out.println(alphaFile);
		System.out.println(numFile);
		

	}
}
