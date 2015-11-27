package net.syspherice.controller;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.syspherice.form.FolderImage;
import net.syspherice.form.BinXmlData;
import net.syspherice.service.BinXmlDataService;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;
import net.syspherice.validator.FolderImageValidator;

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
@RequestMapping("/binxmldata")
@SessionAttributes({"IsAdmin"})
public class BinXmlDataController {
	@Autowired
	private BinXmlDataService xmlDataService;
	@Autowired
	private ExcelDataDocService excelDataDocService;
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
		ModelAndView mv = new ModelAndView("binxmldata/index");
		PagedGenericView<BinXmlData> ulist = new PagedGenericView<BinXmlData>();

		long count = xmlDataService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);

			if (index == null || index < 1)
				ulist.getNav().setCurrentPage(1);
			else
				ulist.getNav().setCurrentPage(index);

			ulist.setEntities(xmlDataService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Xml Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Xml Data", session));
		return mv;
	}

	@RequestMapping("/import")
	public ModelAndView getImportData(HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("binxmldata/import");
		FolderImage folderImage = new FolderImage();
		folderImage.dateImport = Common.getSimpleDateFormat(Calendar
				.getInstance().getTime());
		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.folderimage, folderImage);
		mv.addObject(AbsoluteString.pathBar, "Xml Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Xml data", session));
		return mv;
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public ModelAndView postImportData(
			@ModelAttribute("folderimage") FolderImage folderImage,
			ModelMap map, HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("binimagedata/import");
		ServletContext sc = session.getServletContext();
		String physicalPath = sc.getRealPath("/");
		FolderImageValidator validator = new FolderImageValidator();
		validator.validate(folderImage, bresult);
		if (!bresult.hasErrors()) {
			int result = xmlDataService.addBinXmlDataFromListFile(folderImage,
					session);
			if (result > 0) {
				mv.addObject(AbsoluteString.message, result + " Xml file inserted");
				mv.addObject(AbsoluteString.noticesuccess, true);
			} else
				mv.addObject(AbsoluteString.noticefail, true);
		} else {
			mv.addObject(AbsoluteString.noticefail, true);
		}
		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.folderimage, folderImage);
		mv.addObject(AbsoluteString.pathBar, "Xml Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Xml data", session));
		return mv;
	}

	@RequestMapping("/delete/{xmlID}")
	public ModelAndView delete(@PathVariable("xmlID") String xmlID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Boolean result = xmlDataService.delete(xmlID);
		ModelAndView mv = this.Index(session);

		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Xml Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Xml data", session));
		return mv;

	}

	@RequestMapping("/update/{xmlID}")
	public ModelAndView update(@PathVariable("xmlID") String xmlID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		BinXmlData dataOrigine = xmlDataService.single(xmlID);
		BinXmlData dataUpdate = dataOrigine;
		Boolean result = xmlDataService.update(dataOrigine, dataUpdate);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Xml Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Xml data", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("binxmldata/index");
		PagedGenericView<BinXmlData> ulist = new PagedGenericView<BinXmlData>();
		long count = xmlDataService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);
			ulist.getNav().setCurrentPage(1);
			ulist.setEntities(xmlDataService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
			mv.addObject(AbsoluteString.ulist, ulist);
		}
		mv.addObject(AbsoluteString.pathBar, "Xml Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Xml data", session));
		return mv;
	}
}
