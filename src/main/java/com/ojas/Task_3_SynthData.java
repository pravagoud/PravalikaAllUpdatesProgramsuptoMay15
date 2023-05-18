package com.ojas;

import java.util.Scanner;

public class Task_3_SynthData {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of rounds : ");
		int a = sc.nextInt();
		if(a==2) {
			for (char alphabets = 'a'; alphabets <= 'z'; alphabets++) {
				  System.out.println(alphabets); }
			for (char c = 'a'; c <= 'z'; c++) {

				for (char j = 'a'; j <= 'z'; j++) {
					System.out.println(c + "" + j);

				}
			}
		}
		else{
		System.out.println("please enter 2 ");
	}
	}
}

