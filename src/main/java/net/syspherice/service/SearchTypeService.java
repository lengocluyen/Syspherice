package net.syspherice.service;

import java.util.List;

import net.syspherice.form.SearchType;

public interface SearchTypeService {
	SearchType single(String searchTypeID);
	SearchType singleByDisplayNameAndProjectName(String displayName, String projectName);
	List<SearchType> all();

	Boolean add(SearchType searchType) ;
	Boolean update(SearchType objOrigine, SearchType objUpdate) ;

	Boolean delete(String searchTypeID);
	
	long count();
	List<SearchType> paging(int page, int pagesize);
	List<SearchType> searchByProjectName(String projectName);
}
