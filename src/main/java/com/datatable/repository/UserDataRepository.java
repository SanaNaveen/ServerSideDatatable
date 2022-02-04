package com.datatable.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.datatable.Entites.UserData;

public interface UserDataRepository extends JpaRepository<UserData,Long>{
	

}
