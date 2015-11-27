package net.syspherice.form;

public class BinXmlData {
	// _id
	private String xmlID;
	private String name;
	private String url;
	private String urlTemps;
	// link to Plant in ExcelDataDoc with field ID
	private String plantID;
	private String sowing_nb;
	private String repet_nb;
	private String plant_nb;
	private String panicle_nb;

	
	private String dateImport;
	private String userImport;
	
	public String getXmlID() {
		return xmlID;
	}

	public void setxmlID(String xmlID) {
		this.xmlID = xmlID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlTemps() {
		return urlTemps;
	}

	public void setUrlTemps(String urlTemps) {
		this.urlTemps = urlTemps;
	}
	

	public String getPlantID() {
		return plantID;
	}

	public void setPlantID(String plantID) {
		this.plantID = plantID;
	}

	public String getSowing_nb() {
		return sowing_nb;
	}

	public void setSowing_nb(String sowing_nb) {
		this.sowing_nb = sowing_nb;
	}

	public String getRepet_nb() {
		return repet_nb;
	}

	public void setRepet_nb(String repet_nb) {
		this.repet_nb = repet_nb;
	}

	public String getPlant_nb() {
		return plant_nb;
	}

	public void setPlant_nb(String plant_nb) {
		this.plant_nb = plant_nb;
	}

	public String getPanicle_nb() {
		return panicle_nb;
	}

	public void setPanicle_nb(String panicle_nb) {
		this.panicle_nb = panicle_nb;
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

}
