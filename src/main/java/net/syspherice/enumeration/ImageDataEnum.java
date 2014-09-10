package net.syspherice.enumeration;

public enum ImageDataEnum {
	// _id
		ImageID("_id"),
		Name("Name"),
		Url("Url"),
		// link to Plant in ExcelDataDoc with field ID
		PlantID("PlantID"),
		Sowing_nb("Sowing_nb"),
		Repet_nb("Repet_nb"),
		Plant_nb("Plant_nb"),
		Panicle_nb("Panicle_nb"),

		TypeOfImageData("TypeOfImageData"),
		Order("Order"),
		DateImport("DateImport"),
		UserImport("UserImport");
		
		 private final String text;

		    /**
		     * @param text
		     */
		    private ImageDataEnum(final String text) {
		        this.text = text;
		    }


		    /* (non-Javadoc)
		     * @see java.lang.Enum#toString()
		     */
		    @Override
		    public String toString() {
		        return text;
		    }
}
