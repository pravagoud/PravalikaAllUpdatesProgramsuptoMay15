package com.ojas;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Task_8_BigSynthDataSpliterOptimizationOfLowMemory {
	public static void main(String[] args) {

		File newfile = new File("bigdata.txt");
		int s = 100;
		try {
			// splitFile(newfile, s);
			splitFileOptimzed(newfile, 20);
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<File> splitFile(File file, int sizeOfFileInMB) throws IOException {
		int counter = 1;
		List<File> files = new ArrayList<File>();
		int sizeOfChunk = 1024 * 1024 * sizeOfFileInMB;
		String eof = System.lineSeparator();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String name = file.getName();
			String line = br.readLine();
			while (line != null) {
				File newFile = new File(file.getParent(), name + "." + String.format("%03d", counter++));
				try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
					int fileSize = 0;
					while (line != null) {
						byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
						if (fileSize + bytes.length > sizeOfChunk)
							break;
						out.write(bytes);
						fileSize += bytes.length;
						line = br.readLine();
					}
				}
				files.add(newFile);
			}
		}
		return files;
	}

	public static void splitFileOptimzed(File f, long numSplits) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		long sourceSize = raf.length();
		// System.out.println(sourceSize);
		long bytesPerSplit = sourceSize / numSplits;
		// System.out.println(bytesPerSplit); long remainingBytes = sourceSize %
		// numSplits; int maxReadBufferSize = 8 * 1024; // 8KB for (int destIx = 1;
		// destIx <= numSplits; destIx++) { BufferedOutputStream bw = new
		// BufferedOutputStream(new FileOutputStream("FilePart_" + destIx)); if
		// (bytesPerSplit > maxReadBufferSize) { long numReads = bytesPerSplit /
		// maxReadBufferSize; // System.out.println("numreads: "+numReads); long
		// numRemainingRead = bytesPerSplit % maxReadBufferSize; //
		// System.out.println("numRemainingRead: "+numRemainingRead); for (int i = 0; i
		// < numReads; i++) { readWrite(raf, bw, maxReadBufferSize); } if
		// (numRemainingRead > 0) { readWrite(raf, bw, numRemainingRead); } } else {
		// readWrite(raf, bw, bytesPerSplit); } bw.close(); } if (remainingBytes > 0) {
		// BufferedOutputStream bw = new BufferedOutputStream(new
		// FileOutputStream("FilePart_" + (numSplits + 1))); readWrite(raf, bw,
		// remainingBytes); bw.close(); } raf.close(); } static void
		// readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes)
		// throws IOException { byte[] buf = new byte[(int) numBytes]; //
		// System.out.println("bytes variable"+buf); int val = raf.read(buf); //
		// System.out.println("Val variable"+val); if (val != -1) { //
		// System.out.println("Suresh2"); bw.write(buf); bw.flush(); } } }
	}
}