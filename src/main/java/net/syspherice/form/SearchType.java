package net.syspherice.form;

import java.util.List;
import java.util.Map;

public class SearchType {
	private String searchTypeID;
	private String nameDisplay;
	private List<FieldCollection> fieldOfCollections;
	private String dateCreate;
	private String userCreate;
	private String state;
	private String projectName;
	private String description;
	public String getSearchTypeID() {
		return searchTypeID;
	}
	public void setSearchTypeID(String searchTypeID) {
		this.searchTypeID = searchTypeID;
	}
	public String getNameDisplay() {
		return nameDisplay;
	}
	public void setNameDisplay(String nameDisplay) {
		this.nameDisplay = nameDisplay;
	}
	public List<FieldCollection> getFieldOfCollections() {
		return fieldOfCollections;
	}
	public void setFieldOfCollections(List<FieldCollection> fieldOfCollections) {
		this.fieldOfCollections = fieldOfCollections;
	}
	public String getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	public String getUserCreate() {
		return userCreate;
	}
	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
