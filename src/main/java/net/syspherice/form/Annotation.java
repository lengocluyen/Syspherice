package net.syspherice.form;

import java.util.Date;

public class Annotation {
	private String annotationID;
	private String objectID;
	private String dateCreate;
	private String dateModify;
	private String userCreate;
	private String content;
	private String nameObject;
	private String state;
	
	public String getNameObject() {
		return nameObject;
	}
	public void setNameObject(String nameObject) {
		this.nameObject = nameObject;
	}
	
	
	public String getAnnotationID() {
		return annotationID;
	}
	public void setAnnotationID(String annotationID) {
		this.annotationID = annotationID;
	}
	public String getObjectID() {
		return objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	public String getDateModify() {
		return dateModify;
	}
	public void setDateModify(String dateModify) {
		this.dateModify = dateModify;
	}
	public String getUserCreate() {
		return userCreate;
	}
	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getState() {
		return state;
	}
	public void setState(String  state) {
		this.state = state;
	}
	
}