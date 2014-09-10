package net.syspherice.form;

import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ExcelDataDoc {
	@Id
	private String textDataDocID;
	private String fileUrl;
	private ExcelMetaData metadata;
	private String dateImport;
	private String userImport;
	private String description;
	private String projectname;
	
	public String getTextDataDocID() {
		return textDataDocID;
	}

	public void setTextDataDocID(String textDataDocID) {
		this.textDataDocID = textDataDocID;
	}

	public String getDateImport() {
		return dateImport;
	}

	public void setDateImport(String dateImport) {
		this.dateImport = dateImport;
	}

	public String getUserImport() {
		return userImport;
	}

	public void setUserImport(String userImport) {
		this.userImport = userImport;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public ExcelMetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(ExcelMetaData metadata) {
		this.metadata = metadata;
	}

}
