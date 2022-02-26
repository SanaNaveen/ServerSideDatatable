package com.datatable.service;

import com.datatable.dto.PaginationDto;
import com.datatable.dto.ResponseData;
import com.datatable.dto.UserDataDto;

public interface UserDataService {

	public ResponseData getAllUserData(PaginationDto paginationDto);
	
	public ResponseData searchData(UserDataDto userDataDto);
}
