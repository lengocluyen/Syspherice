package net.syspherice.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import net.syspherice.dao.BinXmlDataDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.form.BinXmlData;
import net.syspherice.form.FolderImage;
import net.syspherice.utils.Common;
import net.syspherice.utils.SessionManage;

@Service
public class BinXmlDataServiceImpl implements BinXmlDataService {

	private BinXmlDataDao xmlDataDao;

	public BinXmlDataServiceImpl() {
		xmlDataDao = DaoFactory.getBinXmlDataDao();
	}

	public BinXmlData single(String xmlID) {
		return xmlDataDao.single(xmlID);
	}

	public List<BinXmlData> all() {
		return xmlDataDao.all();
	}

	public Boolean add(BinXmlData data) {
		return xmlDataDao.insert(data);
	}

	public Boolean update(BinXmlData dataOrigine, BinXmlData dataUpdate) {
		return xmlDataDao.update(dataOrigine, dataUpdate);
	}

	public Boolean delete(String xmlID) {
		return xmlDataDao.delete(xmlID);
	}

	public long count() {
		return xmlDataDao.count();
	}

	public List<BinXmlData> paging(int page, int pagesize) {
		return xmlDataDao.paging(page, pagesize);
	}
	
	public int addBinXmlDataFromListFile(FolderImage folderImage,
			HttpSession session) {
		
		SessionManage sessionManage = new SessionManage(session);
		String filename = folderImage.getUrl().substring(11,folderImage.getUrl().length());
		
		String phycialfile = session.getServletContext().getRealPath(filename);
		File directory = new File(phycialfile);
		String[] extensions = new String[] { "ricegr", "xml"};
		List<File> files = Common.listFilesForFolder(directory);
		int result = 0;
		for (File file : files) {
			if(Common.checkExisteExtensionFile(file, extensions)){
			int pStart = file.getPath().indexOf(filename);
				BinXmlData xmlData = new BinXmlData();
				xmlData.setDateImport(folderImage.getDateImport());
				xmlData.setUserImport(sessionManage.getAccount()
						.getUsername());
				xmlData.setName(file.getName());
				//String imgPath = file.getPath().substring(pStart,file.getPath().length());
				xmlData.setUrl(file.getPath());
				// get information on path of image

				String[] infoNameImage = file.getName().split("_");
				if (infoNameImage.length == 6) {
					//xmlData.setPlantID(infoNameImage[0].substring(1,infoNameImage[0].length()));// plantID
					xmlData.setPlantID(infoNameImage[0]);// plantID
					
					xmlData.setSowing_nb(infoNameImage[1]);// sowing_nb
					xmlData.setRepet_nb(infoNameImage[2]);// repet_nb
					xmlData.setPlant_nb(infoNameImage[3]);// plant_nb;
					xmlData.setPanicle_nb(infoNameImage[4]);// Panicle_nb
				} else
					xmlData.setPlantID(file.getName());

				xmlDataDao.insert(xmlData);
				result++;
			}
		}
		return result;
	}
	public List<BinXmlData> findByPlantID(String id){
		return xmlDataDao.findByPlantID(id);
	}
}
