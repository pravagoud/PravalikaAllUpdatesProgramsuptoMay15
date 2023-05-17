package com.ojas.hiring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.ApiManager;
import com.ojas.hiring.repo.ApiManagerRepository;

@Service
public class ApiManagerServiceImpl implements ApiManagerService {

	@Autowired
	private ApiManagerRepository repo;

	public ApiManager saveApiDetails(ApiManager api) {
		api.setVisibility(1);
		
		return repo.save(api);
	}

	@Override
	public List<ApiManager> getAllApiDetails() {
		return repo.findAll();
	}

	@Override
	public Optional<ApiManager> getApiById(int id) {
		return repo.findById(id);
	}

	@Override
	public void deleteApiById(int id) {
		repo.deleteById(id);
	}

}
