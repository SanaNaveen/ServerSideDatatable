package com.datatable.serviceImpl;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.datatable.Entites.UserData;
import com.datatable.dto.DataTable;
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
		
		String sortByName ="id";
		String sortByOrder = "asc";

		try {
			
			int page = paginationDto.getStart() / paginationDto.getLength();
			
			for(int i=0;i < paginationDto.getOrder().size();i++) {
				
				if(null != paginationDto.getColumns().get(i).getData()) {				
					sortByOrder =paginationDto.getOrder().get(i).getDir();
					sortByName = paginationDto.getColumns().get(Integer.parseInt(paginationDto.getOrder().get(i).getColumn())).getData();
				}
				
			}
			
			Pageable pageable = null;
			
			if(sortByOrder.equalsIgnoreCase("desc")) {
				pageable = PageRequest.of(page,paginationDto.getLength(),Sort.by(sortByName).descending());
			}else {
				pageable = PageRequest.of(page,paginationDto.getLength(),Sort.by(sortByName).ascending());
			}
			
			Page<UserData> userDatas = null;
		
			if(null != paginationDto.getSearch().getValue() && !paginationDto.getSearch().getValue().isEmpty()) {
				String search =paginationDto.getSearch().getValue();
				userDatas = userDataRepository.findByFullNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrPostalZipContainingIgnoreCaseOrEmailIdContainingIgnoreCaseOrRegionContainingIgnoreCaseOrCountryContainingIgnoreCase(search,search,search,search,search,search,pageable);
			}else {
				userDatas= userDataRepository.findAll(pageable);
			}
			
			
			DataTable dataTable = new DataTable<>();
			
			dataTable.setData(userDatas.getContent());
		    dataTable.setRecordsTotal(userDatas.getTotalElements());
		    dataTable.setRecordsFiltered(userDatas.getTotalElements());

		    dataTable.setDraw(paginationDto.getDraw());
		    dataTable.setStart(paginationDto.getStart());
			
			responseData.setResponseList(dataTable);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDataServiceImpl :: getAllUserData -> Error : "+e);
		}
		return responseData;
	}
	
	
	@Override
	public ResponseData paginationSearchData(PaginationDto paginationDto) {
		
		ResponseData responseData = new ResponseData();
		
		try {
			
			int page = paginationDto.getStart() / paginationDto.getLength();
			
			Pageable pageable = null;
			
			String sortByName ="id";
			String sortByOrder = "asc";
	
			
			for(int i=0;i < paginationDto.getOrder().size();i++) {
				
				if(null != paginationDto.getColumns().get(i).getData()) {				
					sortByOrder =paginationDto.getOrder().get(i).getDir();
					sortByName = paginationDto.getColumns().get(Integer.parseInt(paginationDto.getOrder().get(i).getColumn())).getData();
				}
				
			}
			
			if(sortByOrder.equalsIgnoreCase("desc")) {
				pageable = PageRequest.of(page,paginationDto.getLength(),Sort.by(sortByName).descending());
			}else {
				pageable = PageRequest.of(page,paginationDto.getLength(),Sort.by(sortByName).ascending());
			}
			
		
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserData> criteriaQuery = criteriaBuilder.createQuery(UserData.class);
			Root<UserData> root = criteriaQuery.from(UserData.class);
			
			Predicate predicate = null;
			
			if(null != paginationDto.getFullName() && !paginationDto.getFullName().isEmpty()) {
				predicate = criteriaBuilder.equal(root.get("fullName"),paginationDto.getFullName());
			}
			if(null != paginationDto.getEmailId() && !paginationDto.getEmailId().isEmpty()) {
				predicate = criteriaBuilder.equal(root.get("emailId"),paginationDto.getEmailId());
			}
			
			if(null != paginationDto.getCountry() && !paginationDto.getCountry().isEmpty()) {
				predicate = criteriaBuilder.equal(root.get("country"),paginationDto.getCountry());
			}
			
			criteriaQuery.where(predicate);
			
	
			
			List<UserData> userDatas = entityManager.createQuery(criteriaQuery).getResultList();
			
			
			TypedQuery<UserData>  typedQuery = entityManager.createQuery(criteriaQuery);
			
			typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
			
			typedQuery.setMaxResults(pageable.getPageSize());
			
			
			
			Page<UserData> result = new PageImpl<UserData>(typedQuery.getResultList(),pageable,userDatas.size());
			
			
			DataTable dataTable = new DataTable<>();
			
			dataTable.setData(result.getContent());
		    dataTable.setRecordsTotal(result.getTotalElements());
		    dataTable.setRecordsFiltered(result.getTotalElements());

		    dataTable.setDraw(paginationDto.getDraw());
		    dataTable.setStart(paginationDto.getStart());
			
			responseData.setResponseList(dataTable);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDataServiceImpl :: searchData -> Error : "+e);
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
