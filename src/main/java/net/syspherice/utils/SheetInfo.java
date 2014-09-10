package net.syspherice.utils;

import javax.persistence.Entity;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFSheet;
@Entity
public class SheetInfo {
	int number;
	//for getting the information id and a list of fields index
	String colonneID;
	public String getColonneID() {
		return colonneID;
	}
	public void setColonneID(String colonneID) {
		this.colonneID = colonneID;
	}
	public String[] getColonneIndex() {
		return colonneIndex;
	}
	public void setColonneIndex(String[] colonneIndex) {
		this.colonneIndex = colonneIndex;
	}
	String[] colonneIndex;
	
	String name;
	String typefile;
	HSSFSheet contentXSL;
	XSSFSheet contentXSLX;
	String path;
	public SheetInfo() {
		// TODO Auto-generated constructor stub
	}
	public SheetInfo(int number, String name, String typefile, HSSFSheet conXSL,XSSFSheet conXSLS,String path) {
		this.setName(name);
		this.setNumber(number);
		this.setTypefile(typefile);
		this.setContentXSL(conXSL);
		this.setContentXSLX(conXSLS);
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypefile() {
		return typefile;
	}
	public void setTypefile(String typefile) {
		this.typefile = typefile;
	}
	public HSSFSheet getContentXSL() {
		return contentXSL;
	}
	public void setContentXSL(HSSFSheet contentXSL) {
		this.contentXSL = contentXSL;
	}
	public XSSFSheet getContentXSLX() {
		return contentXSLX;
	}
	public void setContentXSLX(XSSFSheet contentXSLX) {
		this.contentXSLX = contentXSLX;
	}
	
}
