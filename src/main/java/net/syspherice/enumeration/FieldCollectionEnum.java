package net.syspherice.enumeration;

public enum FieldCollectionEnum {
	Collection("Collection"),
	Field("Field");
	private final String text;

    /**
     * @param text
     */
    private FieldCollectionEnum(final String text) {
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
