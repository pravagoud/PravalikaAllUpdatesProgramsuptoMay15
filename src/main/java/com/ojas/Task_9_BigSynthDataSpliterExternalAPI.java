package com.ojas;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Task_9_BigSynthDataSpliterExternalAPI {

	public static void main(String[] args) throws IOException {
		openNotepad();
		// openChrome();
		 openFileSpliter();
		// openTextFile();
	}
	public static void openNotepad() throws IOException {
		// Runtime.getRuntime().exec("notepad.exe" + "D:\\prava.txt" );

		File file = new File("D:\\springbootworkspace1\\Microservice\\SynthData-R_Project\\op.txt");
		if (!Desktop.isDesktopSupported()) {
			System.out.println("not supported");
			return;
		}
		Desktop desktop = Desktop.getDesktop();
		if (file.exists())
			desktop.open(file);
	}

	public static void openTextFile() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("notepad.exe ", "D:/prava.txt");
	}

	public static void openChrome() throws IOException {
		Runtime.getRuntime().exec("\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\""
				+ "http://192.168.7.48/Bonobo.Git.Server/proj203.git");
	}
//	  String url="http://192.168.7.48/Bonobo.Git.Server/proj203.git";
//	  java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); }

	public static void openFileSpliter() throws IOException {
		Runtime.getRuntime()
				.exec("D:\\springbootworkspace1\\Microservice\\SynthData-R_Project\\Free-File-Splitter-v5.0.1189.exe");
	}

}
