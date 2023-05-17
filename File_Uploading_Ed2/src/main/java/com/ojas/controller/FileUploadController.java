package com.ojas.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.entity.FileUploadEntity;
import com.ojas.service.DownloadService;
import com.ojas.service.FileUploadService;

@Controller
public class FileUploadController {

	@Value("${user.storage}")
	private String dir;
	@Autowired(required = true)
	private FileUploadService fileUploadSerice;
	@Autowired
	private DownloadService downloadService;
	@PostMapping("/upload")
	// @ResponseBody
	public String fileUpload(@RequestParam("name") String name, @RequestParam("message") String message,
			@RequestParam("uploader") String uploader, @RequestParam("file") MultipartFile file, HttpSession session)
			throws IllegalStateException, IOException {
		// fileUploadSerice.uploadFile(file);
		try {
			System.out.println(name);
			System.out.println(message);
			System.out.println(file.getOriginalFilename());
			// if no value is given in client side it will show some message
			if (name.isEmpty() || message.isEmpty() || file.isEmpty()) {
				session.setAttribute("result", "File Failed To Upload : Provide All Data");
				return "index";
			} else {
				if (uploader.isEmpty()) {
					uploader = "guest";// if uploader name not mentioned in client side ===>it will take default value
										// as "guest"
				}

				session.setAttribute("result", fileUploadSerice.uploadToDB(name, message, file, uploader));// calling
																											// the
																											// uploadToDB()
																											// method
				return "redirect:/index";
			}
		} catch (Exception e) {
			session.setAttribute("result", "File Failed To Upload : " + e.toString());
			return "redirect:/index";
		}
	}

	@GetMapping("/upload")
	public String fileUpload(HttpSession session) throws IllegalStateException, IOException {
		session.setAttribute("result", "No Such Operation Allowed ");
		return "redirect:/index";
	}

	@RequestMapping("/")
	public String defaultHome(Model model) {
		return "index";
	}

	@RequestMapping("/index")
	public String showHome(Model model) {
		return "index";
	}

	@GetMapping("/fileResult/{id}")
	public String fileResult(@PathVariable int id, HttpSession session) {
		System.out.println(id);
		session.setAttribute("fileId", id);
		return "fileResult";

	}
	// ====================>IMG_PNG<================================================================
	@RequestMapping(value = "/fileDownloadImg/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> fileDownloadImg(@PathVariable int id) throws IOException {
		System.out.println("nag____");
		System.out.println(id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>IMG_JPG<===============================================================
	@RequestMapping(value = "/fileDownloadPng/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> fileDownloadPng(@PathVariable int id) throws IOException {
		System.out.println("nag____Png");
		System.out.println(id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>IMG_GIF<=====================================================================
	@RequestMapping(value = "/fileDownloadGif/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_GIF_VALUE)
	public ResponseEntity<?> fileDownloadGif(@PathVariable int id) throws IOException {
		System.out.println("nag____Gif");
		System.out.println(id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>XML<=======================================================================
	@RequestMapping(value = "/fileDownloadXML/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_XML_VALUE)
	public @ResponseBody String fileDownloadXML(@PathVariable int id) throws IOException {
		System.out.println("nag____XMl");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());

		// Let's get XML file as String using BufferedReader
		// FileReader uses platform's default character encoding
		// if you need to specify a different encoding,
		// use InputStreamReader
		Reader fileReader = new FileReader(f);
		BufferedReader bufReader = new BufferedReader(fileReader);

		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		while (line != null) {
			sb.append(line).append("\n");
			line = bufReader.readLine();
		}
		String xml2String = sb.toString();
		System.out.println("XML to String using BufferedReader : ");
		System.out.println(xml2String);

		bufReader.close();

		return xml2String;

	}

	// =======================xml--2==================================
	@RequestMapping(value = "/fileDownloadXml2/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<?> fileDownloadXml2(@PathVariable int id) throws IOException {
		System.out.println("nag____Xml---2");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>PDF<====================================================================
	@RequestMapping(value = "/fileDownloadPDF/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> fileDownloadPDF(@PathVariable int id) throws IOException {
		System.out.println("nag____PDF");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>Plain
	// Text<================================================================
	@RequestMapping(value = "/fileDownloadPlain/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> fileDownloadPlain(@PathVariable int id) throws IOException {
		System.out.println("nag____Plain text");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>JSON<===================================================================
	@RequestMapping(value = "/fileDownloadJson/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fileDownloadJson(@PathVariable int id) throws IOException {
		System.out.println("nag____Json");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ====================>HTML<====================================================================

	@RequestMapping(value = "/fileDownloadHtml/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> fileDownloadHtml(@PathVariable int id) throws IOException {
		System.out.println("nag____Html");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ===========================>Audio<=================================================
	@RequestMapping(value = "/fileDownloadAudio/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> fileDownloadAudio(@PathVariable int id) throws IOException {
		System.out.println("nag____Audio");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// ===========================>VIDEO<=================================================
	@RequestMapping(value = "/fileDownloadVideo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> fileDownloadVideo(@PathVariable int id) throws IOException {
		System.out.println("Naga video");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// =================================ZIP========================================
	@RequestMapping(value = "/fileDownloadZip/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> fileDownloadZip(@PathVariable int id) throws IOException {
		System.out.println("Naga Zip");
		System.out.println("------------>" + id);
		Optional<FileUploadEntity> fObj = downloadService.getFileById(id);
		System.out.println(fObj.get().getFile());
		File f = new File(fObj.get().getFile());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		ResponseEntity<?> responseEntity = null;
		if (fileContent == null)
			responseEntity = ResponseEntity.notFound().build();
		else
			responseEntity = ResponseEntity.ok(fileContent);
		return responseEntity;

	}

	// =================================ZIP========================================
	@RequestMapping(value = "/fileDownloadDirect/{fileName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> fileDownloadDirect(@PathVariable String fileName) throws IOException {
		System.out.println("fileDownloadDirect Zip");
		File f = new File(dir, fileName);
		System.out.println(f.getAbsolutePath());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		f.delete();

		return ResponseEntity.ok().header("Content-Disposition", "attachment;filename="+f.getName())
				.header("Content-Type", "application/octet-stream")
				.body(fileContent);

	}

}
