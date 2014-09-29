package net.syspherice.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.syspherice.form.Account;
import net.syspherice.form.Annotation;
import net.syspherice.form.AnnotationInfo;
import net.syspherice.form.ImageData;
import net.syspherice.form.ItemTag;
import net.syspherice.form.Login;
import net.syspherice.service.AccountService;
import net.syspherice.service.AnnotationService;
import net.syspherice.service.ImageDataService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.PagedGenericView;
import net.syspherice.utils.SessionManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/annotation")
public class AnnotationController {
	@Autowired
	private AnnotationService annotationService;
	@Autowired
	private ImageDataService imageDataService;
	@Autowired
	private AccountService accountService;
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
	@RequestMapping("/add/{imageID}")
	public ModelAndView getAddNewAnnotation(@PathVariable("imageID") String imageID,
			HttpSession session){
		sessionManage = new SessionManage(session);
		if (sessionManage.getAccount()==null) {
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("annotation/add");
		ImageData imageData = imageDataService.single(imageID);
		
		Annotation annotation = new Annotation();
		annotation.setObjectID(imageID);
		annotation.setUserCreate(sessionManage.getAccount().getUsername());
		mv.addObject(AbsoluteString.annotation, annotation);
		
		//get annotationinfo
		List<Annotation> annotations = annotationService.findbyObjectID(imageID);
		List<AnnotationInfo> annotationInfos = new ArrayList<AnnotationInfo>();
		for(Annotation an:annotations){
			AnnotationInfo ai = new AnnotationInfo();
			Account ac = accountService.single(an.getUserCreate());
			ai.setAvatar(ac.getAvatar());
			ai.setAnnotationid(an.getAnnotationID());
			ai.setFullname(ac.getFirstName() + " " + ac.getLastName());
			ai.setContent(an.getContent());
			ai.setCreatedate(an.getDateCreate());
			if(ac.getUsername().compareTo(sessionManage.getAccount().getUsername())==0)
				ai.setAuthor(true);
			else
				ai.setAuthor(false);
			ai.setUsername(an.getUserCreate());
			annotationInfos.add(ai);
		}
		
		mv.addObject(AbsoluteString.annotationinfo, annotationInfos);
		mv.addObject(AbsoluteString.imageobject, imageData);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("Image Annotation", session));
		
		return mv;
	}
	
	@RequestMapping(value = "/add/{imageID}", method = RequestMethod.POST)
	public ModelAndView postAddNewAnnotation(@ModelAttribute("annotation") Annotation annotation,@PathVariable("imageID") String imageID,
			HttpSession session){
		sessionManage = new SessionManage(session);
			if (sessionManage.getAccount()==null) {
				return new ModelAndView("redirect:/");
			}
		ModelAndView mv = new ModelAndView("annotation/add");
		ImageData imageData = imageDataService.single(imageID);
		
		if(annotation.getContent().length()>0){
			annotation.setUserCreate(sessionManage.getAccount().getUsername());
			annotation.setObjectID(imageID);
			annotation.setDateCreate(Common.getSimpleDateFormat(Calendar
					.getInstance().getTime()));
			annotation.setDateModify(Common.getSimpleDateFormat(Calendar
							.getInstance().getTime()));
			annotation.setState("active");
			if(!annotationService.add(annotation))
			mv.addObject(AbsoluteString.noticefail,null);
			else
			mv.addObject(AbsoluteString.noticesuccess, true);
		}
		else{
			mv.addObject(AbsoluteString.noticefail,true);
			mv.addObject(AbsoluteString.noticesuccess, null);
		}
		
		mv.addObject(AbsoluteString.annotation, annotation);
		//mv.addObject(AbsoluteString.tags, tagService.all());
		//mv.addObject(AbsoluteString.itemtags, new ItemTag());
		//mv.addObject(AbsoluteString.uobject, uObject);
		//mv.addObject(AbsoluteString.imagedata, result);
		
		
		//get annotationinfo
		List<Annotation> annotations = annotationService.findbyObjectID(imageID);
		List<AnnotationInfo> annotationInfos = new ArrayList<AnnotationInfo>();
		for(Annotation an:annotations){
			AnnotationInfo ai = new AnnotationInfo();
			Account ac = accountService.single(an.getUserCreate());
			ai.setAvatar(ac.getAvatar());
			ai.setAnnotationid(an.getAnnotationID());
			ai.setFullname(ac.getFirstName() + " " + ac.getLastName());
			ai.setContent(an.getContent());
			ai.setCreatedate(an.getDateCreate());
			if(ac.getUsername().compareTo(sessionManage.getAccount().getUsername())==0)
				ai.setAuthor(true);
			else
				ai.setAuthor(false);
			ai.setUsername(an.getUserCreate());
			annotationInfos.add(ai);
		}
		
		mv.addObject(AbsoluteString.annotationinfo, annotationInfos);
		mv.addObject(AbsoluteString.imageobject, imageData);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("Image Annotation", session));
		return mv;
	}
	
	@RequestMapping("/del/{annotationID}")
	public ModelAndView postDeleteNewAnnotation(@PathVariable("annotationID") String annotationID,
			HttpSession session){
		sessionManage = new SessionManage(session);
			if (sessionManage.getAccount()==null) {
				return new ModelAndView("redirect:/");
			}
		ModelAndView mv = new ModelAndView("annotation/del");
		
		Annotation annotation = annotationService.single(annotationID);
		mv.addObject(AbsoluteString.annotation, new Annotation());
		String imageID = annotation.getObjectID();
		
		if(!annotationService.delete(annotationID))
			mv.addObject(AbsoluteString.noticefail1,null);
			else
			mv.addObject(AbsoluteString.noticesuccess1, true);
		//get annotationinfo
		List<Annotation> annotations = annotationService.findbyObjectID(imageID);
		List<AnnotationInfo> annotationInfos = new ArrayList<AnnotationInfo>();
		for(Annotation an:annotations){
			AnnotationInfo ai = new AnnotationInfo();
			Account ac = accountService.single(an.getUserCreate());
			ai.setAvatar(ac.getAvatar());
			ai.setAnnotationid(an.getAnnotationID());
			ai.setFullname(ac.getFirstName() + " " + ac.getLastName());
			ai.setContent(an.getContent());
			ai.setCreatedate(an.getDateCreate());
			if(ac.getUsername().compareTo(sessionManage.getAccount().getUsername())==0)
				ai.setAuthor(true);
			else
				ai.setAuthor(false);
			ai.setUsername(an.getUserCreate());
			annotationInfos.add(ai);
		}
		
		mv.addObject(AbsoluteString.annotationinfo, annotationInfos);
		mv.addObject(AbsoluteString.imageobject, imageDataService.single(imageID));
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("Image Annotation", session));
		
		
		return mv;
	}
	
	
	@RequestMapping("/delete/{annotationID}")
	public ModelAndView delete(
			@PathVariable("annotationID") String annotationID,
			HttpSession session) {
		sessionManage = new SessionManage(session);
		if (sessionManage.getIsAdmin()) {
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
