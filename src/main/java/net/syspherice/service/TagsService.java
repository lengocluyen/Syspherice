package net.syspherice.service;

import java.util.List;
import net.syspherice.form.Tags;

public interface TagsService {
	Tags single(String tagID);

	List<Tags> all();

	Boolean add(Tags tag) ;
	Boolean update(Tags tagOrigine, Tags tagUpdate) ;

	Boolean delete(String tagID);
	
	long count();
	Tags singleByName(String name);
	List<Tags> paging(int page, int pagesize);
}
