package net.syspherice.utils;

public class LabelValue {
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getsValue() {
		return sValue;
	}
	public void setsValue(String sValue) {
		this.sValue = sValue;
	}
	private String lable;
	private int value;
	private String sValue;
	public LabelValue() {
		// TODO Auto-generated constructor stub
	}
	public LabelValue(String label,int value) {
		this.lable = label;
		this.value = value;
	}
	public LabelValue(String label, String sValue){
		this.lable = label;
		this.sValue = sValue;
	}
	
}
