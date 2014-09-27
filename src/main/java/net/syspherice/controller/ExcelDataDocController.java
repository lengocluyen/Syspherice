package net.syspherice.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ExcelMetaData;
import net.syspherice.form.UnidentifiedObject;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.UnidentifiedObjectService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.ExcelsHandles;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;
import net.syspherice.validator.ExcelDataDocValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/exceldatadoc")
@SessionAttributes({"IsAdmin"})
public class ExcelDataDocController {

	@Autowired
	ExcelDataDocService excelDataDocService;
	@Autowired
	UnidentifiedObjectService uObjectService;
	SessionManage sessionManage;

	@RequestMapping("/index")
	public ModelAndView Index(HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		return this.IndexData(session);
	}

	@RequestMapping("/index/{index}")
	public ModelAndView Index(@PathVariable("index") Integer index,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("exceldatadoc/index");
		PagedGenericView<ExcelDataDoc> ulist = new PagedGenericView<ExcelDataDoc>();

		ulist.getNav().setRowCount(excelDataDocService.count());

		if (index == null || index < 1)
			ulist.getNav().setCurrentPage(1);
		else
			ulist.getNav().setCurrentPage(index);

		ulist.setEntities(excelDataDocService.paging(ulist.getNav()
				.getCurrentPage(), ulist.getNav().getPageSize()));

		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Excel Data and Projet Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ExcelDataDoc", session));
		return mv;
	}

	// redirect to import data page
	@RequestMapping(value = "/create", params = "sbimport", method = RequestMethod.POST)
	public ModelAndView importDataPage() {
		return new ModelAndView("redirect:/import/data");
	}

	// redirect to view list excel
	@RequestMapping(value = "/create", params = "sblist", method = RequestMethod.POST)
	public ModelAndView listExcelDocPage(HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("exceldatadoc/index");
		return mv;
	}

	// redirect to import data page
	@RequestMapping(value = "/update", params = "sbimport", method = RequestMethod.POST)
	public ModelAndView updateImportDataPage() {
		return new ModelAndView("redirect:/import/data");
	}

	// redirect to view list excel
	@RequestMapping(value = "/update", params = "sblist", method = RequestMethod.POST)
	public ModelAndView UpdateListExcelDocPage() {
		return new ModelAndView("redirect:/exceldatadoc/index");
	}

