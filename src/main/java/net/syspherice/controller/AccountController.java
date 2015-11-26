package net.syspherice.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.syspherice.enumeration.AccountEnum;
import net.syspherice.form.Account;
import net.syspherice.service.AccountService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController{
	@Autowired
	private AccountService accountService;
	private SessionManage sessionManage;
	@RequestMapping("/index")
	public ModelAndView Index(HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		return this.IndexData(session);
	}
	@RequestMapping("/index/{index}")
	public ModelAndView Index(@PathVariable("index") Integer index, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("account/index");
		PagedGenericView<Account> ulist = new PagedGenericView<Account>();

		ulist.getNav().setRowCount(accountService.count());

		if (index == null || index < 1)
			ulist.getNav().setCurrentPage(1);
		else
			ulist.getNav().setCurrentPage(index);

		ulist.setEntities(accountService.paging(ulist.getNav().getCurrentPage(),
				ulist.getNav().getPageSize()));

		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Account Manage Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("account", session));
		return mv;
	}

	@RequestMapping("/delete/{username}")
	public ModelAndView delete(@PathVariable("username") String username, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Boolean result = accountService.delete(username);
		ModelAndView mv = this.Index(session);
		
		if(result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Account Manage Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("account", session));
		return mv;
		
	}
	@RequestMapping("/update/{username}")
	public ModelAndView update(@PathVariable("username") String username, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Account accountOrigine = accountService.single(username);
		Account accountUpdate = accountOrigine;
		accountUpdate.setState(accountOrigine.getState().compareTo("active")==0?"inactive":"active");
		Boolean result = accountService.update(accountOrigine, accountUpdate);
		ModelAndView mv = this.Index(session);
		if(result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Account Manage Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("account", session));
		return mv;
	}
	@RequestMapping("/updaterole/{username}")
	public ModelAndView updaterole(@PathVariable("username") String username, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		Account accountOrigine = accountService.single(username);
		Account accountUpdate = accountOrigine;
		accountUpdate.setRole(accountOrigine.getRole().compareTo("user")==0?"admin":"user");
		Boolean result = accountService.update(accountOrigine, accountUpdate);
		ModelAndView mv = this.Index(session);
		if(result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Account Manage Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("account", session));
		return mv;
	}
	public ModelAndView IndexData(HttpSession session){
		ModelAndView mv = new ModelAndView("account/index");
		PagedGenericView<Account> ulist = new PagedGenericView<Account>();
		ulist.getNav().setRowCount(accountService.count());
		ulist.getNav().setCurrentPage(1);
		ulist.setEntities(accountService.paging(ulist.getNav()
				.getCurrentPage(), ulist.getNav().getPageSize()));
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Account Manage Page");
		mv.addObject(AbsoluteString.adminmenu, MenuBuild.getAdminMenu("account", session));
		return mv;
	}
	//for user page
	@RequestMapping(value = "/detail/{username}")
	public ModelAndView Detail(@PathVariable("username") String username, ModelMap map, HttpSession session) {
		sessionManage = new SessionManage(session);
		if(!sessionManage.getIsAdmin()){
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("account/detail");
		Account account = accountService.single(username);
		
		mv.addObject(AbsoluteString.account,account);
		// set menu display
		mv.addObject(AbsoluteString.pathBar,"Detail Account Page");
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("registration", session));
		return mv;
	}
}
