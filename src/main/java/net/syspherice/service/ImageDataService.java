package net.syspherice.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.syspherice.form.FolderImage;
import net.syspherice.form.ImageData;

public interface ImageDataService {
	ImageData single(String imageID);

	List<ImageData> all();

	Boolean add(ImageData data) ;
	Boolean update(ImageData dataOrigine, ImageData dataUpdate) ;

	Boolean delete(String imageID);
	
	long count();
	List<ImageData> paging(int page, int pagesize);
	int addImageDataFromListFile(FolderImage folderImage,HttpSession session);
	
	List<ImageData> findByPlantID(String id);
}
