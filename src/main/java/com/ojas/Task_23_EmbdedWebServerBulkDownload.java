package com.ojas;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Task_23_EmbdedWebServerBulkDownload {
	public static String stringResult = "";
	public static String rootDirectory = "serv";
	static String resources = "resources";
	public static List<String> records = new ArrayList<String>();

	public static File newfile = null;

	public static FileWriter fw = null;

	public static void main(String[] args) throws Exception {
		
		int input = JOptionPane.showConfirmDialog(null, "Would you like to Create big Data");
        // 0=yes, 1=no, 2=cancel
        if(input==0) {
        	try {
        		cleanRootDirectory();
        		copyResources();
        		
    			// 1
    			generateBigData();

    			// open file splitter
    			openFileSpliter();

    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
		

		
		
		HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
		System.out.println("Server started at 9999 port...");
		server.createContext("/" + rootDirectory, new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		
		URL url=new URL("http://localhost:9999/"+rootDirectory);
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(url.toURI());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openFileSpliter() throws IOException {
		Runtime.getRuntime().exec("resources\\Free-File-Splitter-v5.0.1189.exe");
	}

	private static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!sourceFile.exists()) {
			return;
		}
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}

	}

	private static void copyResources() throws IOException {

		File index = new File(resources);
		File[] entries = index.listFiles();
		for (File currentFile : entries) {
			System.out.println(currentFile.getName());
			File destination = new File(rootDirectory, currentFile.getName());
			System.out.println("source file" + currentFile.getAbsolutePath());
			System.out.println("Destination File " + destination.getAbsolutePath());
			copyFile(currentFile, destination);

		}
		

	}

//Cleaning serv Directory with own Data
	private static void cleanRootDirectory() {
		File index = new File(rootDirectory);
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			System.out.println(currentFile.getName());
			currentFile.delete();
		}
		System.out.println("Successfully Deleted ");
	}

	static class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println(t.getRequestURI());
			URI uri = t.getRequestURI();
			String name = new File(uri.getPath()).getName();
			File path = new File(rootDirectory, name);

			// t.sendResponseHeaders(200, response.length());
			Headers h = t.getResponseHeaders();
			// Could be more clever about the content type based on the filename here.
			h.add("Content-Type", "text/html");

			OutputStream out = t.getResponseBody();

			if (path.exists()) {
				t.getResponseHeaders().add("Content-Disposition", "attachment; filename=" + path.getName());
				t.sendResponseHeaders(200, path.length());
				out.write(Files.readAllBytes(path.toPath()));
			} else {
				System.err.println("File not found: " + path.getAbsolutePath());
				if (t.getRequestURI().toString().contains("serverKill")) {
					System.exit(0);
				}//generating zip file
				if (t.getRequestURI().toString().contains("bulkDownload")) {
					System.out.println("Initiating Zipping...");
					File zipSource=new File(rootDirectory);
					File zipDestination=new File(rootDirectory,"bulk.zip");
					pack(zipSource.getAbsolutePath(),zipDestination.getAbsolutePath() );
					t.getResponseHeaders().add("Content-Disposition", "attachment; filename=" + zipDestination.getName());
					t.sendResponseHeaders(200, zipDestination.length());
					out.write(Files.readAllBytes(zipDestination.toPath()));
					zipDestination.delete();
				}				
				// t.sendResponseHeaders(404, 0);
				// generating links
				stringResult = "";
				File folder = new File(rootDirectory);
				listFilesForFolder(folder);
				stringResult += "<a href='" + rootDirectory + "/bulkDownload'>Bulk Download</a><br/>";
				stringResult += "<a href='serverKill'>Kill Server</a><br/>";
				t.sendResponseHeaders(200, stringResult.length());
				out.write(stringResult.getBytes());
			}

			out.close();
		}
	}

	public static void listFilesForFolder(final File folder) {

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				stringResult +="<meta http-equiv=\"refresh\" content=\"10\">\n";
				stringResult += "<a href='" + rootDirectory + "/" + fileEntry.getName() + "'>" + fileEntry.getName()
						+ "</a>";
				stringResult += "<br/>";
			}
		}
	}

	private static void generateBigData() throws IOException {
		String fn = JOptionPane.showInputDialog("Enter Big Data File Name : ", "bigdata.txt");

		newfile = new File(fn);
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
	
	public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
	    Path p = Files.createFile(Paths.get(zipFilePath));
	    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
	        Path pp = Paths.get(sourceDirPath);
	        Files.walk(pp)
	          .filter(path -> !Files.isDirectory(path))
	          .forEach(path -> {
	              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
	              try {
	                  zs.putNextEntry(zipEntry);
	                  Files.copy(path, zs);
	                  zs.closeEntry();
	            } catch (IOException e) {
	                System.err.println(e);
	            }
	          });
	    }
	}
	
	
	

}
