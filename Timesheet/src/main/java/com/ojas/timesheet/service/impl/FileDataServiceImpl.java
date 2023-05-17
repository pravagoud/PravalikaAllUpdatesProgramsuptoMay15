package com.ojas.timesheet.service.impl;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.timesheet.entity.FileData;
import com.ojas.timesheet.entity.TimeSheet;
import com.ojas.timesheet.repo.FileDataRepo;
import com.ojas.timesheet.util.ImageUtils;

@Service
public class FileDataServiceImpl {

	@Autowired
	private FileDataRepo fileDataRepo;

	public String uploadImage(MultipartFile[] uploadfile, int timesheet_Id) throws IOException {

		FileData fileModel = new FileData();
		List<String> fileNames = new ArrayList<String>();
		try {
			List<FileData> storedFile = new ArrayList<FileData>();

			for (MultipartFile file : uploadfile) {

				fileModel.setApprovedFile(file.getBytes());

				fileModel = new FileData(null, file.getOriginalFilename(), file.getContentType(), file.getBytes(),
						timesheet_Id);

				if (file.getOriginalFilename() != "") {
					fileNames.add(file.getOriginalFilename());
					storedFile.add(fileModel);
					fileDataRepo.saveAllAndFlush(storedFile);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public FileData getFile(long id) throws Exception {
        return fileDataRepo.findById(id)
                .orElseThrow(() -> new Exception("File not found with id " + id));
    }

}
