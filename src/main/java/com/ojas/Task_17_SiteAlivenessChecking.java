package com.ojas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task_17_SiteAlivenessChecking {

	public static File newfile = new File("synth.txt");
	public static FileWriter fw = null;

	private static void checkAliveness(String s) throws IOException {
		URL url = new URL(s);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();

		huc.setRequestMethod("GET");
		huc.setRequestProperty("site", s);

		int responseCode = huc.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {// success
			BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {

			System.out.println("GET request is not authorized");
		}
	}

	public static void openUrl(String s) throws IOException {
		URL oracle = new URL(s);
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
//        outputFolder = new File("output");
//        outputFolder.mkdir();
//        File outputFile = new File(outputFolder, "Output.txt");
//        outputFileWriter = new FileWriter(outputFile);

		in.close();
	}

	public static void main(String[] args) throws IOException {

		String s = "https://ojasit.darwinbox.com";
		checkAliveness(s);
		openUrl(s);

	}
}
