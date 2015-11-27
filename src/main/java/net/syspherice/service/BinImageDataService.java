package net.syspherice.service;

import java.util.List;

import javax.servlet.http.HttpSession;


import net.syspherice.form.BinImageData;
import net.syspherice.form.FolderImage;

public interface BinImageDataService {
	BinImageData single(String imageID);

	List<BinImageData> all();

	Boolean add(BinImageData data) ;
	Boolean update(BinImageData dataOrigine, BinImageData dataUpdate) ;

	Boolean delete(String imageID);
	
	long count();
	List<BinImageData> paging(int page, int pagesize);
	int addImageDataFromListFile(FolderImage folderImage,HttpSession session);
	
	List<BinImageData> findByPlantID(String id);
}
