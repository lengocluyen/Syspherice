package net.syspherice.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class MenuBuild {
	
	public static List<MenuItem> getMenu(String actionName, HttpSession session){
		SessionManage sessionManage = new SessionManage(session);
		if(sessionManage.getAccount()!=null){
			return MenuBuild.ApresLogin(actionName,sessionManage.getAccount().getUsername());
		}
		else
		{
			return MenuBuild.AvantLogin(actionName);
		}
	}
	
	public static List<MenuItemAdmin> getAdminMenu(String actioName, HttpSession session){
		SessionManage sessionManage = new SessionManage(session);
		if(sessionManage.getIsAdmin()){
			return MenuBuild.AdminLogin(actioName);
		}
		return null;
	}
	
	public static List<MenuItem> AvantLogin(String nameActive) {
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		list.add(new MenuItem("/Syspherice/", "Home", ""));
		list.add(new MenuItem("/Syspherice/introduction", "Introduction", ""));
		list.add(new MenuItem("/Syspherice/search/", "Search Page", ""));
		list.add(new MenuItem("/Syspherice/contactpage", "Contact", ""));
		//list.add(new MenuItem("/Syspherice/aboutus", "About us", ""));
		for (MenuItem menuItem : list) {
			if (menuItem.name.toLowerCase().compareTo(nameActive.toLowerCase()) == 0)
				menuItem.setIsCurrent("active");
		}
		return list;
	}

	public static List<MenuItem> ApresLogin(String nameActive, String username) {
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		list.add(new MenuItem("/Syspherice/", "Home", ""));
		list.add(new MenuItem("/Syspherice/introduction", "Introduction", ""));
		list.add(new MenuItem("/Syspherice/search/", "Search Page", ""));
		list.add(new MenuItem("/Syspherice/contactpage", "Contact", ""));
		list.add(new MenuItem("/Syspherice/account/detail/"+username, "Account", ""));
		//list.add(new MenuItem("/Syspherice/aboutus", "About us", ""));
		for (MenuItem menuItem : list) {
			if (menuItem.name.toLowerCase().compareTo(nameActive.toLowerCase()) == 0)
				menuItem.setIsCurrent("active");
		}
		return list;
	}
	public static List<MenuItemAdmin> AdminLogin(String nameActive){
		ArrayList<MenuItemAdmin> list = new ArrayList<MenuItemAdmin>();
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/admin/", "Home", ""),null));
		
		
		
		ArrayList<MenuItem> importData = new ArrayList<MenuItem>();
		importData.add(new MenuItem("/Syspherice/exceldatadoc/create", "Create import data information ", ""));
		importData.add(new MenuItem("/Syspherice/exceldatadoc/index", "Project list", ""));
		//importData.add(new MenuItem("/Syspherice/import/data", "Data Import", ""));
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/import/", "Import", ""),importData));
		
		ArrayList<MenuItem> searchtype = new ArrayList<MenuItem>();
		searchtype.add(new MenuItem("/Syspherice/searchtype/chooseproject", "Create a type search", ""));
		searchtype.add(new MenuItem("/Syspherice/searchtype/index", "Type search", ""));
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/searchtype/index", "Type search", ""),searchtype));
		
		ArrayList<MenuItem> imagedata = new ArrayList<MenuItem>();
		imagedata.add(new MenuItem("/Syspherice/imagedata/import", "Import image data", ""));
		imagedata.add(new MenuItem("/Syspherice/imagedata/index", "Image data list", ""));
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/imagedata/index", "Image data", ""),imagedata));
		
		ArrayList<MenuItem> tagmenu = new ArrayList<MenuItem>();
		tagmenu.add(new MenuItem("/Syspherice/tags/index", "Tags list", ""));
		tagmenu.add(new MenuItem("/Syspherice/tags/create", "Insert a Tag", ""));
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/tags/index", "Tags", ""),tagmenu));
		
		
		ArrayList<MenuItem> contact = new ArrayList<MenuItem>();
		contact.add(new MenuItem("/Syspherice/contact/index", "Contact list", ""));
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/contact/index", "Contact", ""),contact));
		
		ArrayList<MenuItem> account = new ArrayList<MenuItem>();
		account.add(new MenuItem("/Syspherice/account/index", "Account list", ""));
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/account/index", "Account", ""),account));
		
		list.add(new MenuItemAdmin(new MenuItem("/Syspherice/admin/filemanage", "File Manage", ""),null));
		
		for (MenuItemAdmin menuItem : list) {
			if (menuItem.getItem().getName().toLowerCase().compareTo(nameActive.toLowerCase()) == 0)
				menuItem.getItem().setIsCurrent("active");
		}
		return list;
	}
}
