package com.ojas.timesheet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ojas.timesheet.entity.Technology;
import com.ojas.timesheet.entity.User;
import com.ojas.timesheet.repo.TechnologyRepo;
import com.ojas.timesheet.service.TechnologyService;

/*
 * @Author
 * @Akshaya Mahanty
 * */
@Controller
public class TechnologyController {

	private static final Logger logger = LogManager.getLogger(TechnologyController.class);
	@Autowired
	private TechnologyService techService;
	@Autowired
	private TechnologyRepo techRepo;
	
	@RequestMapping("/addtechnology")
	public String addTechnology(Model model) {
		return "admin/addTechnology";
	}
	
	@PostMapping(path = "technology", consumes = "application/json", produces = "application/json")
	public @ResponseBody String addTechnology(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		String techName = jobj.get("techName").getAsString().toLowerCase();
		logger.info("ajax req is  ---" + ajaxRequest);
		
		
		List<Technology> findAll = techRepo.findAll();
	//	System.out.println("Tech all data : "+findAll);
		boolean found = false;
		for (Technology technology : findAll) {
			if(technology.getName().equals(techName)) {
			//	System.out.println(technology.getName()+" Already available in Database.");
				found = true;
			}
		}
		
		String jsonString = "";
		Gson gson = new GsonBuilder().create();

		
		if(found !=true) {
			try {
				Technology tech = new Technology();
				tech.setName(techName);
				tech.setCreateDate(date);
				tech.setSoftdelete(1);
				Technology save = techService.addTechnology(tech);
				
				save.setName(save.getName()+ " Successfully Created...");
				jsonString = gson.toJson(save);
				logger.info("jsonString is ---" + jsonString);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			Technology tech = new Technology();
			tech.setName("Technology already exists...");
			jsonString = gson.toJson(tech);
			logger.info("jsonString is ---" + jsonString);
		}
		return jsonString;
	}
	
	@GetMapping("/fetchtechdata")
	public Object featchTechDetails() {
		List<Technology> tech = techRepo.findAll();
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(tech);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}

}
