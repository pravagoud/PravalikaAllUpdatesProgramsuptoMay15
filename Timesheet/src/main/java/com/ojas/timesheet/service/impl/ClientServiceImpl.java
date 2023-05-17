package com.ojas.timesheet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.timesheet.entity.Client;
import com.ojas.timesheet.repo.ClientRepo;
import com.ojas.timesheet.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepo clientRepo;

	@Override
	public Client addClient(Client client) {
		// TODO Auto-generated method stub
		return clientRepo.save(client);
	}
	
	
	
}
