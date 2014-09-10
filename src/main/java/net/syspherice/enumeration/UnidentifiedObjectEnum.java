package net.syspherice.enumeration;

public enum UnidentifiedObjectEnum {
	DocID("_id"),
	textDataDocID("textDataDocID"),
	ID("ID");

	  private final String text;

	    /**
	     * @param text
	     */
	    private UnidentifiedObjectEnum(final String text) {
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
