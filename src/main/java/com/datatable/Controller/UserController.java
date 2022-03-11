package com.datatable.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datatable.dto.PaginationDto;
import com.datatable.dto.ResponseData;
import com.datatable.dto.UserDataDto;
import com.datatable.service.UserDataService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(value = "*")
public class UserController {

	@Autowired
	UserDataService userDataService;
	
	@PostMapping(value ="/getAllUserData")
	public ResponseEntity<?> getAllUserData(@RequestBody PaginationDto paginationDto) {
		
		ResponseData responseData = new ResponseData();
		
		if(paginationDto.getSearchType().equalsIgnoreCase("Fillter")) {
			
			 responseData = userDataService.paginationSearchData(paginationDto);
		}else {
			responseData = userDataService.getAllUserData(paginationDto);
		}
				
		
		return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
	}
	
	@PostMapping(value ="/searchData")
	public ResponseEntity<?> searchData(@RequestBody UserDataDto userDataDto){
		
		ResponseData responseData = userDataService.searchData(userDataDto);
		
		return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK); 
	}
}
