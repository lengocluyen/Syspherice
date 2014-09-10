package net.syspherice.enumeration;

public enum ExcelDataDocEnum {
	//Account
	
	//ExelDataDoc
	TextDataDocID("_id"),
	FileUrl("FileUrl"),
	Metadata("Metadata"),
	DateImport("DateImport"),
	UserImport("UserImport"),
	Description("Description"),
	ProjectName("ProjectName");
	
	  private final String text;

	    /**
	     * @param text
	     */
	    private ExcelDataDocEnum(final String text) {
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
