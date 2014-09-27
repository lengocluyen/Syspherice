package net.syspherice.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import net.syspherice.form.SearchType;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.SearchTypeService;
import net.syspherice.service.UnidentifiedObjectService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;
import net.syspherice.validator.SearchTypeValidator;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/searchtype")
@SessionAttributes({"IsAdmin"})
public class SearchTypeController {
	@Autowired
	private SearchTypeService searchTypeService;
	@Autowired
	private UnidentifiedObjectService uObjectService;
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
		ModelAndView mv = new ModelAndView("searchtype/index");
		PagedGenericView<SearchType> ulist = new PagedGenericView<SearchType>();

		long count = searchTypeService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);

			if (index == null || index < 1)
				ulist.getNav().setCurrentPage(1);
			else
				ulist.getNav().setCurrentPage(index);

			ulist.setEntities(searchTypeService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));
		return mv;
	}

	@RequestMapping(value = "/chooseproject")
	public ModelAndView getChooseProject(ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("searchtype/chooseproject");
		mv.addObject(AbsoluteString.searchtype, new SearchType());

		// set menu display
		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));
		return mv;
	}

	@RequestMapping(value = "/chooseproject", method = RequestMethod.POST)
	public ModelAndView postChooseProject(
			@ModelAttribute("searchType") SearchType searchType, ModelMap map,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		sessionManage.setExcelProjectName(searchType.getProjectName());
		return new ModelAndView("redirect:/searchtype/create");
	}

	@RequestMapping(value = "/create")
	public ModelAndView getCreate(ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("searchtype/create");
		sessionManage = new SessionManage(session);
		SearchType searchType = new SearchType();
		searchType.setDateCreate(Common.getSimpleDateFormat(Calendar
				.getInstance().getTime()));
		searchType.setProjectName(sessionManage.getExcelProjectName());

		// set menu display

		mv.addObject(AbsoluteString.searchtype, searchType);
		mv.addObject(AbsoluteString.fieldcollectiondatas, uObjectService
				.getUnidentifiedObjectProperties(sessionManage
						.getExcelProjectName()));
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));
		return mv;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView postCreate(
			@ModelAttribute("searchtype") SearchType searchType, ModelMap map,
			HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("searchtype/create");
		SearchTypeValidator validator = new SearchTypeValidator();
		validator.validate(searchType, bresult);
		if (!bresult.hasErrors()) {
			if (searchTypeService.singleByDisplayNameAndProjectName(
					searchType.getNameDisplay(), searchType.getProjectName()) != null) {
				mv.addObject(AbsoluteString.noticefail,
						"Existe this type of search for this project!");
			} else {

				searchType.setDateCreate(Common.getSimpleDateFormat(Calendar
						.getInstance().getTime()));
				searchType.setUserCreate(sessionManage.getAccount()
						.getUsername());

				Boolean result = searchTypeService.add(searchType);
				if (result) {
					mv.addObject(AbsoluteString.noticesuccess, true);
				} else
					mv.addObject(AbsoluteString.noticefail, true);
			}
		} else
			mv.addObject(AbsoluteString.noticefail, true);
		// set menu display

		mv.addObject(AbsoluteString.searchtype, searchType);
		mv.addObject(AbsoluteString.fieldcollectiondatas, uObjectService
				.getUnidentifiedObjectProperties(sessionManage
						.getExcelProjectName()));
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));

		return mv;
	}

	@RequestMapping("/delete/{searchTypeID}")
	public ModelAndView delete(
			@PathVariable("searchTypeID") String searchTypeID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Boolean result = searchTypeService.delete(searchTypeID);
		ModelAndView mv = this.Index(session);

		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));
		return mv;

	}

	@RequestMapping("/update/{searchTypeID}")
	public ModelAndView update(
			@PathVariable("searchTypeID") String searchTypeID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		SearchType searchTypeOrigine = searchTypeService.single(searchTypeID);
		SearchType searchTypeUpdate = searchTypeOrigine;
		searchTypeUpdate.setState(searchTypeOrigine.getState().compareTo(
				"Active") == 0 ? "Unactive" : "Active");
		Boolean result = searchTypeService.update(searchTypeOrigine,
				searchTypeUpdate);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("searchtype/index");
		PagedGenericView<SearchType> ulist = new PagedGenericView<SearchType>();
		long count = searchTypeService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);
			ulist.getNav().setCurrentPage(1);
			ulist.setEntities(searchTypeService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Search Type Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("SearchType", session));
		return mv;
	}
}
