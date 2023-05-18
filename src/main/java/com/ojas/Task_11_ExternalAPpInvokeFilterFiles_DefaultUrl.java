package com.ojas;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Task_11_ExternalAPpInvokeFilterFiles_DefaultUrl {
	public static void main(String[] args) throws IOException {
		String[] options = { "Notepad", "Chrome" };
		int choice = JOptionPane.showOptionDialog(null, "Select an application to launch:", "Launch Application",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		System.out.println("Choice is : " + choice);

		// notepad
		if (choice == 0) {
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				System.out.println("Pravalika" + selectedFile);
				String filePath = selectedFile.getAbsolutePath();
				String path = "txt";
				if (filePath.length() > 3) {
					String ltr = filePath.substring(filePath.length() - 3);
					System.out.println("length is" + ltr);
					if (ltr.equals(path)) {
						System.out.println(ltr.equals(ltr) + "truefalse");
						ProcessBuilder pb = new ProcessBuilder("notepad.exe", filePath);
						System.out.println("Successfully Generated Pdf..");
						pb.start();
					} else {
						JOptionPane.showMessageDialog(chooser, "Please Provide Valid Text File", ltr, option);
					}
				}
			}
		}
		// chrome
		if (choice == 1) {
			chrome();
		}
	}
	private static void chrome() throws IOException {
		String url = JOptionPane.showInputDialog(null, "Enter the URL to open in Chrome:", "https://www.google.com/");

		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", url);
		pb.start();
	}
}