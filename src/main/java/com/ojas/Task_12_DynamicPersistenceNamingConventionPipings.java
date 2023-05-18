package com.ojas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class Task_12_DynamicPersistenceNamingConventionPipings {

	public static List<String> records = new ArrayList<String>();

	private static String maxLength;

	public static File newfile = null;

	public static FileWriter fw = null;

	private long startTime;
	private long stopTime;
	private boolean running;

	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}

	public long getElapsedTime() {
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		} else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

	public static void printSequence(String prefix, int length) throws IOException {

		if (length == 0) {
			// System.out.println(prefix);
			fw.write(prefix + "\n");
			// records.add(prefix);
		} else {

			for (char c = 'a'; c <= 'z'; c++) {
				printSequence(prefix + c, length - 1);
			}
		}

	}

	public static void main(String[] args) throws IOException {

		Task_5_SynthDataDynamicGenerator stopwatch = new Task_5_SynthDataDynamicGenerator();
		stopwatch.start(); // Do some work...
		String r = JOptionPane.showInputDialog("Enter Number Of Rounds : ");
		int maxLength = Integer.parseInt(r);

		// creating new folder 2nd task
		String path = "SynthDat_R";
		File folder = new File(path);
		folder.mkdir();

		// createting year 3rd task
		Date d = new Date();
		int year = d.getYear();
		int currentYear = year + 1900;
		int month = d.getMonth()+1;
		System.out.println("the year is" + currentYear);
		System.out.println("The month Of this year is " + month);
		System.out.println("The Day Of this Month is " + d.getDate());
		System.out.println("The Hours Of this Day is " + d.getHours());
		System.out.println("The Minutes Of this Hour is " + d.getMinutes());
		System.out.println("The Seconds Of this Minutes is " + d.getSeconds());
		

		// creating dynamic filename 1st task
		System.out.println("The files Folder is " + path);
		newfile = new File(folder, "fileoutput_" + maxLength +" " +  currentYear + "_" + month + "_"
				+ d.getDate() + "_" + d.getHours() + "_" + d.getMinutes()+ "_" +d.getSeconds()+ ".txt" );

		fw = new FileWriter(newfile);
		for (int length = 1; length <= maxLength; length++) {
			printSequence("", length);
		}

		stopwatch.stop();
		long elapsedTime = stopwatch.getElapsedTime();
		long seconds = elapsedTime / 1000;
		System.out.println("Elapsed time: " + seconds + " seconds");

		fw.close();
	}
}
