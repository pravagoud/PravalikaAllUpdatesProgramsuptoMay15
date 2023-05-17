package com.ojas.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojas.entity.FileUploadEntity;
import com.ojas.repository.FileRepository;

@Service
public class DownloadService {
	@Value("${user.storage}")
	private String dir;
	@Value("${user.expiry_time}")
	private long fileExpiry;
	@Autowired(required = true)
	private FileRepository repo;
	public List<FileUploadEntity> getAllFiles() {
		System.out.println("------------Get All--------------");
//	 List<Date> fileUploadedTime = repo.getFileUploadedTime();
//	 System.out.println("================>"+fileUploadedTime);
//	
	//	return repo.findAll();
		System.out.println("-----------"+repo.getAllValidFiles(fileExpiry));
		return repo.getAllValidFiles(fileExpiry);
	}
	
	public Optional<FileUploadEntity> getFileById( int id){
		return repo.findById(id);
		
	}
	public static void zipFile() throws IOException {
		byte[] buffer=new byte[1024];
		FileOutputStream fos=new FileOutputStream("D:\\docs\\resume.zip");
		ZipOutputStream zos=new ZipOutputStream(fos);
		ZipEntry ze=new ZipEntry("resume1.txt");
		zos.putNextEntry(ze);
		FileInputStream in=new FileInputStream("D:\\docs\\resume.txt");
		int len=0;
		while((len=in.read(buffer))>0) {
			zos.write(buffer,0,len);
			in.close();
			zos.close();
			System.out.println("done");
			
		}
	}
		public List<FileUploadEntity> getUserFiles(String UserName) {
			 List<FileUploadEntity> userFiles = repo.getUserFiles(UserName);
			 System.out.println(userFiles);
			
			return userFiles;
	}
		
		public List<FileUploadEntity> getUserPng(String userName) {
			 List<FileUploadEntity> userPng = repo.getUserPng(userName);
			 System.out.println(userPng);
			
			return userPng;
	}
		
		public List<FileUploadEntity> getUserAudio(String userName) {
		List<FileUploadEntity> audioFiles = repo.getAudioFiles(userName);
			 System.out.println(audioFiles);
			
			return audioFiles;
	}

		public List<FileUploadEntity> getVideoAudio(String userName) {
			
			return repo.getVideoFiles(userName);
		}

		public List<FileUploadEntity> getUserGif(String userName) {
			// TODO Auto-generated method stub
			 return repo.getGifFiles(userName);
		}


}
