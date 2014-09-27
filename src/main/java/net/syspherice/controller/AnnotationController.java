package net.syspherice.controller;

import javax.servlet.http.HttpSession;

import net.syspherice.form.Account;
import net.syspherice.form.Annotation;
import net.syspherice.service.AccountService;
import net.syspherice.service.AnnotationService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/annotation")
public class AnnotationController {
	@Autowired
	private AnnotationService annotationService;
	private SessionManage sessionManage;

	@RequestMapping("/index")
	public ModelAndView Index(HttpSession session) {
		sessionManage = new SessionManage(session);
		if (!sessionManage.getIsAdmin()) {
			return new ModelAndView("redirect:/");
		}
		return this.IndexData(session);
	}

	@RequestMapping("/index/{index}")
	public ModelAndView Index(@PathVariable("index") Integer index,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if (!sessionManage.getIsAdmin()) {
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("annotation/index");
		PagedGenericView<Annotation> ulist = new PagedGenericView<Annotation>();
		if(annotationService.count()>0){
		ulist.getNav().setRowCount(annotationService.count());

		if (index == null || index < 1)
			ulist.getNav().setCurrentPage(1);
		else
			ulist.getNav().setCurrentPage(index);

		ulist.setEntities(annotationService.paging(ulist.getNav()
				.getCurrentPage(), ulist.getNav().getPageSize()));

		mv.addObject(AbsoluteString.ulist, ulist);
		}
		mv.addObject(AbsoluteString.pathBar, "Annotation Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("annotation", session));
		return mv;
	}

	@RequestMapping("/delete/{annotationID}")
	public ModelAndView delete(
			@PathVariable("annotationID") String annotationID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if (!sessionManage.getIsAdmin()) {
			return new ModelAndView("redirect:/");
		}
		Boolean result = annotationService.delete(annotationID);
		ModelAndView mv = this.Index(session);

		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Annotation Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("annotation", session));
		return mv;

	}

	@RequestMapping("/update/{annotationID}")
	public ModelAndView update(
			@PathVariable("annotationID") String annotationID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if (!sessionManage.getIsAdmin()) {
			return new ModelAndView("redirect:/");
		}
		Annotation annotationOrigine = annotationService.single(annotationID);
		Annotation annotationUpdate = annotationOrigine;
		annotationUpdate.setState(annotationOrigine.getState().compareTo(
				"active") == 0 ? "inactive" : "active");
		Boolean result = annotationService.update(annotationOrigine,
				annotationUpdate);
		ModelAndView mv = this.Index(session);
		if (result)
			mv.addObject(AbsoluteString.noticesuccess, true);
		else
			mv.addObject(AbsoluteString.noticefail, true);
		mv.addObject(AbsoluteString.pathBar, "Annotation Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("annotation", session));
		return mv;
	}

	public ModelAndView IndexData(HttpSession session) {
		ModelAndView mv = new ModelAndView("annotation/index");
		PagedGenericView<Annotation> ulist = new PagedGenericView<Annotation>();
		if (annotationService.count() > 0) {
			ulist.getNav().setRowCount(annotationService.count());
			ulist.getNav().setCurrentPage(1);
			ulist.setEntities(annotationService.paging(ulist.getNav()
					.getCurrentPage(), ulist.getNav().getPageSize()));
			mv.addObject(AbsoluteString.ulist, ulist);
		}
		mv.addObject(AbsoluteString.pathBar, "Annotation Manage Page");
		mv.addObject(AbsoluteString.adminmenu,
				MenuBuild.getAdminMenu("annotation", session));
		return mv;
	}

	// for user page
	@RequestMapping(value = "/detail/{annotationID}")
	public ModelAndView Detail(
			@PathVariable("annotationID") String annotationID, ModelMap map,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if (!sessionManage.getIsAdmin()) {
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("annotation/detail");
		Annotation annotation = annotationService.single(annotationID);

		mv.addObject(AbsoluteString.annotation, annotation);
		// set menu display
		mv.addObject(AbsoluteString.pathBar, "Detail Annotation Page");
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("annotation", session));
		return mv;
	}
}
