package net.syspherice.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.syspherice.form.Contact;
import net.syspherice.service.ContactService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contact")
public class ContactController {
	@Autowired
	private ContactService contactService;

	@RequestMapping("/index")
	public ModelAndView Index(HttpSession session) {
		return this.IndexData(session);
	}

	@RequestMapping("/index/{index}")
	public ModelAndView Index(@PathVariable("index") Integer index,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("contact/index");
		PagedGenericView<Contact> ulist = new PagedGenericView<Contact>();

		ulist.getNav().setRowCount(contactService.count());

		if (index == null || index < 1)
			ulist.getNav().setCurrentPage(1);
		else
			ulist.getNav().setCurrentPage(index);

		ulist.setEntities(contactService.paging(
				ulist.getNav().getCurrentPage(), ulist.getNav().getPageSize()));

		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Contact Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("contact", session));
		return mv;
	}

	@RequestMapping("/delete/{contactID}")
	public ModelAndView delete(@PathVariable("contactID") String contactID,
			HttpSession session) {

		Boolean result = contactService.delete(contactID);
		ModelAndView mv = this.Index(session);

		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Contact Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("contact", session));
		return mv;

	}

	@RequestMapping("/update/{contactID}")
	public ModelAndView update(@PathVariable("contactID") String contactID,
			HttpSession session) {
		Contact contactOrigine = contactService.single(contactID);
		Contact contactUpdate = contactOrigine;
		contactUpdate.setIsRead(contactOrigine.getIsRead() == true ? false
				: true);
		Boolean result = contactService.update(contactOrigine, contactUpdate);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Contact Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("contact", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("contact/index");
		PagedGenericView<Contact> ulist = new PagedGenericView<Contact>();
		long count = contactService.count();
		if (count > 0) {
			ulist.getNav().setRowCount(count);
			ulist.getNav().setCurrentPage(1);
			ulist.setEntities(contactService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
		}
		mv.addObject(AbsoluteString.ulist, ulist);
		mv.addObject(AbsoluteString.pathBar, "Contact Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("contact", session));
		return mv;
	}
}
