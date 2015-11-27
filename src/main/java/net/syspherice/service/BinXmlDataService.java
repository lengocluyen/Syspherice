package net.syspherice.service;

import java.util.List;

import javax.servlet.http.HttpSession;


import net.syspherice.form.BinXmlData;
import net.syspherice.form.FolderImage;

public interface BinXmlDataService {
	BinXmlData single(String imageID);

	List<BinXmlData > all();

	Boolean add(BinXmlData  data) ;
	Boolean update(BinXmlData  dataOrigine, BinXmlData  dataUpdate) ;

	Boolean delete(String imageID);
	
	long count();
	List<BinXmlData > paging(int page, int pagesize);
	int addBinXmlDataFromListFile(FolderImage folderImage,HttpSession session);
	
	List<BinXmlData > findByPlantID(String id);
}
