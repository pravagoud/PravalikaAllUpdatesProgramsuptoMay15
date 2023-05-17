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
import com.ojas.timesheet.entity.Client;
import com.ojas.timesheet.entity.Technology;
import com.ojas.timesheet.repo.ClientRepo;
import com.ojas.timesheet.service.ClientService;
/*
 * @Author
 * @Akshaya Mahanty
 * */
@Controller
public class ClientController {

	private static final Logger logger = LogManager.getLogger(ClientController.class);
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientRepo clientRepo;
	
	@RequestMapping("/addclient")
	public String addTechnology(Model model) {
		return "admin/addClient";
	}
	
	@PostMapping(path = "client", consumes = "application/json", produces = "application/json")
	public @ResponseBody String addTechnology(@RequestBody String ajaxRequest) {
		JsonObject jobj = new Gson().fromJson(ajaxRequest, JsonObject.class);
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		String clientName = jobj.get("clientName").getAsString().toLowerCase();
		logger.info("ajax req is  ---" + ajaxRequest);
		
		List<Client> findAll = clientRepo.findAll();
		boolean found = false;
		for (Client client : findAll) {
			
			if(client.getName().equals(clientName)) {
				found = true;
			}
		}
		String jsonString = "";
		Gson gson = new GsonBuilder().create();
		if(found != true) {
			try {
				Client client = new Client();
				client.setName(clientName);
				client.setCreateDate(date);
				client.setSoftdelete(1);
				Client save = clientService.addClient(client);
				
				save.setName(save.getName()+" Sucessfully Created...");
				jsonString = gson.toJson(save);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			Client client = new Client();
			client.setName("Client already exists...");
		    jsonString = gson.toJson(client);
		}
		return jsonString;
	}
	
	@GetMapping("/fetchclientdata")
	public Object featchTechDetails() {
		List<Client> tech = clientRepo.findAll();
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(tech);
		System.out.println(jsonString);
		return new ResponseEntity<Object>(jsonString, HttpStatus.OK);
	}
	
}
