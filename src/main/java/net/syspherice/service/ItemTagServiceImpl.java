package net.syspherice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.syspherice.dao.DaoFactory;
import net.syspherice.dao.ItemTagDao;
import net.syspherice.form.ItemTag;

@Service
public class ItemTagServiceImpl implements ItemtagService{

	private ItemTagDao itemTagDao;
	public ItemTagServiceImpl() {
		itemTagDao = DaoFactory.getItemTagDao();
	}
	public ItemTag single(String itemTagID) {
		return itemTagDao.single(itemTagID);
	}

	public List<ItemTag> all() {
		return itemTagDao.all();
	}

	public Boolean add(ItemTag data) {
		return itemTagDao.insert(data);
	}

	public Boolean update(ItemTag itemtagOrigine, ItemTag itemtagUpdate) {
		return itemTagDao.update(itemtagOrigine, itemtagUpdate);
	}

	public Boolean delete(String itemtagID) {
		return itemTagDao.delete(itemtagID);
	}

	public long count() {
		return itemTagDao.count();
	}

	public List<ItemTag> paging(int page, int pagesize) {
		return itemTagDao.paging(page, pagesize);
	}
	public ItemTag singleByItem(ItemTag item){
		return itemTagDao.singleByItems(item);
	}
	public List<ItemTag> findByTagID(String tagID){
		return itemTagDao.findByTagID(tagID);
	}

}
