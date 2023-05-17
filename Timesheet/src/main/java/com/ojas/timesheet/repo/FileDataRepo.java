package com.ojas.timesheet.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Transient;

import com.ojas.timesheet.entity.FileData;

public interface FileDataRepo extends JpaRepository<FileData, Long> {

	Optional<FileData> findByName(String fileName);
	
	
	
}
