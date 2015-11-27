package net.syspherice.controller;

import javax.servlet.http.HttpSession;

import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.SessionManage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/demo")
public class DemoController {
	SessionManage sessionManage;
	@RequestMapping("/")
	public ModelAndView acceuil(ModelMap map, HttpSession session) {
		//sessionManage = new SessionManage(session);
		//if(!sessionManage.getIsAdmin()){
			//return new ModelAndView("redirect:/");
		//}
		ModelAndView mv = new ModelAndView("demo/connection");
		//SessionManage sessionManage = new SessionManage(session);
		//sessionManage.setIsAdminPage(true);
		// set menu display
		mv.addObject(AbsoluteString.pathBar, "Admin Home Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("adacceuil", session));
		return mv;
	}
}
