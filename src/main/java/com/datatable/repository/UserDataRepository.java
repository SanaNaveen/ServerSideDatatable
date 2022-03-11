package com.datatable.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.datatable.Entites.UserData;

public interface UserDataRepository extends JpaRepository<UserData,Long>{
	

	Page<UserData> findByFullNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrPostalZipContainingIgnoreCaseOrEmailIdContainingIgnoreCaseOrRegionContainingIgnoreCaseOrCountryContainingIgnoreCase(String nameFilter,String phoneFilter,String postalFilter,String emailFilter,String reginFilter,String countryFilter,Pageable pageable); 
	
}
