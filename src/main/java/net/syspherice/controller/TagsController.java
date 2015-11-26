package net.syspherice.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import net.syspherice.form.Tags;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.TagsService;
import net.syspherice.service.UnidentifiedObjectService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;
import net.syspherice.validator.TagsValidator;

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
@RequestMapping("/tags")
@SessionAttributes({"IsAdmin"})
public class TagsController {
	@Autowired
	private TagsService tagsService;
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
		ModelAndView mv = new ModelAndView("tags/index");
		PagedGenericView<Tags> ulist = new PagedGenericView<Tags>();
		long count = tagsService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);

			if (index == null || index < 1)
				ulist.getNav().setCurrentPage(1);
			else
				ulist.getNav().setCurrentPage(index);

			ulist.setEntities(tagsService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Tags Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Tags", session));
		return mv;
	}

	@RequestMapping(value = "/create")
	public ModelAndView getCreate(ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("tags/create");
		sessionManage = new SessionManage(session);
		Tags tags = new Tags();

		mv.addObject(AbsoluteString.tags, tags);

		mv.addObject(AbsoluteString.pathBar, "Tags Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Tags", session));
		return mv;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView postCreate(@ModelAttribute("tags") Tags tags,
			ModelMap map, HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("tags/create");
		TagsValidator validator = new TagsValidator();
		validator.validate(tags, bresult);
		if (!bresult.hasErrors()) {
			if (tagsService.singleByName(tags.getName()) != null) {
				mv.addObject(AbsoluteString.noticefail,
						"Existe this Tags in system!");
			} else {

				Boolean result = tagsService.add(tags);
				if (result) {
					mv.addObject(AbsoluteString.noticesuccess, true);
					mv.addObject(AbsoluteString.tags, new Tags());
				} else {
					mv.addObject(AbsoluteString.noticefail, true);

					mv.addObject(AbsoluteString.tags, tags);
				}
			}
		} else {
			mv.addObject(AbsoluteString.noticefail, true);
			mv.addObject(AbsoluteString.tags, tags);
		}
		// set menu display

		mv.addObject(AbsoluteString.pathBar, "Tags Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Tags", session));

		return mv;
	}

	@RequestMapping("/delete/{tagsID}")
	public ModelAndView delete(@PathVariable("tagsID") String tagsID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Boolean result = tagsService.delete(tagsID);
		ModelAndView mv = this.Index(session);

		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Tags Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Tags", session));
		return mv;

	}

	@RequestMapping("/update/{TagsID}")
	public ModelAndView update(@PathVariable("tagsID") String tagsID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Tags tagsOrigine = tagsService.single(tagsID);
		Tags tagsUpdate = tagsOrigine;
		Boolean result = tagsService.update(tagsOrigine, tagsUpdate);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Tags Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Tags", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("tags/index");
		PagedGenericView<Tags> ulist = new PagedGenericView<Tags>();
		long count = tagsService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);

			ulist.getNav().setCurrentPage(1);
			ulist.setEntities(tagsService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Tags Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("Tags", session));
		return mv;
	}
}
