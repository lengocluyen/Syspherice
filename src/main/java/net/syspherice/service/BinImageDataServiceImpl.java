package net.syspherice.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.syspherice.dao.BinImageDataDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.dao.BinImageDataDao;
import net.syspherice.form.BinImageData;
import net.syspherice.form.FolderImage;
import net.syspherice.utils.Common;
import net.syspherice.utils.SessionManage;

@Service
public class BinImageDataServiceImpl implements BinImageDataService {

	private BinImageDataDao imageDataDao;

	public BinImageDataServiceImpl() {
		imageDataDao = DaoFactory.getBinImageDataDao();
	}

	public BinImageData single(String imageID) {
		return imageDataDao.single(imageID);
	}

	public List<BinImageData> all() {
		return imageDataDao.all();
	}

	public Boolean add(BinImageData data) {
		return imageDataDao.insert(data);
	}

	public Boolean update(BinImageData dataOrigine, BinImageData dataUpdate) {
		return imageDataDao.update(dataOrigine, dataUpdate);
	}

	public Boolean delete(String imageID) {
		return imageDataDao.delete(imageID);
	}

	public long count() {
		return imageDataDao.count();
	}

	public List<BinImageData> paging(int page, int pagesize) {
		return imageDataDao.paging(page, pagesize);
	}
	
	public int addImageDataFromListFile(FolderImage folderImage,
			HttpSession session) {
		
		SessionManage sessionManage = new SessionManage(session);
		String filename = folderImage.getUrl().substring(11,folderImage.getUrl().length());
		
		String phycialfile = session.getServletContext().getRealPath(filename);
		File directory = new File(phycialfile);
		String[] extensions = new String[] { "png", "jpg", "bmp" };
		List<File> files = Common.listFilesForFolder(directory);
		int result = 0;
		for (File file : files) {
			if(Common.checkExisteExtensionFile(file, extensions)&&!file.isHidden()){
			
			int pStart = file.getPath().indexOf(filename);
				BinImageData imageData = new BinImageData();
				imageData.setDateImport(folderImage.getDateImport());
				imageData.setUserImport(sessionManage.getAccount()
						.getUsername());
				imageData.setName(file.getName());
				String imgPath = file.getPath().substring(pStart,file.getPath().length());
				//imageData.setUrl(imgPath);
				imageData.setUrl(file.getPath());
				// get information on path of image

				String[] infoNameImage = file.getName().split("_");
				if (infoNameImage.length == 6) {
					imageData.setPlantID(infoNameImage[0].substring(1,
							infoNameImage[0].length()));// plantID
					imageData.setSowing_nb(infoNameImage[1]);// sowing_nb
					imageData.setRepet_nb(infoNameImage[2]);// repet_nb
					imageData.setPlant_nb(infoNameImage[3]);// plant_nb;
					imageData.setPanicle_nb(infoNameImage[4]);// Panicle_nb

					imageData.setTypeOfImageData(infoNameImage[5].substring(0,
							1));// type of image data N or P
					imageData.setOrder(infoNameImage[5].substring(1,
							infoNameImage[5].length() - 4));// order
				} else
					imageData.setPlantID(file.getName());

				imageDataDao.insert(imageData);
				result++;
			}
		}
		return result;
	}
	public List<BinImageData> findByPlantID(String id){
		return imageDataDao.findByPlantID(id);
	}
}
