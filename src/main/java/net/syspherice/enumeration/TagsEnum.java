package net.syspherice.enumeration;

import java.util.Map;

public enum TagsEnum {
	TagID("_id"),
	Name("Name"),
	Description("Description");

	  private final String text;

	    /**
	     * @param text
	     */
	    private TagsEnum(final String text) {
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
