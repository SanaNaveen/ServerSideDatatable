package com.datatable.serviceImpl;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	
	@Autowired
	private EntityManager entityManager;

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

	@Override
	public ResponseData searchData(UserDataDto userDataDto) {
		
		ResponseData responseData = new ResponseData();
		
		try {
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserData> criteriaQuery = criteriaBuilder.createQuery(UserData.class);
			Root<UserData> root = criteriaQuery.from(UserData.class);
			
			Predicate predicate = null;
			
			if(null != userDataDto.getFullName() && !userDataDto.getFullName().isEmpty()) {
				predicate = criteriaBuilder.equal(root.get("fullName"),userDataDto.getFullName());
			}
			if(null != userDataDto.getEmailId() && !userDataDto.getEmailId().isEmpty()) {
				predicate = criteriaBuilder.equal(root.get("emailId"),userDataDto.getEmailId());
			}
			
			if(null != userDataDto.getCountry() && !userDataDto.getCountry().isEmpty()) {
				predicate = criteriaBuilder.equal(root.get("country"),userDataDto.getCountry());
			}
			
			criteriaQuery.where(predicate);
			
			List<UserData> userDatas = entityManager.createQuery(criteriaQuery).getResultList();
			
			if(userDatas.size() > 0) {
				System.out.println(userDatas.size());
				responseData.setResponseList(userDatas);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDataServiceImpl :: searchData -> Error : "+e);
		}
		
		return responseData;
	}
	
	

}
