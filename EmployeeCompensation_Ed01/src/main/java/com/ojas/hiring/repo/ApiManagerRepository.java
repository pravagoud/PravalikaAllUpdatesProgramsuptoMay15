package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.ApiManager;
@Repository
public interface ApiManagerRepository extends JpaRepository<ApiManager, Integer> {

}
