package com.ojas;

import java.io.File;

public class Task_22_GenerateLinksForLocalDirectory {
	
	public static String stringResult = "";
	
	public static void listFilesForFolder(final File folder) {
		
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				stringResult += fileEntry.getName();
				stringResult += "\t";
			}
		}
	}

	public static void main(String[] args) {
		stringResult = "";
		File folder = new File("serv");
		listFilesForFolder(folder);
		System.out.println(stringResult);
	}
}
