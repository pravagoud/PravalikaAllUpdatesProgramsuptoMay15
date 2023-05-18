package com.ojas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Task_5_SynthDataDynamicGenerator {
	public static List<String> records = new ArrayList<String>();

	public static File newfile = new File("op6.txt");

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
		fw = new FileWriter(newfile);
		Task_5_SynthDataDynamicGenerator stopwatch = new Task_5_SynthDataDynamicGenerator();
		stopwatch.start(); // Do some work...
		String r = JOptionPane.showInputDialog("Enter Number Of Rounds : ");

		int maxLength = Integer.parseInt(r);
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