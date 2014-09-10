package net.syspherice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.syspherice.dao.ContactDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.dao.ExcelDataDocDao;
import net.syspherice.form.ExcelDataDoc;

@Service
public class ExcelDataDocServiceImpl implements ExcelDataDocService{
	private ExcelDataDocDao excelDataDocDao;

	public ExcelDataDocServiceImpl() {
		excelDataDocDao = DaoFactory.getExcelDataDocDao();
	}
	public ExcelDataDoc single(String excelDataDocID) {
		return excelDataDocDao.single(excelDataDocID);
	}

	public List<ExcelDataDoc> all() {
		return excelDataDocDao.all();
	}

	public Boolean add(ExcelDataDoc data) {
		return excelDataDocDao.insert(data);
	}

	public Boolean update(ExcelDataDoc excelDataDocOrigine,
			ExcelDataDoc excelDataDocUpdate) {
		return excelDataDocDao.update(excelDataDocOrigine, excelDataDocUpdate);
	}

	public Boolean delete(String excelDataDocID) {
		return excelDataDocDao.delete(excelDataDocID);
	}

	public long count() {
		return excelDataDocDao.count();
	}

	public List<ExcelDataDoc> paging(int page, int pagesize) {
		return excelDataDocDao.paging(page, pagesize);
	}
	public ExcelDataDoc singleByName(String projectName) {
		return excelDataDocDao.singleByprojectName(projectName);
	}

}
