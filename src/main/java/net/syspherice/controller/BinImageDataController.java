package net.syspherice.controller;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.syspherice.form.FolderImage;
import net.syspherice.form.BinImageData;
import net.syspherice.service.BinImageDataService;
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
@RequestMapping("/binimagedata")
@SessionAttributes({"IsAdmin"})
public class BinImageDataController {
	@Autowired
	private BinImageDataService imageDataService;
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
		ModelAndView mv = new ModelAndView("binimagedata/index");
		PagedGenericView<BinImageData> ulist = new PagedGenericView<BinImageData>();

		long count = imageDataService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);

			if (index == null || index < 1)
				ulist.getNav().setCurrentPage(1);
			else
				ulist.getNav().setCurrentPage(index);

			ulist.setEntities(imageDataService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Image Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("image Data", session));
		return mv;
	}

	@RequestMapping("/import")
	public ModelAndView getImportData(HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("binimagedata/import");
		FolderImage folderImage = new FolderImage();
		folderImage.dateImport = Common.getSimpleDateFormat(Calendar
				.getInstance().getTime());
		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.folderimage, folderImage);
		mv.addObject(AbsoluteString.pathBar, "Image Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("image data", session));
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
			int result = imageDataService.addImageDataFromListFile(folderImage,
					session);
			if (result > 0) {
				mv.addObject(AbsoluteString.message, result + " image inserted");
				mv.addObject(AbsoluteString.noticesuccess, true);
			} else
				mv.addObject(AbsoluteString.noticefail, true);
		} else {
			mv.addObject(AbsoluteString.noticefail, true);
		}
		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.folderimage, folderImage);
		mv.addObject(AbsoluteString.pathBar, "Image Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("image data", session));
		return mv;
	}

	@RequestMapping("/delete/{imageID}")
	public ModelAndView delete(@PathVariable("imageID") String imageID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Boolean result = imageDataService.delete(imageID);
		ModelAndView mv = this.Index(session);

		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Image Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("image data", session));
		return mv;

	}

	@RequestMapping("/update/{imageID}")
	public ModelAndView update(@PathVariable("imageID") String imageID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		BinImageData dataOrigine = imageDataService.single(imageID);
		BinImageData dataUpdate = dataOrigine;
		Boolean result = imageDataService.update(dataOrigine, dataUpdate);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Image Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("image data", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("binimagedata/index");
		PagedGenericView<BinImageData> ulist = new PagedGenericView<BinImageData>();
		long count = imageDataService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);
			ulist.getNav().setCurrentPage(1);
			ulist.setEntities(imageDataService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
			mv.addObject(AbsoluteString.ulist, ulist);
		}
		mv.addObject(AbsoluteString.pathBar, "Image Data Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("image data", session));
		return mv;
	}
}
