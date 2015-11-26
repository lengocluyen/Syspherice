package net.syspherice.form;

import java.util.Map;

public class UnidentifiedObject {
	private String docId;
	private String textDataDocID;
	private Map<Object,Object> data;
	public String getDocId() {
		return docId;
	}
	
	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Map<Object, Object> getData() {
		return data;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

	public String getTextDataDocID() {
		return textDataDocID;
	}

	public void setTextDataDocID(String textDataDocID) {
		this.textDataDocID = textDataDocID;
	}
	
}
