package com.ojas.Phase2;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileLineByLine {

	public static void main(String[] args) {

		String outputFileName = "SqlScript.txt";
		List<String[]> lmines = new ArrayList<>();
		List<String> sqlStatements = new ArrayList<>();
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setDialogTitle("Select Input File");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getPath();

			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				// List<String[]> lines = new ArrayList<>();

				String line;
				while ((line = br.readLine()) != null) {

					String[] lineArray = line.split(",");
					// System.out.println(lineArray[5]);

					String sql = "INSERT INTO synthdata.data_model (id,Name, Email, Country, Phone, Email2, Dob) VALUES (0,'"
							+ lineArray[0] + "', '" + lineArray[1] + "', '" + lineArray[2] + "', '" + lineArray[3]
							+ "', '" + lineArray[4] + "', '" + lineArray[5] + "');";
					System.out.println(sql);

				}

				// Printing the lines (for demonstration purposes)
//				for (String[] lineArray : lines) {
//					for (String item : lineArray) {
//						System.out.println(item + " ");
//					}
//					System.out.println();
//				}
				// System.out.println(lines.toString());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
