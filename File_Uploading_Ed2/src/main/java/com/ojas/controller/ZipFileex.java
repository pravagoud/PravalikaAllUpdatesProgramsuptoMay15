package com.ojas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileex {

	private String path;

	public ZipFileex(String path) {
		super();
		this.path = path;
	}

	public void compress(String... files) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path));
		for (String f : files) {
			File file = new File(f);
			ZipEntry entry = new ZipEntry(file.getName());
			zos.putNextEntry(entry);
			FileInputStream fis = new FileInputStream(file);
			int len;
			byte[] data = new byte[1024];
			while ((len = fis.read(data)) != -1) {
				zos.write(data, 0, len);
			}
			fis.close();
			zos.closeEntry();
		}
		zos.close();
	}

//	public static void main(String[] args) throws IOException {
//		ZipFileex zipF = new ZipFileex("C:\\Users\\nm21439\\Desktop\\ZIPPER\\output.zip");
//		zipF.compress("C:\\Users\\nm21439\\Desktop\\jsp1.png", "C:\\Users\\nm21439\\Desktop\\ojas.png");
//	}
	
}
