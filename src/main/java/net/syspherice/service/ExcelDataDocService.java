package net.syspherice.service;

import java.util.List;
import net.syspherice.form.ExcelDataDoc;

import org.springframework.stereotype.Repository;

@Repository
public interface ExcelDataDocService {

	ExcelDataDoc single(String excelDataDocID);
	ExcelDataDoc singleByName(String projectName);

	
	List<ExcelDataDoc> all();

	Boolean add(ExcelDataDoc data) ;
	Boolean update(ExcelDataDoc excelDataDocOrigine, ExcelDataDoc excelDataDocUpdate) ;

	Boolean delete(String excelDataDocID);
	
	long count();
	List<ExcelDataDoc> paging(int page, int pagesize);
	
}
