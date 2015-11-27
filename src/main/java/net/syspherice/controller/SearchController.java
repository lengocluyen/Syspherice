package net.syspherice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.syspherice.form.BinImageData;
import net.syspherice.form.Contact;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ItemTag;
import net.syspherice.form.SearchInfo;
import net.syspherice.form.SearchType;
import net.syspherice.form.UnidentifiedObject;
import net.syspherice.service.AccountService;
import net.syspherice.service.BinImageDataService;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.ItemtagService;
import net.syspherice.service.SearchTypeService;
import net.syspherice.service.TagsService;
import net.syspherice.service.UnidentifiedObjectService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;
import net.syspherice.utils.ExcelsHandles;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.SessionManage;
import net.syspherice.utils.ZipUtils;
import net.syspherice.validator.ContactValidator;
import net.syspherice.validator.SearchInfoValidator;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.corba.se.spi.activation.Server;

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
	private BinImageDataService imageDataService;
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
		sessionManage = new SessionManage(session);
		ModelAndView mv = new ModelAndView("searchresult");
		SearchInfoValidator validator = new SearchInfoValidator();
		validator.validate(searchInfo, bresult);
		if (!bresult.hasErrors()) {
			// handle result here
			Map<String, List<UnidentifiedObject>> result = uObjectService
					.findByField(searchInfo);
				mv.addObject(AbsoluteString.searchresult, result);
				sessionManage.setSearchInfo(searchInfo);
		// UnidentifiedObject a = new UnidentifiedObject(

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

		List<BinImageData> result = imageDataService.findByPlantID(uObject
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

		List<BinImageData> images = imageDataService.findByPlantID(uObject
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

	@RequestMapping("/exporttoexcel")
	public void exporttoxsl(HttpSession session, HttpServletResponse response) {
		sessionManage = new SessionManage(session);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());
		String fileName = timeStamp + "_"
				+ sessionManage.getAccount().getUsername() + ".xlsx";
		String exportFileName = "/uploads/exports/excels/" +fileName;
		String phycialfile = session.getServletContext().getRealPath(
				exportFileName);
		if (sessionManage.getSearchInfo() != null) {
			Map<String, List<UnidentifiedObject>> result = uObjectService
					.findByField(sessionManage.getSearchInfo());
			if (!result.isEmpty()) {
				ExcelsHandles excelhandles = new ExcelsHandles();
				if (excelhandles.exportToXsl(result, phycialfile)) {
					File fileReponse = new File(phycialfile);
					response.setContentType("application/xlsx");
					response.setContentLength(new Long(fileReponse.length()).intValue());
					response.setHeader("Content-Disposition",
							"attachment; filename="+fileName);

					try {
						FileCopyUtils.copy(new FileInputStream(fileReponse),
								response.getOutputStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}

	
	@RequestMapping("/exporttozip")
	public void exporttozip(HttpSession session, HttpServletResponse response) {
		sessionManage = new SessionManage(session);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(Calendar.getInstance().getTime());
		String zipfileName = timeStamp + "_"
				+ sessionManage.getAccount().getUsername() + ".zip";
		String exportFileName = "/uploads/exports/zips/" +zipfileName;
		
		String phycialZipFile = session.getServletContext().getRealPath(
				exportFileName);
		String folderTemp = zipfileName.substring(0,zipfileName.length()-4);
		
		if (sessionManage.getSearchInfo() != null) {
			Map<String, List<UnidentifiedObject>> result = uObjectService
					.findByField(sessionManage.getSearchInfo());
			if (!result.isEmpty()) {
				
				List<String> plantID = Common.getListImageBySearchResult(result);
				
				List<BinImageData> images = new ArrayList<BinImageData>();
				for(String pID: plantID){
					images.addAll(imageDataService.findByPlantID(pID));
				}
				Common.copyImageToFolderTemp(images, folderTemp,session);
				 ZipUtils appZip = new ZipUtils();
				 appZip.setOutputZipFile(phycialZipFile);
				 appZip.setSourceFolder(folderTemp);
				 appZip.generateFileList(new File(folderTemp));
				if(appZip.zipIt(phycialZipFile)){
					try {
						FileUtils.deleteDirectory(new File(folderTemp));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					File fileReponse = new File(phycialZipFile);
					response.setContentType("application/zip");
					response.setContentLength(new Long(fileReponse.length()).intValue());
					response.setHeader("Content-Disposition",
							"attachment; filename="+zipfileName);

					try {
						FileCopyUtils.copy(new FileInputStream(fileReponse),
								response.getOutputStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}
	
	@RequestMapping("/tags/{tagID}")
	public ModelAndView getTags(@PathVariable("tagID") String tagID,
			HttpSession session) {
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
	public @ResponseBody List<SearchType> getSearchTypesByProjectName(
			@PathVariable("textDataDocID") String textDataDocID) {
		ExcelDataDoc excelDataDoc = excelDataDocService.single(textDataDocID);
		List<SearchType> searchTypes = searchTypeService
				.searchByProjectName(excelDataDoc.getProjectname());
		return searchTypes;
	}
}
