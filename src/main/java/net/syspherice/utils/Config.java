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
		IMAGE_FILTER = new ExtensionsFilter(new String[]{".png",".jpg",".bmp"});
	}
}
