package com.ojas;

import java.util.Scanner;

public class Task_6_BigSynthData {

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

	public static void main(String[] args) {
		Task_6_BigSynthData stopwatch = new Task_6_BigSynthData();
		stopwatch.start(); // Do some work... 
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of rounds : ");
		int a = sc.nextInt();

		for (char alphabets = 'a'; alphabets <= 'z'; alphabets++) {
			System.out.println(alphabets);
		}
		for (char c = 'a'; c <= 'z'; c++) {

			for (char j = 'a'; j <= 'z'; j++) {

				for (char i = 'a'; i <= 'z'; i++) {
					for (char k = 'a'; k <= 'z'; k++) {

						System.out.println(c + "" + j + "" + i + "" + k);

					}
				}
			}
		}
		stopwatch.stop(); 
		long elapsedTime = stopwatch.getElapsedTime();
		long seconds=elapsedTime/1000;
		System.out.println("Elapsed time: " + seconds+ " seconds");
	}
	

}
