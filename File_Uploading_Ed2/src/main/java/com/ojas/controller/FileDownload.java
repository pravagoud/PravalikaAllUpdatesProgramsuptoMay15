package com.ojas.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.entity.FileUploadEntity;
import com.ojas.service.DownloadService;

@RestController
public class FileDownload {
	@Value("${user.storage}")
	private String dir;
	@Autowired(required = true)
	private DownloadService downloadService;

	@GetMapping("/getAll")
	public List<FileUploadEntity> getAllFiles() {
		List<FileUploadEntity> obj = downloadService.getAllFiles();
		System.out.println(obj);
		return obj;
	}

	@GetMapping("/file/{id}")
	public Optional<FileUploadEntity> getFileById(@PathVariable int id ) {
		return downloadService.getFileById(id);

	}

	@PostMapping("/downloadBulk")
	public String downloadBulkFiles() {
		return null;

	}

	@RequestMapping(value = "/allFiles/{uploaderName}", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public @ResponseBody String getAllUserFiles(@PathVariable String uploaderName, HttpSession session)
			throws IOException {
		System.out.println("---------------------A--------------------");
		JSONObject obj = new JSONObject();

		File par = new File(dir, uploaderName);
		if (!par.exists()) {
			try {
				obj.put("result", "No Such Uploader ");
				obj.put("file", "no");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonString = obj.toString();
			return jsonString;
		}
		System.out.println(par.getAbsolutePath());
		File[] listFiles = par.listFiles();
		String[] allFiles = new String[listFiles.length];
		for (int i = 0; i < listFiles.length; i++) {
			String name = listFiles[i].getAbsolutePath();
			allFiles[i] = name;
		}
		ZipFileex zipF = new ZipFileex(dir + uploaderName + ".zip");
		zipF.compress(allFiles);

		try {
			obj.put("result", uploaderName + ".zip");
			obj.put("file", "yes");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonString = obj.toString();
		return jsonString;

	}

	@GetMapping("/findUserFiles/{UserName}")
	public List<FileUploadEntity> getUserFiles(@PathVariable String UserName) {
		File f = new File(dir + UserName);
		System.out.println("-------------111111111111111111-------------");
		System.out.println(f);
		if (f.exists()) {
			System.out.println(UserName + " this user data is availbale");
			List<FileUploadEntity> userFiles = downloadService.getUserFiles(UserName);
			System.out.println(userFiles);
			return userFiles;
		} else {
			System.out.println("No such user");
			return null;
		}

	}

	@GetMapping("/getUserPng/{userName}")
	public List<FileUploadEntity> getUserPNG(@PathVariable String userName) {
		
		File f = new File(dir + userName);
		if (f.exists()) {
			System.out.println(userName + " this user data is availbale");
			List<FileUploadEntity> userFiles = downloadService.getUserPng(userName);
			System.out.println(userFiles);
			// return userFiles;
			return null;
		} else {
			System.out.println("No such user");
			return null;
		}

	}

	@GetMapping("/getUserAudio/{userName}")
	public List<FileUploadEntity> getUserAudioFiles(@PathVariable String userName, HttpServletResponse response) throws IOException {
		File f = new File(dir + userName);
		if (f.exists()) {
			System.out.println(userName + " this user data is availbale");
			List<FileUploadEntity> userAudio = downloadService.getUserAudio(userName);

			System.out.println(userAudio);
			return userAudio;
			
		} else {
			String str = "No Such User/File";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(str);
			return null;
		}
	}

	@GetMapping("/getUserVideo/{userName}")
	public List<FileUploadEntity> getUserVideoFiles(@PathVariable String userName) {
		File f = new File(dir + userName);
		if (f.exists()) {
			System.out.println(userName + " this user data is availbale");
			List<FileUploadEntity> userVideo = downloadService.getVideoAudio(userName);
			System.out.println(userVideo);
			return userVideo;
		} else {
			System.out.println("No such user");
			return null;
		}
	}
	

	@GetMapping("/getUserGif/{userName}")
	public List<FileUploadEntity> getUserGifFiles(@PathVariable String userName) {
		File f = new File(dir + userName);
		if (f.exists()) {
			System.out.println(userName + " this user data is availbale");
			 List<FileUploadEntity> userGif = downloadService.getUserGif(userName);
			
			return userGif;
		} else {
			System.out.println("No such user");
			return null;
		}
	}

}
