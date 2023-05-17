package com.ojas.hiring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.ojas.hiring.entity.ApiManager;

@Service
public interface ApiManagerService {
	public ApiManager saveApiDetails(ApiManager api);
	public List<ApiManager> getAllApiDetails();
	public Optional<ApiManager> getApiById(int id);
	void deleteApiById(int id);
}
