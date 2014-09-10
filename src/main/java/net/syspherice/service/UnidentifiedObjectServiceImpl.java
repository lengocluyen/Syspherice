package net.syspherice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.syspherice.dao.DaoFactory;
import net.syspherice.dao.ExcelDataDocDao;
import net.syspherice.dao.SearchTypeDao;
import net.syspherice.dao.UnidentifiedObjectDao;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ItemTag;
import net.syspherice.form.SearchInfo;
import net.syspherice.form.SearchType;
import net.syspherice.form.UnidentifiedObject;
import net.syspherice.utils.Common;
import net.syspherice.utils.SheetDataRow;
import net.syspherice.utils.SheetInfo;

import org.springframework.stereotype.Service;

@Service
public class UnidentifiedObjectServiceImpl implements UnidentifiedObjectService {

	private UnidentifiedObjectDao uObjectDao;
	private SearchTypeDao searchTypeDao;

	public UnidentifiedObjectServiceImpl() {
		uObjectDao = new UnidentifiedObjectDao();
	}

	public String nameCollection;

	public int add(String projectName, ExcelDataDoc excelData, SheetInfo info,
			SheetDataRow title, List<SheetDataRow> datas) {
		nameCollection = ExcelDataDoc.class.getSimpleName() + "_" + projectName
				+ "_" + info.getName();
		uObjectDao = DaoFactory.getUnidentifiedObjectDao(nameCollection);
		return uObjectDao.insert(excelData, info, title, datas);
	}

	public Map<String, List<UnidentifiedObject>> findByField(
			SearchInfo searchInfo) {
		searchTypeDao = DaoFactory.getSearchTypeDao();
		SearchType searchType = searchTypeDao
				.single(searchInfo.getSearchType());
		return uObjectDao
				.findbyField(searchInfo.getSearchContent(), searchType);
	}

	public int deleteByTextDataDocID(String textDataDocID) {
		return uObjectDao.deleteBytextDocID(textDataDocID);
	}

	public Map<String, List<UnidentifiedObject>> single(String collectionName,
			String _id) {
		uObjectDao = DaoFactory.getUnidentifiedObjectDao(collectionName);
		return uObjectDao.singlebyID(collectionName, _id);
	}

	public Map<String, List<String>> getUnidentifiedObjectProperties(
			String projectName) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> nameCollections = this
				.getAllCollectionsByName(projectName);
		for (String nameCollection : nameCollections) {
			uObjectDao = DaoFactory.getUnidentifiedObjectDao(nameCollection);
			List<String> properties = uObjectDao.getOneforSearchProperties();
			result.put(nameCollection, properties);
		}
		return result;
	}

	public List<String> getAllCollectionsByName(String projectName) {
		return Common.getSubCollectionByCompareName(
				DaoFactory.getAllCollections(), projectName);
	}

	public Map<String, List<UnidentifiedObject>> findByItemTags(
			List<ItemTag> itemTag) {
		Map<String, List<UnidentifiedObject>> result = new HashMap<String, List<UnidentifiedObject>>();

		for (ItemTag i : itemTag) {
			result.putAll(this.single(i.getCollectionName(),
					i.getItemID()));
		}

		return result;
	}
}
