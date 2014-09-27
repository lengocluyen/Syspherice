package net.syspherice.controller;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.HttpSession;

import net.syspherice.dao.DaoFactory;
import net.syspherice.form.Account;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ExcelMetaData;
import net.syspherice.form.IntroData;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;
import net.syspherice.utils.ExcelsHandles;
import net.syspherice.utils.FileHandle;
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
	SessionManage sessionManage;
	@RequestMapping("/")
	public ModelAndView acceuil(ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("admin/adaccueil");
		SessionManage sessionManage = new SessionManage(session);
		sessionManage.setIsAdminPage(true);
		// set menu display
		mv.addObject(AbsoluteString.pathBar, "Admin Home Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("adacceuil", session));
		return mv;
	}
	@RequestMapping("/introduction")
	public ModelAndView getIntroduction(HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("admin/adintroduction"); 
		String phycialfile = session.getServletContext().getRealPath(Config.INTRODUCTION_FILE_URL);
		File file = new File(phycialfile);
		FileHandle fh = new FileHandle();
		String data = fh.getContents(file);
		IntroData intro = new IntroData();
		intro.setContentData(data);
		mv.addObject("introdata", intro);
	    // set menu display
	     mv.addObject(AbsoluteString.pathBar, "Update Introduction Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("adacceuil", session));
		return mv;
	}
	@RequestMapping(value = "/introduction", method = RequestMethod.POST)
	public ModelAndView postIntroduction(@ModelAttribute("introdata") IntroData introData, ModelMap map,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("admin/adintroduction"); 
		String phycialfile = session.getServletContext().getRealPath(Config.INTRODUCTION_FILE_URL);
		
		File file = new File(phycialfile);
		FileHandle fh = new FileHandle();
		try{
		fh.setContents(file,introData.getContentData());
		}
		catch(Exception e){
			IntroData intro = new IntroData();
			intro.setContentData(fh.getContents(file));
			mv.addObject("introdata", intro);
			mv.addObject(AbsoluteString.noticefail, true);
			// set menu display
		     mv.addObject(AbsoluteString.pathBar, "Update Introduction Page");
			mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("adacceuil", session));
			return mv;
		}
		mv.addObject(AbsoluteString.noticesuccess, true);
		IntroData intro = new IntroData();
		intro.setContentData(fh.getContents(file));
		mv.addObject("introdata", intro);
	    // set menu display
	     mv.addObject(AbsoluteString.pathBar, "Update Introduction Page");
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
