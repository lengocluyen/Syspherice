package net.syspherice.form;

public class ImageData {
	// _id
	private String imageID;
	private String name;
	private String url;
	// link to Plant in ExcelDataDoc with field ID
	private String plantID;
	private String sowing_nb;
	private String repet_nb;
	private String plant_nb;
	private String panicle_nb;

	private String typeOfImageData;
	private String order;
	
	private String dateImport;
	private String userImport;

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
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

	public String getTypeOfImageData() {
		return typeOfImageData;
	}

	public void setTypeOfImageData(String typeOfImageData) {
		this.typeOfImageData = typeOfImageData;
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
