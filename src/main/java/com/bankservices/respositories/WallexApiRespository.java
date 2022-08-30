package com.bankservices.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankservices.entities.WallexAPI;

public interface WallexApiRespository extends JpaRepository<WallexAPI, Long> {
	
	

}
