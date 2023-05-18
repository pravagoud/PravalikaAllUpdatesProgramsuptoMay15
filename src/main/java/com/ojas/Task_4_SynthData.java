package com.ojas;

import java.util.Scanner;

public class Task_4_SynthData {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of rounds : ");
		int a = sc.nextInt();

		for (char alphabets = 'a'; alphabets <= 'z'; alphabets++) {
			System.out.println(alphabets);
		}
		for (char c = 'a'; c <= 'z'; c++) {

			for (char j = 'a'; j <= 'z'; j++) {

				for (char i = 'a'; i <= 'z'; i++) {

					System.out.println(c + "" + j + "" + i);

				}
			}
		}

	}
}
