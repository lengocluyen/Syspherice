package net.syspherice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.syspherice.form.ExcelDataDoc;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.UnidentifiedObjectService;
import net.syspherice.utils.*;

@Controller
@RequestMapping("/import")
public class ImportController {
	@Autowired
	private UnidentifiedObjectService uObjectService;
	@Autowired
	private ExcelDataDocService excelDataDocService;
	SessionManage sessionManage;
	
	@RequestMapping("/data")
	public ModelAndView getImportData(ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("import/data");
		mv.addObject(AbsoluteString.tab1, "in active");
		mv.addObject(AbsoluteString.tab2, "");
		SessionManage sessionManage = new SessionManage(session);
		SheetInfo sheetInfo = new SheetInfo();
		ExcelDataDoc excelDataDoc = sessionManage.getExcelDataDoc();
		if (excelDataDoc != null)
			sheetInfo.setPath(excelDataDoc.getFileUrl());
		mv.addObject(AbsoluteString.sheetinfo, sheetInfo);
		
		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ImportData", session));
		return mv;
	}

	@RequestMapping("/dataid/{excelDataDocID}")
	public ModelAndView getImportDataByTextDocID(@PathVariable("excelDataDocID") String excelDataDocID,ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("import/dataid");
		mv.addObject(AbsoluteString.tab1, "in active");
		mv.addObject(AbsoluteString.tab2, "");
		SessionManage sessionManage = new SessionManage(session);
		SheetInfo sheetInfo = new SheetInfo();
		ExcelDataDoc excelDataDoc = excelDataDocService.single(excelDataDocID);
		sessionManage.setExcelDataDoc(excelDataDoc);
		if (excelDataDoc != null)
			sheetInfo.setPath(excelDataDoc.getFileUrl());
		mv.addObject(AbsoluteString.sheetinfo, sheetInfo);

		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ImportData", session));
		return mv;
	}
	// Check data in file excel
	@RequestMapping(value = "/data", params = "checkdata", method = RequestMethod.POST)
	public ModelAndView postImportFinderCheckData(
			@ModelAttribute("sheetinfo") SheetInfo sheetinfo, ModelMap map,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		ModelAndView mv = new ModelAndView("import/data");
		String filename = sheetinfo.getPath().substring(12,
				sheetinfo.getPath().length());
		String phycialfile = session.getServletContext().getRealPath(filename);
		ExcelsHandles exlhandle = new ExcelsHandles();
		exlhandle.setFilename(phycialfile);
		List<SheetInfo> sheets = exlhandle.ReadFile();
		sessionManage.setSheets(sheets);
		List<LabelValue> selectvalues = Common.getColumnsfromSheets(sheets);
		mv.addObject(AbsoluteString.sheetinfo, new SheetInfo());
		mv.addObject(AbsoluteString.selectlist, selectvalues);
		mv.addObject(AbsoluteString.tab1, "");
		mv.addObject(AbsoluteString.tab2, "in active");
	

		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ImportData", session));
		return mv;
	}

	// Save metadata of file excel
	@RequestMapping(value = "/data", params = "saveExcelDoc", method = RequestMethod.POST)
	public ModelAndView postImportFinderMetaData(
			@ModelAttribute("sheetinfo") SheetInfo sheetinfo, ModelMap map,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("import/data");

		sessionManage = new SessionManage(session);
		// 12: /syspherice/
		String filename = sheetinfo.getPath().substring(12,sheetinfo.getPath().length());
		String phycialfile = session.getServletContext().getRealPath(filename);
		
		ExcelsHandles exlhandle = new ExcelsHandles();
		exlhandle.setFilename(phycialfile);
		List<SheetInfo> sheets = exlhandle.ReadFile();

		sessionManage.setSheets(sheets);
		
		List<LabelValue> selectvalues = Common.getColumnsfromSheets(sheets);

		mv.addObject(AbsoluteString.sheetinfo, sheetinfo);
		mv.addObject(AbsoluteString.selectlist, selectvalues);
		mv.addObject(AbsoluteString.tab1, "");
		mv.addObject(AbsoluteString.tab2, "in active");
		

		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ImportData", session));
		return mv;
	}

