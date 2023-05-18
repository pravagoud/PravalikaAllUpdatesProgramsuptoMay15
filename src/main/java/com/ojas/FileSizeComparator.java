package com.ojas;

import java.util.Comparator;

import java.io.File;
import java.util.Comparator;

public class FileSizeComparator implements Comparator<File> {

	@Override
	public int compare(File file1, File file2) {
		long file1Length = file1.length();
		long file2Length = file2.length();

		if (file1Length > file2Length)
			return 1;
		else if (file1Length < file2Length)
			return -1;
		return 0;
	}

}