package com.ojas.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.entity.FileUploadEntity;
import com.ojas.repository.FileRepository;

@Service
public class FileUploadService {
	@Autowired
	private FileRepository fileRepository;
	@Value("${user.storage}")
	private String dir;
	@Value("${user.mp4storagelimit}")
	private double mp4StorageLimit;
	@Value("${user.normalstoragelimit}")
	private double normalStorageLimit;

//	public void uploadFile(MultipartFile file) throws IllegalStateException, IOException {
//		String dir = "D:\\docs\\";
//	
//		File f = new File(dir,file.getOriginalFilename());
//		file.transferTo(f);
//		System.out.println(f.getAbsolutePath());
//	}

	@SuppressWarnings("deprecation")
	public String uploadToDB(String name, String message, MultipartFile file, String uploader)
			throws IllegalStateException, IOException, ParseException {
		System.out.println(dir);
		System.out.println(mp4StorageLimit);
		System.out.println(normalStorageLimit);
		File f = new File(dir, file.getOriginalFilename());// declaring the fully path
		long bytes = file.getSize();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);

		String mimeType = file.getContentType();

		/*
		 * if user not given uploader name it will take by default name as "guest" and
		 * uploaded to root path
		 * 
		 * if user given uploadername it will create a new folder with uploadername and
		 * upload to that folder
		 * 
		 */

		if (!uploader.equals("guest")) {
			File f2 = new File(dir, uploader);// creating a new folder with uploader name if uploader not exists
			if (f2.exists()) {
				f = new File(f2, file.getOriginalFilename());// if already that name folder exist it will not create a
																// new folder just upload to that folder
			} else {
				f2.mkdir();// creating new directory/folder
				f = new File(f2, file.getOriginalFilename());// f is the complete path
			}
		}

		/*
		 * if mime type ends with mp4 it will allow declared size normal files also
		 * allow the declared size
		 */

		if (mimeType.endsWith("mp4")) {
			if (kilobytes > mp4StorageLimit) {
				return "File size exceeded";
			}
		} else {
			if (kilobytes > normalStorageLimit) {
				return "File size exceeded";
			}
		}

		System.out.println("--------" + kilobytes);

		/*
		 * to avoid the dublicate files if the file already exist with same name then we
		 * are adding the time with milliseconds to that file
		 */

		String fname = f.getName();
		if (f.exists()) {
			java.util.Date d = new java.util.Date();
			String s = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss_").format(d).replaceAll("-", "_").replaceAll(":", "_");
			File f2 = new File(dir, s + file.getOriginalFilename());
			f.renameTo(f2);
			fname = f2.getName();
		}

		file.transferTo(f);
//			long ms=System.currentTimeMillis();
//			Date date=new Date(ms);

		// to store the date and time in mysql
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());	
		
		
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.MINUTE, 10);
		 java.util.Date expiryTime = (cal.getTime());
		
		
		
		
		FileUploadEntity entity = new FileUploadEntity(fname, message, f.getAbsolutePath(), kilobytes, mimeType,
				uploader, date, expiryTime);
		fileRepository.save(entity);
		return "File uploaded succesfully";

	}
}