	// Viewsheet data
	@RequestMapping(value = "/data", params = "viewsheet", method = RequestMethod.POST)
	public ModelAndView postImportFinderViewSheet(@ModelAttribute("sheetinfo") SheetInfo sheetinfo, ModelMap map,HttpSession session) {
		ModelAndView mv = new ModelAndView("import/data");
		ExcelsHandles exlhandle = new ExcelsHandles();
		sessionManage = new SessionManage(session);
		List<SheetInfo> sheets = sessionManage.getSheets();
		List<LabelValue> selectvalues = Common.getColumnsfromSheets(sheets);

		mv.addObject(AbsoluteString.sheetinfo, sheetinfo);
		mv.addObject(AbsoluteString.selectlist, selectvalues);
		mv.addObject(AbsoluteString.tab1, "");
		mv.addObject(AbsoluteString.tab2, "");
		mv.addObject(AbsoluteString.tab3, "in active");
		
		List<SheetDataRow> sheetdatarows = exlhandle.ReadSheet(sheets.get(sheetinfo.getNumber()));
		SheetDataRow rowHead = sheetdatarows.get(exlhandle.getTitleRow());
		List<SheetDataRow> rowsBody = Common.getRowBody(rowHead, exlhandle, sheetinfo, sheets);
		mv.addObject(AbsoluteString.sheethead, rowHead);
		mv.addObject(AbsoluteString.sheetdata, rowsBody);

		List<LabelValue> selectColonne = new ArrayList<LabelValue>();
		int i = 0;
		for (SheetDataCell sii : rowHead.getColomns()) {
			selectColonne.add(new LabelValue(sii.getCell(), i++));
		}
		mv.addObject(AbsoluteString.colonnes, selectColonne);
		

		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ImportData", session));
		return mv;
	}

	@RequestMapping(value = "/data", params = "selectidindex", method = RequestMethod.POST)
	public ModelAndView postColumnandIndex(@ModelAttribute("sheetinfo") SheetInfo sheetinfo, ModelMap map,HttpSession session) {
		ModelAndView mv = new ModelAndView("import/data");
		
		ExcelsHandles exlhandle = new ExcelsHandles();
		sessionManage = new SessionManage(session);
		List<SheetInfo> sheets = sessionManage.getSheets();
		sheetinfo = sheets.get(sheetinfo.getNumber());
		List<SheetDataRow> sheetdatarows = exlhandle.ReadSheet(sheetinfo);
		List<LabelValue> selectvalues = Common.getColumnsfromSheets(sheets);
		SheetDataRow rowHead = sheetdatarows.get(exlhandle.getTitleRow());
		List<SheetDataRow> rowsBody = Common.getRowBody(rowHead, exlhandle, sheetinfo, sheets);

		
		mv.addObject(AbsoluteString.sheetinfo, sheetinfo);
		mv.addObject(AbsoluteString.selectlist, selectvalues);
		mv.addObject(AbsoluteString.tab1, "");
		mv.addObject(AbsoluteString.tab2, "");
		mv.addObject(AbsoluteString.tab3, "in active");		
		mv.addObject(AbsoluteString.sheethead, rowHead);
		mv.addObject(AbsoluteString.sheetdata, rowsBody);
		mv.addObject(AbsoluteString.colonnes, Common.getColumnsAttributes(rowHead));
		
		
		// get String by value
		String fieldMakeID = Common.getLabelByValue(selectvalues,Common.ConvertToInt(sheetinfo.getColonneID(), 0));
		// handle insert here
		SessionManage sessionManage = new SessionManage(session);
		
		// test
		ExcelDataDoc textDataDoc = sessionManage.getExcelDataDoc();
		if (textDataDoc == null){
			mv.addObject(AbsoluteString.noticefail, true);
			mv.addObject(AbsoluteString.message, "Create Project inforamtion before");
		}
		else{
			textDataDoc.setTextDataDocID(sessionManage.getExcelDataDoc().getTextDataDocID());
			int result = uObjectService.add(textDataDoc.getProjectname(),textDataDoc, sheetinfo, rowHead, rowsBody);
			if(result>0)
			{
				mv.addObject(AbsoluteString.noticesuccess, result+" line be inserted");		
			}
			else 
				mv.addObject(AbsoluteString.noticefail, true);
		}
		

		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ImportData", session));
		return mv;
	}
	
	
}
