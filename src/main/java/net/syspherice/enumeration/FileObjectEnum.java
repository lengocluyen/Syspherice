package net.syspherice.enumeration;

import java.io.File;

public enum FileObjectEnum {
	FileObjectID("_id"),
	FileType("FileType"),
	FileName("FileName"),
	FileContent("FileContent");
	 private final String text;

	    /**
	     * @param text
	     */
	    private FileObjectEnum(final String text) {
	        this.text = text;
	    }


	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    public String toS() {
	        return text;
	    }
}
