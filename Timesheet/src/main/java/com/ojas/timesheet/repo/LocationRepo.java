package com.ojas.timesheet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.timesheet.entity.Location;

public interface LocationRepo extends JpaRepository<Location, Integer> {

}
