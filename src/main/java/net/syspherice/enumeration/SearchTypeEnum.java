package net.syspherice.enumeration;

import java.util.Map;

public enum SearchTypeEnum {
	SearchTypeID("_id"),
	NameDisplay("NameDisplay"),
	FieldOfCollections("FieldOfCollections"),
	DateCreate("DateCreate"),
	UserCreate("UserCreate"),
	State("State"),
	ProjectName("ProjectName"),
	Description("Description");

	  private final String text;

	    /**
	     * @param text
	     */
	    private SearchTypeEnum(final String text) {
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
