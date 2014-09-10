package net.syspherice.service;

import java.util.List;

import net.syspherice.form.ItemTag;

public interface ItemtagService {
	ItemTag single(String itemtagID);

	List<ItemTag> all();

	Boolean add(ItemTag tag) ;
	Boolean update(ItemTag itemtagOrigine, ItemTag itemtagUpdate) ;

	Boolean delete(String itemtagID);
	
	long count();
	List<ItemTag> paging(int page, int pagesize);
	ItemTag singleByItem(ItemTag item);
	List<ItemTag> findByTagID(String tagID);
}