	@RequestMapping("/create")
	public ModelAndView getCreate(ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("exceldatadoc/create");

		ExcelDataDoc doc = new ExcelDataDoc();
		doc.setDateImport(Common.getSimpleDateFormat(Calendar.getInstance()
				.getTime()));

		mv.addObject(AbsoluteString.docexcel, doc);

		mv.addObject(AbsoluteString.pathBar, "Create new project Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("adacceuil", session));
		return mv;
	}

	@RequestMapping(value = "/create", params = "sbcreateinfo", method = RequestMethod.POST)
	public ModelAndView postCreate(
			@ModelAttribute("docexcel") ExcelDataDoc excelDoc, ModelMap map,
			HttpSession session, BindingResult result) {
		ModelAndView mv = new ModelAndView("exceldatadoc/create");
		sessionManage = new SessionManage(session);
		ExcelDataDocValidator validator = new ExcelDataDocValidator();
		validator.validate(excelDoc, result);
		if (!result.hasErrors()) {
			ExcelDataDoc temp = excelDataDocService.singleByName(excelDoc.getProjectname()) ;
			if (temp == null) {
				String filename = excelDoc.getFileUrl().substring(12,
						excelDoc.getFileUrl().length());

				String phycialfile = session.getServletContext().getRealPath(
						filename);

				ExcelsHandles exHandle = new ExcelsHandles();
				exHandle.setFilename(phycialfile);

				ExcelMetaData metadata = exHandle.ReadMetadata();
				// insert data in here
				excelDoc.setMetadata(metadata);

				// insert imported user information
				excelDoc.setUserImport(sessionManage.getAccount().getUsername());
				excelDataDocService.add(excelDoc);
				// addSession
				sessionManage.setExcelDataDoc(excelDoc);
				mv.addObject(AbsoluteString.noticesuccess, true);
			}
			else {
				mv.addObject(AbsoluteString.message, "Exist project name!");
				mv.addObject(AbsoluteString.noticefail, true);
			}
		} 
		else
			mv.addObject(AbsoluteString.noticefail, true);

		mv.addObject(AbsoluteString.docexcel, excelDoc);
		mv.addObject(AbsoluteString.pathBar, "Import Data Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("exceldatadoc", session));
		return mv;
	}

	// redirect to view list excel

	@RequestMapping("/delete/{excelDataDocID}")
	public ModelAndView delete(
			@PathVariable("excelDataDocID") String excelDataDocID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Boolean result = excelDataDocService.delete(excelDataDocID);
		uObjectService.deleteByTextDataDocID(excelDataDocID);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		
		mv.addObject(AbsoluteString.pathBar, "Excel Data and Projet Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("ExcelDataDoc", session));
		return mv;

	}

	@RequestMapping("/update/{excelDataDocID}")
	public ModelAndView getUpdate(
			@PathVariable("excelDataDocID") String excelDataDocID,
			ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("exceldatadoc/update");

		ExcelDataDoc doc = excelDataDocService.single(excelDataDocID);
		sessionManage = new SessionManage(session);
		sessionManage.setExcelDataDoc(doc);
		mv.addObject(AbsoluteString.docexcel, doc);
		mv.addObject(AbsoluteString.pathBar,
				"Update information of project Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("exceldatadoc", session));
		return mv;
	}

	@RequestMapping(value = "/update/{excelDataDocID}", method = RequestMethod.POST)
	public ModelAndView postUpdate(
			@PathVariable("excelDataDocID") String excelDataDocID,
			@ModelAttribute("docExcel") ExcelDataDoc excelDoc,
			HttpSession session, BindingResult result) {
		ModelAndView mv = new ModelAndView("exceldatadoc/update");
		ExcelDataDocValidator validator = new ExcelDataDocValidator();
		validator.validate(excelDoc, result);
		if (!result.hasErrors()) {
			ExcelDataDoc temp = excelDataDocService.singleByName(excelDoc
					.getProjectname());
			if (temp == null||temp.getProjectname().compareTo(excelDoc.getProjectname())==0) {
				String filename = excelDoc.getFileUrl().substring(12,
						excelDoc.getFileUrl().length());

				String phycialfile = session.getServletContext().getRealPath(
						filename);

				ExcelsHandles exHandle = new ExcelsHandles();
				exHandle.setFilename(phycialfile);

				ExcelMetaData metadata = exHandle.ReadMetadata();
				// insert data in here
				excelDoc.setMetadata(metadata);

				// insert imported user information
				excelDoc.setUserImport(sessionManage.getAccount().getUsername());
				
				mv.addObject(AbsoluteString.noticesuccess, true);
				ExcelDataDoc excelDataDocOrigine = excelDataDocService
						.single(excelDataDocID);
				ExcelDataDoc excelDataDocUpdate = excelDoc;
				Boolean rs = excelDataDocService.update(excelDataDocOrigine,
						excelDataDocUpdate);
				// addSession
				sessionManage.setExcelDataDoc(excelDataDocUpdate);
				if (rs)
					mv.addObject(AbsoluteString.noticesuccess, true);
				else
					mv.addObject(AbsoluteString.noticefail, true);
			} 				else {
				mv.addObject(AbsoluteString.message, "Exist project name!");
				mv.addObject(AbsoluteString.noticefail, true);

			}
		} else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.docexcel, excelDoc);
		mv.addObject(AbsoluteString.pathBar, "Excel Data and Projet Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("excelDataDoc", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("exceldatadoc/index");
		PagedGenericView<ExcelDataDoc> ulist = new PagedGenericView<ExcelDataDoc>();
		long count = excelDataDocService.count();
		if(count>0){
		ulist.getNav().setRowCount(excelDataDocService.count());
		ulist.getNav().setCurrentPage(1);
		ulist.setEntities(excelDataDocService.paging(ulist.getNav()
				.getCurrentPage(), ulist.getNav().getPageSize()));
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Excel Data and Projet Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("excelDataDoc", session));
		return mv;
		}
		else{
			mv.addObject(AbsoluteString.ulist, null);
			mv.addObject(AbsoluteString.pathBar, "Excel Data and Projet Page");
			mv.addObject(AbsoluteString.adminmenu,
					MenuBuild.getAdminMenu("excelDataDoc", session));
			return mv;
			
		}
	}
}
