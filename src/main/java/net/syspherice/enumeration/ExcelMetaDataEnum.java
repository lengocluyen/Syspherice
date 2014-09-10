package net.syspherice.enumeration;

public enum ExcelMetaDataEnum {
    ExcelMetaData_ID("ExcelMetaData_ID"),
	Title("Title"),
	Subject("Subject"),
	Author("Author"),
	Comments("Comments"),
	Keywords("Keywords"),
	CreateDateTime("CreateDateTime"),
	LastSaveDateTime("LastSaveDateTime");

    private final String text;

    /**
     * @param text
     */
    private ExcelMetaDataEnum(final String text) {
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