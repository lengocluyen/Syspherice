package net.syspherice.service;

import java.util.List;

import net.syspherice.dao.DaoFactory;
import net.syspherice.dao.TagsDao;
import net.syspherice.form.Tags;

import org.springframework.stereotype.Service;

@Service
public class TagsServiceImpl implements TagsService {
	
	private TagsDao tagDao;
	public TagsServiceImpl() {
		tagDao = DaoFactory.getTagDao();
	}
	
	public Tags single(String tagID) {
		return tagDao.single(tagID);
	}

	public List<Tags> all() {
		return tagDao.all();
	}

	public Boolean add(Tags tag) {
		return tagDao.insert(tag);
	}

	public Boolean update(Tags tagOrigine, Tags tagUpdate) {
		return tagDao.update(tagOrigine, tagUpdate);
	}

	public Boolean delete(String tagID) {
		return tagDao.delete(tagID);
	}

	public long count() {
		return tagDao.count();
	}

	public List<Tags> paging(int page, int pagesize) {
		return tagDao.paging(page, pagesize);
	}

	public Tags singleByName(String name) {
		return tagDao.singleByName(name);
	}
}
