package com.ojas.hiring.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.ApiManager;
import com.ojas.hiring.service.ApiManagerServiceImpl;

@RestController
@RequestMapping("/api")
public class ApiManagerController {
	@Autowired
	private ApiManagerServiceImpl apiManagerService;
	@PostMapping("/saveApiDetails")
public ApiManager saveApiDetails(@RequestBody ApiManager api) {
		java.util.Date d = new java.util.Date();
		String s = new SimpleDateFormat("dd-MM-yyyy").format(d);
		api.setPublishedDate(s);
	return apiManagerService.saveApiDetails(api);	
}
	@GetMapping("/getAllApis")
public List<ApiManager> getAllApiDetails(){
	return apiManagerService.getAllApiDetails();	
	}
	@GetMapping("/getApiById/{id}")
	public Optional<ApiManager> getApiById(@PathVariable("id") int id) {
		return apiManagerService.getApiById(id);	
	}
	@DeleteMapping("/deleteApiById/{id}")
	public String deleteApiById(@PathVariable("id") int id) {
		apiManagerService.deleteApiById(id);
		return "successfully Api Deleted";	
	}
	@PutMapping("/updateApi")
	public String updateApi(@RequestBody ApiManager api) {
		apiManagerService.saveApiDetails(api);
		return "succesFully Updated" + api.getApiName();

	}
	
}
