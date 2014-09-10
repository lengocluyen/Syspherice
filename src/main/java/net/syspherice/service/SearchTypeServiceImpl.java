package net.syspherice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.syspherice.dao.DaoFactory;
import net.syspherice.dao.SearchTypeDao;
import net.syspherice.form.SearchType;

@Service
public class SearchTypeServiceImpl implements SearchTypeService{

	private SearchTypeDao searchTypeDao;
	public SearchTypeServiceImpl() {
		searchTypeDao = DaoFactory.getSearchTypeDao();
	}
	
	public SearchType single(String searchTypeID) {
		return searchTypeDao.single(searchTypeID);
	}

	public SearchType singleByDisplayNameAndProjectName(String displayName, String projectName){
		return searchTypeDao.singleByDisplayNameAndProjectName(displayName, projectName);
	}
	
	public List<SearchType> all() {
		return searchTypeDao.all();
	}

	public Boolean add(SearchType searchType) {
		return searchTypeDao.insert(searchType);
	}

	public Boolean update(SearchType objOrigine, SearchType objUpdate) {
		return searchTypeDao.update(objOrigine, objUpdate);
	}

	public Boolean delete(String searchTypeID) {
		return searchTypeDao.delete(searchTypeID);
	}

	public long count() {
		return searchTypeDao.count();
	}

	public List<SearchType> paging(int page, int pagesize) {
		return searchTypeDao.paging(page, pagesize);
	}
	
	public List<SearchType> searchByProjectName(String projectName){
		return searchTypeDao.findByProjectName(projectName);
	}
}
