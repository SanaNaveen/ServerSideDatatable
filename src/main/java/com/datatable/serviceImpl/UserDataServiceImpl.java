package com.datatable.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.datatable.Entites.UserData;
import com.datatable.dto.PaginationDto;
import com.datatable.dto.ResponseData;
import com.datatable.dto.UserDataDto;
import com.datatable.repository.UserDataRepository;
import com.datatable.service.UserDataService;

@Service
public class UserDataServiceImpl implements UserDataService{
	
	@Autowired
	UserDataRepository userDataRepository;

	@Override
	public ResponseData getAllUserData(PaginationDto paginationDto) {
		
		ResponseData responseData = new ResponseData<>();

		try {
			
			Pageable pageable = PageRequest.of(paginationDto.getPageNo(),
					paginationDto.getPageSize(),Sort.by(paginationDto.getSortBy()));
			
			Page<UserData> userDatas = userDataRepository.findAll(pageable);
		
			
			responseData.setResponseList(userDatas);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDataServiceImpl :: getAllUserData -> Error : "+e);
		}
		return responseData;
	}
	
	

}
