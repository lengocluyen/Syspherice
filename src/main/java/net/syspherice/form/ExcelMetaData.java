package net.syspherice.form;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ExcelMetaData {
	@Id
	private String excelMetaData_ID;
	private String title;
	private String subjet;
	private String author;
	private String comments;
	private String keywords;
	private String createDateTime;
	private String lastSaveDateTime;
	//others: Template, last author, revision number, application name, security, category, format, manager, hyperlink base;
	
	public String getTitle() {
		return title;
	}
	public String getExcelMetaData_ID() {
		return excelMetaData_ID;
	}
	public void setExcelMetaData_ID(String excelMetaData_ID) {
		this.excelMetaData_ID = excelMetaData_ID;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubjet() {
		return subjet;
	}
	public void setSubjet(String subjet) {
		this.subjet = subjet;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getLastSaveDateTime() {
		return lastSaveDateTime;
	}
	public void setLastSaveDateTime(String lastSaveDateTime) {
		this.lastSaveDateTime = lastSaveDateTime;
	}
	
}
