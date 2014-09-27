package net.syspherice.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.syspherice.form.Account;
import net.syspherice.form.Login;
import net.syspherice.form.SearchInfo;
import net.syspherice.service.AccountService;
import net.syspherice.service.ContactService;
import net.syspherice.service.ExcelDataDocService;
import net.syspherice.service.SearchTypeService;
import net.syspherice.service.TagsService;
import net.syspherice.utils.AbsoluteString;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;
import net.syspherice.utils.FileHandle;
import net.syspherice.utils.MenuBuild;
import net.syspherice.utils.SessionManage;
import net.syspherice.validator.AccountValidator;
import net.syspherice.validator.ContactValidator;
import net.syspherice.validator.LoginValidator;
import net.syspherice.form.Contact;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Resource
public class AccueilController {
	@Autowired
	private ContactService contactService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ExcelDataDocService excelDataDocService;
	@Autowired
	private SearchTypeService searchTypeService;
	@Autowired
	private TagsService tagsService;

	@RequestMapping("/")
	public ModelAndView accueil(ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("accueil");
		SessionManage sessionManage = new SessionManage(session);
		sessionManage.setIsAdminPage(false);
		// set menu display
		mv.addObject(AbsoluteString.loginreload, null);

		mv.addObject(AbsoluteString.projects, excelDataDocService.all());
		mv.addObject(AbsoluteString.searchinfo, new SearchInfo());
		
		mv.addObject(AbsoluteString.menu, MenuBuild.getMenu("home", session));
		mv.addObject(AbsoluteString.tags, tagsService.all());
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("login") Login login,
			ModelMap map, HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("accueil");
		LoginValidator validator = new LoginValidator();
		validator.validate(login, bresult);
		SessionManage sessionManage = new SessionManage(session);
		if (!bresult.hasErrors()) {
			Account account = accountService.login(login.getUsername(),
					login.getPassword());
			if (account != null) {
				if (account.getState() != "inactive") {
					Account origine = account;
					account.setLastLogin(Common.getSimpleDateFormat(Calendar
							.getInstance().getTime()));
					accountService.update(origine, account);
					sessionManage.setAccount(account);
					sessionManage.setIsLogin(true);
					if (Common.checkStringInArray(Config.ADMIN_LIST,
							account.getUsername())||account.getRole().compareTo("admin")==0) {
						sessionManage.setIsAdmin(true);
						return new ModelAndView("redirect:admin/");
					} else {
						sessionManage.setIsAdmin(false);
						return new ModelAndView("redirect:/");
					}
				} else {
					mv.addObject(AbsoluteString.noticefail, "Locked Account!");
				}
			} else {
				mv.addObject(AbsoluteString.noticefail,
						"Username or Password incorrect!");
				mv.addObject(AbsoluteString.loginreload, true);
			}
		} else {
			mv.addObject(AbsoluteString.noticefail,
					"Missing required informations!");
			mv.addObject(AbsoluteString.loginreload, true);
		}
		// set menu display
		mv.addObject(AbsoluteString.menu, MenuBuild.getMenu("home", session));
		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView Logout(HttpSession session) {
		ModelAndView mv = new ModelAndView("accueil");
		SessionManage sessionManage = new SessionManage(session);
		sessionManage.setAccount(null);
		sessionManage.setIsLogin(false);
		sessionManage.setIsAdmin(false);
		sessionManage.setIsAdminPage(false);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/introduction")
	public ModelAndView Introduction(ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("introduction");
		String phycialfile = session.getServletContext().getRealPath(Config.INTRODUCTION_FILE_URL);
		File file = new File(phycialfile);
		FileHandle fh = new FileHandle();
		String data = fh.getContents(file);
		mv.addObject("data", data);
		// set menu display
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("introduction", session));
		return mv;
	}

	// update account
	@RequestMapping(value = "/registration/{username}")
	public ModelAndView UpdateRegistration(
			@PathVariable("username") String username, ModelMap map,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("registration");
		mv.addObject("account", accountService.single(username));
		// set menu display
		mv.addObject(AbsoluteString.isactionupdate, true);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("registration", session));
		return mv;
	}

