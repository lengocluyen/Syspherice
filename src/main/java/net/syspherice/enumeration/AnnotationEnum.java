package net.syspherice.enumeration;

public enum AnnotationEnum {
	AnnotationID("_id"),
	ObjectID("ObjectID"),
	DateCreate("DateCreate"),
	DateModify("DateModify"),
	UserCreate("UserCreate"),
	Content("Content"),
	State("State");

	  private final String text;

	    /**
	     * @param text
	     */
	    private AnnotationEnum(final String text) {
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
