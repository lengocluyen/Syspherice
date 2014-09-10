package net.syspherice.controller;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.HttpSession;

import net.syspherice.dao.DaoFactory;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ExcelMetaData;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.ExcelsHandles;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.SessionManage;
import net.syspherice.utils.SheetInfo;
import net.syspherice.validator.ExcelDataDocValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBObject;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping("/")
	public ModelAndView acceuil(ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("adaccueil");
		SessionManage sessionManage = new SessionManage(session);
		// set menu display
		sessionManage.setIsAdminPage(true);
		mv.addObject(AbsoluteString.pathBar, "Admin Home Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("adacceuil", session));
		return mv;
	}
	@RequestMapping("/importfinder")
	public ModelAndView FileManagePopUp(HttpSession session){
		ModelAndView mv = new ModelAndView("admin/importfinder");
		return mv;
	}
		@RequestMapping("/filemanage")
	public ModelAndView FileManage(HttpSession session){
		ModelAndView mv = new ModelAndView("filemanage");
		mv.addObject(AbsoluteString.pathBar, "File Manage Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("filemange", session));
		return mv;
	}

}