	@RequestMapping(value = "/accountdetail/update/{username}", method = RequestMethod.POST)
	public ModelAndView RegistrationUpdatePost(
			@PathVariable("username") String username,
			@RequestParam("fileAvatar") MultipartFile file,
			@ModelAttribute("account") Account account, ModelMap map,
			HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("registration");
		AccountValidator validator = new AccountValidator();
		validator.validate(account, bresult);
		if (!bresult.hasErrors()) {
			String ex = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!file.isEmpty()) {
				if (ex.toLowerCase().compareTo("jpg") == 0
						|| ex.toLowerCase().compareTo("png") == 0
						|| ex.toLowerCase().compareTo("gif") == 0) {

					try {

						byte[] bytes = file.getBytes();
						// creating the directory to store file
						File dir = new File(Config.ROOT_PATH_AVATAR);
						if (!dir.exists())
							dir.mkdirs();
						// create the file on server
						File serverFile = new File(dir.getAbsoluteFile()
								+ File.separator + account.getUsername() + "."
								+ ex);
						BufferedOutputStream stream = new BufferedOutputStream(
								new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();
						account.setAvatar(Config.URL_AVATAR
								+ account.getUsername() + "." + ex);
					} catch (Exception e) {
					}

				} else {
					mv.addObject(AbsoluteString.noticefail,
							"Accept only files images: *.png, *.jpg, *.gif!");
					mv.addObject(AbsoluteString.isactionupdate, false);
					mv.addObject(AbsoluteString.menu,
							MenuBuild.getMenu("registration", session));
					return mv;
				}

			} else {
				// image default
				account.setAvatar(Config.URL_AVATAR_DEFAULT);
			}
			Account origine = account;

			account.setLastLogin(Common.getSimpleDateFormat(Calendar
					.getInstance().getTime()));
			Boolean result = accountService.update(origine, account);
			if (result) {
				mv.addObject(AbsoluteString.noticesuccess, " ");
			} else
				mv.addObject(AbsoluteString.noticefail,
						"Registration is not succesful!");
		}
		// set menu display
		mv.addObject(AbsoluteString.isactionupdate, true);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("registration", session));
		return mv;
	}

	// New registration
	@RequestMapping(value = "/registration")
	public ModelAndView Registration(ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("registration");
		mv.addObject("account", new Account());

		// set menu display
		mv.addObject(AbsoluteString.isactionupdate, false);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("registration", session));
		return mv;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView RegistrationPost(
			@RequestParam("fileAvatar") MultipartFile file,
			@ModelAttribute("account") Account account, ModelMap map,
			HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("registration");
		AccountValidator validator = new AccountValidator();
		validator.validate(account, bresult);
		if (!bresult.hasErrors()) {
			String ex = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!file.isEmpty()) {
				if (ex.toLowerCase().compareTo("jpg") == 0
						|| ex.toLowerCase().compareTo("png") == 0
						|| ex.toLowerCase().compareTo("gif") == 0) {
					try {

						byte[] bytes = file.getBytes();
						// creating the directory to store file
						File dir = new File(Config.ROOT_PATH_AVATAR);
						if (!dir.exists())
							dir.mkdirs();
						// create the file on server
						File serverFile = new File(dir.getAbsoluteFile()
								+ File.separator + account.getUsername() + "."
								+ ex);
						BufferedOutputStream stream = new BufferedOutputStream(
								new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();
						account.setAvatar(Config.URL_AVATAR
								+ account.getUsername() + "." + ex);
					} catch (Exception e) {
						account.setAvatar(Config.URL_AVATAR_DEFAULT);
					}
				} else {
					mv.addObject(AbsoluteString.noticefail,
							"Accept only files images: *.png, *.jpg, *.gif!");
					mv.addObject(AbsoluteString.isactionupdate, false);
					mv.addObject(AbsoluteString.menu,
							MenuBuild.getMenu("registration", session));
					return mv;
				}

			} else {
				// image default
				account.setAvatar(Config.URL_AVATAR_DEFAULT);
			}
			if (accountService.single(account.getUsername()) != null) {
				mv.addObject(AbsoluteString.noticefail, "Existe username!");
			} else {
				account.setDateCreate(Common.getSimpleDateFormat(Calendar
						.getInstance().getTime()));
				account.setState("active");
				account.setRole("user");
				Boolean result = accountService.add(account);
				if (result) {
					mv.addObject(AbsoluteString.noticesuccess, true);
				} else
					mv.addObject(AbsoluteString.noticefail, true);
			}
		}
		// set menu display
		mv.addObject(AbsoluteString.isactionupdate, false);
		mv.addObject(AbsoluteString.menu,
				MenuBuild.getMenu("registration", session));
		return mv;
	}

	// Non handle
	@RequestMapping(value = "/contactpage")
	public ModelAndView Contact(ModelMap map, HttpSession session) {
		ModelAndView mv = new ModelAndView("contactpage");

		// set value display for view
		mv.addObject(AbsoluteString.contact, new Contact());
		mv.addObject(AbsoluteString.pathBar, "Contact");
		// set menu display
		mv.addObject(AbsoluteString.menu, MenuBuild.getMenu("contact", session));

		return mv;
	}

	@RequestMapping(value = "/contactpage", method = RequestMethod.POST)
	public ModelAndView ContactPost(@ModelAttribute("contact") Contact contact,
			ModelMap map, HttpSession session, BindingResult bresult) {
		ModelAndView mv = new ModelAndView("contactpage");
		ContactValidator validator = new ContactValidator();
		validator.validate(contact, bresult);
		if (!bresult.hasErrors()) {
			// add new Contact
			Boolean result = contactService.add(contact);
			if (result)
				mv.addObject(AbsoluteString.noticesuccess, true);
			else
				mv.addObject(AbsoluteString.noticefail, true);
		} else
			mv.addObject(AbsoluteString.noticefail, true);

		// set value display for view
		mv.addObject(AbsoluteString.pathBar, "Contact");
		mv.addObject(AbsoluteString.contact, contact);
		// set menu display
		mv.addObject(AbsoluteString.menu, MenuBuild.getMenu("contact", session));
		return mv;
	}
}
