package net.syspherice.form;

import java.io.File;

public class FileObject {
	String fileObjectID;
	String fileType;
	String fileName;
	File fileContent;
	public String getFileObjectID() {
		return fileObjectID;
	}
	public void setFileObjectID(String fileObjectID) {
		this.fileObjectID = fileObjectID;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getFileContent() {
		return fileContent;
	}
	public void setFileContent(File fileContent) {
		this.fileContent = fileContent;
	}
	
}
