package net.syspherice.enumeration;

public enum BinXmlDataEnum {
	// _id
		XmlID("_id"),
		Name("Name"),
		Url("Url"),
		UrlTemps("UrlTemps"),
		// link to Plant in ExcelDataDoc with field ID
		PlantID("PlantID"),
		Sowing_nb("Sowing_nb"),
		Repet_nb("Repet_nb"),
		Plant_nb("Plant_nb"),
		Panicle_nb("Panicle_nb"),

		DateImport("DateImport"),
		UserImport("UserImport");
		
		 private final String text;

		    /**
		     * @param text
		     */
		    private BinXmlDataEnum(final String text) {
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
