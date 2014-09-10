package net.syspherice.utils;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.syspherice.form.Account;
import net.syspherice.form.ExcelDataDoc;

public class SessionManage {
	HttpSession session;
	// Account
	private Account account;
	private Boolean isLogin;
	private Boolean isAdmin;
	private Boolean isAdminPage;

	public Account getAccount() {
		if (account != null)
			return account;
		else
			// return new Account();
			return (Account) session.getAttribute("Account");
	}

	public void setAccount(Account account) {
		session.setAttribute("Account", account);
		this.account = account;
	}

	public Boolean getIsLogin() {
		if (isLogin != null)
			return isLogin;
		else
			return (Boolean) session.getAttribute("IsLogin");
	}

	public void setIsLogin(Boolean isLogin) {
		session.setAttribute("IsLogin", isLogin);
		this.isLogin = isLogin;
	}

	public Boolean getIsAdmin() {
		if (isAdmin != null)
			return isAdmin;
		else
			// return true;
			// test
			return (Boolean) session.getAttribute("IsAdmin");
	}

	public void setIsAdmin(Boolean isAdmin) {
		session.setAttribute("IsAdmin", isAdmin);
		this.isAdmin = isAdmin;
	}

	public Boolean getIsAdminPage() {
		if (isAdminPage != null)
			return isAdminPage;
		else
			// return true;
			// test
			return (Boolean) session.getAttribute("IsAdminPage");
	}

	public void setIsAdminPage(Boolean isAdminPage) {
		session.setAttribute("IsAdminPage", isAdminPage);
		this.isAdminPage = isAdminPage;
	}

	// end account session
	// Sheet info
	private SheetInfo sheetInfo;
	private List<SheetInfo> sheets;

	public SheetInfo getSheetInfo() {
		if (sheetInfo != null)
			return sheetInfo;
		return (SheetInfo) session.getAttribute("sheetinfo");
	}

	public void setSheetInfo(SheetInfo sheetInfo) {
		session.setAttribute("sheetinfo", sheetInfo);
		this.sheetInfo = sheetInfo;
	}

	public List<SheetInfo> getSheets() {
		if (sheets != null)
			return sheets;
		return (List<SheetInfo>) session.getAttribute("sheets");
	}

	public void setSheets(List<SheetInfo> sheets) {
		session.setAttribute("sheets", sheets);
		this.sheets = sheets;
	}

	public ExcelDataDoc excelDataDoc;
	public String excelProjectName;

	public ExcelDataDoc getExcelDataDoc() {
		try {
			ExcelDataDoc data = (ExcelDataDoc) session
					.getAttribute(ExcelDataDoc.class.getSimpleName());
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	public void setExcelDataDoc(Object a) {
		session.setAttribute(ExcelDataDoc.class.getSimpleName(), a);
	}

	public String getExcelProjectName() {
		if (excelProjectName != null)
			return excelProjectName;
		else
			// return true;
			// test
			return (String) session.getAttribute("ExcelProjectName");
	}

	public void setExcelProjectName(String excelProjectName) {
		session.setAttribute("ExcelProjectName", excelProjectName);
		this.excelProjectName = excelProjectName;
	}

	private String collectionName;
	private String collectionID;

	public String getCollectionName() {
		if (collectionName != null)
			return collectionName;
		else
			return (String) session.getAttribute("collectionName");
	}

	public void setCollectionName(String collectionName) {
		session.setAttribute("collectionName", collectionName);
	}

	public String getCollectionID() {
		if (collectionID != null)
			return collectionID;
		else
			return (String) session.getAttribute("collectionID");
	}

	public void setCollectionID(String collectionID) {
		session.setAttribute("collectionID", collectionID);

	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public SessionManage(HttpSession session) {
		// TODO Auto-generated constructor stub
		this.session = session;
	}
}
