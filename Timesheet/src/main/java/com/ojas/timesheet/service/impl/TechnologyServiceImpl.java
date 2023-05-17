package com.ojas.timesheet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.timesheet.entity.Technology;
import com.ojas.timesheet.repo.TechnologyRepo;
import com.ojas.timesheet.service.TechnologyService;
/*
 * @Author
 * @Akshaya Mahanty
 * */
@Service
public class TechnologyServiceImpl implements TechnologyService {

	@Autowired
	private TechnologyRepo technologyRepo;

	@Override
	public Technology addTechnology(Technology tech) {
		return technologyRepo.save(tech);
	}
	
	
}
