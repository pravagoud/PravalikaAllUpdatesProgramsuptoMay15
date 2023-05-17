package com.ojas.timesheet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.timesheet.entity.Technology;

@Repository
public interface TechnologyRepo extends JpaRepository<Technology, Integer> {

}
