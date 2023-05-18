package com.ojas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Task_21_EmbdedWebServerOptimization {
	public static String stringResult = "";
	public static String rootDirectory = "serv";

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
		server.createContext("/"+rootDirectory, new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
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
				if(t.getRequestURI().toString().contains("serverKill")){
					System.exit(0);
				}
				// t.sendResponseHeaders(404, 0);
				//generating links 
				stringResult = "";
				File folder = new File(rootDirectory);
				listFilesForFolder(folder);
				stringResult += "<a href='serverKill'>Kill Server</a>";
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
				stringResult += "<a href='"+rootDirectory+"/"+fileEntry.getName()+"'>"+fileEntry.getName()+"</a>";
				stringResult += "<br/>";
			}
		}
	}

	
}
