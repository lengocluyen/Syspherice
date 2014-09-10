package net.syspherice.service;

import java.util.List;
import java.util.Map;

import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ItemTag;
import net.syspherice.form.SearchInfo;
import net.syspherice.form.UnidentifiedObject;
import net.syspherice.utils.SheetDataRow;
import net.syspherice.utils.SheetInfo;

public interface UnidentifiedObjectService {
	int add(String projectName,ExcelDataDoc excelData, SheetInfo info, SheetDataRow title, List<SheetDataRow> datas);
	int deleteByTextDataDocID(String textDataDocID);
	Map<String,List<UnidentifiedObject>> findByField(SearchInfo searchInfo);
	Map<String,List<String>> getUnidentifiedObjectProperties(String projectName);
	List<String> getAllCollectionsByName(String projectName);
	Map<String,List<UnidentifiedObject>> single(String collectionname,String _id);
	Map<String,List<UnidentifiedObject>> findByItemTags(List<ItemTag> itemTag);
}
