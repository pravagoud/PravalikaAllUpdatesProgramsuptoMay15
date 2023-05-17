package com.ojas.timesheet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.timesheet.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
