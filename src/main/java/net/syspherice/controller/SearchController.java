package net.syspherice.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.syspherice.form.Contact;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ImageData;
import net.syspherice.form.ItemTag;
import net.syspherice.form.SearchInfo;
import net.syspherice.form.SearchType;
import net.syspherice.form.UnidentifiedObject;
import net.syspherice.service.AccountService;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.ImageDataService;
import net.syspherice.service.ItemtagService;
import net.syspherice.service.SearchTypeService;
import net.syspherice.service.TagsService;
import net.syspherice.service.UnidentifiedObjectService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.SessionManage;
import net.syspherice.validator.ContactValidator;
import net.syspherice.validator.SearchInfoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private ExcelDataDocService excelDataDocService;
	@Autowired
	private SearchTypeService searchTypeService;
	@Autowired
	private UnidentifiedObjectService uObjectService;
	@Autowired
	private ImageDataService imageDataService;
	@Autowired
	private ItemtagService itemTagService;
	@Autowired
	private TagsService tagService;
	SessionManage sessionManage;

	@RequestMapping("/")
	public ModelAndView getSearchPage(HttpSession session) {
		ModelAndView mv = new ModelAndView("searchpage");
		// mv.addObject(AbsoluteString.pathBar, "Search Page");

		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.searchinfo, new SearchInfo());
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("search page", session));
		return mv;
	}

	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public ModelAndView postSearchPage(
			@ModelAttribute("searchinfo") SearchInfo searchInfo, ModelMap map,
			HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("searchresult");
		SearchInfoValidator validator = new SearchInfoValidator();
		validator.validate(searchInfo, bresult);
		if (!bresult.hasErrors()) {
			// handle result here
			Map<String, List<UnidentifiedObject>> result = uObjectService
					.findByField(searchInfo);
			mv.addObject(AbsoluteString.searchresult, result);
			// UnidentifiedObject a = new UnidentifiedObject();

		}
		// set value display for view
		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.searchinfo, searchInfo);

		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("search page", session));
		return mv;
	}

	@RequestMapping("/viewdetail")
	public ModelAndView viewdetail(
			@RequestParam("collectionid") String collectionid,
			@RequestParam("collectionname") String collectionname,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("viewdetail");
		sessionManage = new SessionManage(session);
		sessionManage.setCollectionID(collectionid);
		sessionManage.setCollectionName(collectionname);
		Map<String, List<UnidentifiedObject>> uObject = uObjectService.single(
				collectionname, collectionid);

		List<ImageData> result = imageDataService.findByPlantID(uObject
				.get(collectionname).get(0).getData().get("ID").toString());

		mv.addObject(AbsoluteString.tags, tagService.all());
		mv.addObject(AbsoluteString.itemtags, new ItemTag());
		mv.addObject(AbsoluteString.uobject, uObject);
		mv.addObject(AbsoluteString.imagedata, result);
		mv.addObject(AbsoluteString.active2, "active");
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("search page", session));
		return mv;
	}

	@RequestMapping(value = "/additemtag", method = RequestMethod.POST)
	public ModelAndView postAddItemTag(
			@ModelAttribute("itemtags") ItemTag itemTags, ModelMap map,
			HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("viewdetail");
		sessionManage = new SessionManage(session);
		Map<String, List<UnidentifiedObject>> uObject = uObjectService.single(
				sessionManage.getCollectionName(),
				sessionManage.getCollectionID());

		List<ImageData> images = imageDataService.findByPlantID(uObject
				.get(sessionManage.getCollectionName()).get(0).getData()
				.get("ID").toString());

		if (itemTags.getTagID() != null && itemTags.getTagID() != "-1") {
			itemTags.setAccountID(sessionManage.getAccount().getUsername());
			itemTags.setCollectionName(sessionManage.getCollectionName());
			itemTags.setItemID(sessionManage.getCollectionID());

			if (itemTagService.singleByItem(itemTags) != null) {
				mv.addObject(AbsoluteString.noticefail,
						"Exist a tag for this item!");
			} else {

				Boolean result = itemTagService.add(itemTags);

				if (result) {
					mv.addObject(AbsoluteString.noticesuccess,
							"Tagging success!");
				} else {
					mv.addObject(AbsoluteString.noticefail, "Tagging failed!");
				}
			}
		} else {
			mv.addObject(AbsoluteString.noticefail, "Tagging failed!");
		}
		// set value display for view
		mv.addObject(AbsoluteString.itemtags, itemTags);
		mv.addObject(AbsoluteString.tags, tagService.all());
		mv.addObject(AbsoluteString.uobject, uObject);
		mv.addObject(AbsoluteString.imagedata, images);
		mv.addObject(AbsoluteString.active3, "active");
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("search page", session));
		return mv;
	}
	@RequestMapping("/tags/{tagID}")
	public ModelAndView getTags(@PathVariable("tagID") String tagID,HttpSession session) {
		ModelAndView mv = new ModelAndView("tagsview");
		sessionManage = new SessionManage(session);

		List<ItemTag> itemTags = itemTagService.findByTagID(tagID);
		
		Map<String, List<UnidentifiedObject>> result = uObjectService
				.findByItemTags(itemTags);
		mv.addObject(AbsoluteString.searchresult, result);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("search page", session));
		return mv;
	}
	// for ajax
	@RequestMapping(value = "/searchtypes/{textDataDocID}", method = RequestMethod.GET)
	public @ResponseBody
	List<SearchType> getSearchTypesByProjectName(
			@PathVariable("textDataDocID") String textDataDocID) {
		ExcelDataDoc excelDataDoc = excelDataDocService.single(textDataDocID);
		List<SearchType> searchTypes = searchTypeService
				.searchByProjectName(excelDataDoc.getProjectname());
		return searchTypes;
	}
}
