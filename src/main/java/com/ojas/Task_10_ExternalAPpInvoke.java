package com.ojas;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Task_10_ExternalAPpInvoke {
//Chrome method 
	private static void chrome() throws IOException {

		String url = JOptionPane.showInputDialog(null, "Enter the URL to open in Chrome:");

		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", url);
		pb.start();
	}

	public static void openFileSplitterEXEUsingJava() throws IOException {
//		String url = "https://www.filesplitter.org/";
//		java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		try {

			Process p = Runtime.getRuntime().exec("java BatchDemo.jar");
			p.waitFor();
			System.out.println("File Splitter tool launched!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {

		JFileChooser chooser = new JFileChooser();

		int option = chooser.showOpenDialog(null);

		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			String filePath = selectedFile.getAbsolutePath();
			ProcessBuilder pb = new ProcessBuilder("notepad.exe", filePath);
			pb.start();
//chrome();
		}

	}
}
