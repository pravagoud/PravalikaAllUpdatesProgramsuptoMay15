package com.ojas.timesheet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.timesheet.entity.Client;

public interface ClientRepo extends JpaRepository<Client, Integer> {

}
