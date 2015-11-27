package net.syspherice.utils;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.shiro.session.Session;

public class Config {

	public static final double API=2.0;
	public static final String UPL_MAX_SIZE;
	public static final String SEPARATOR="/";
	public static final String ROOT_PATH;
	public static final String ROOT_NAME="Dossiers";
	public static final String DEFAULT_VOLUMNID="l1_";
	public static final String TMB_URL;
	public static final String URL;
	public static final String ROOT_PATH_AVATAR;
	public static final String URL_AVATAR;
	public static final String ADMIN_LIST;
	public static final int PAGE_SIZE;
	public static final ExtensionsFilter IMAGE_FILTER;
	public static final String URL_AVATAR_DEFAULT;
	public static final String INTRODUCTION_FILE_URL;
	public static final String TEMPS_PHYSICAL_PATH_IMAGE;
	public static final String TEMPS_WEB_PATH_IMAGE;
	static{
		ResourceBundle rb=ResourceBundle.getBundle("config");
		
		UPL_MAX_SIZE=rb.getString("UPL_MAX_SIZE");
		ROOT_PATH=rb.getString("ROOT_PATH");
		
		TMB_URL=rb.getString("CONTEXT_PATH")+"/tmb/";
		URL=rb.getString("CONTEXT_PATH")+"/uploads/";
		ROOT_PATH_AVATAR = rb.getString("ROOT_PATH") + "/avatar";
		URL_AVATAR = rb.getString("CONTEXT_PATH")+"/uploads/avatar/";
		ADMIN_LIST = rb.getString("ADMIN_LIST");
		PAGE_SIZE = Integer.parseInt(rb.getString("PAGE_SIZE"));
		URL_AVATAR_DEFAULT=  rb.getString("CONTEXT_PATH") + rb.getString("URL_AVATAR_DEFAULT");
		IMAGE_FILTER = new ExtensionsFilter(new String[]{".png",".jpg",".bmp"});
		INTRODUCTION_FILE_URL = rb.getString("INTRODUCTION_FILE_URL");
		TEMPS_PHYSICAL_PATH_IMAGE = rb.getString("TEMPS_PHYSICAL_PATH_IMAGE");
		TEMPS_WEB_PATH_IMAGE = rb.getString("TEMPS_WEB_PATH_IMAGE");
	}
}
