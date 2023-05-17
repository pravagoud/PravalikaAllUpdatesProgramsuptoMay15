package com.ojas.timesheet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ojas.timesheet.entity.FileData;
import com.ojas.timesheet.repo.FileDataRepo;
import com.ojas.timesheet.service.TimeSheetService;
import com.ojas.timesheet.service.impl.FileDataServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
public class FileDataController {

	@Autowired
	private FileDataServiceImpl fileDataService;

	@Autowired
	private FileDataRepo fileDataRepo;

	@Autowired
	private TimeSheetService timeSheetService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile[] uploadfile,
			@RequestParam("timesheet_Id") int timesheet_Id) throws IOException {
		String uploadImage = fileDataService.uploadImage(uploadfile, timesheet_Id);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@GetMapping("/getfile/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable long id, HttpServletResponse resp) throws Exception {

		Optional<FileData> filedetails = fileDataRepo.findById(id);

		try {
			FileData dbFile = fileDataService.getFile(id);
			if (dbFile.getName().toLowerCase().endsWith(".pdf")) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
				ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(dbFile.getApprovedFile(), headers, HttpStatus.OK);

				return response;
			}
			if (dbFile.getName().toLowerCase().endsWith(".png")) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
				ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(dbFile.getApprovedFile(), headers, HttpStatus.OK);

				return response;
			}
			if (dbFile.getName().toLowerCase().endsWith(".jpg")) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
				ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(dbFile.getApprovedFile(), headers, HttpStatus.OK);

				return response;
			}
			else {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
			    resp.setHeader("Content-Disposition", "attachment; filename=" + dbFile.getName());

				ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(dbFile.getApprovedFile(), headers, HttpStatus.OK);

				return response;
			}

		} catch (Exception e) {
			throw new Exception("Error downloading file");

		}

	}
}
