package net.syspherice.enumeration;

public enum AccountEnum {
	Username("Username"),
	Password("Password"),
	FirstName("FirstName"),
	LastName("LastName"),
	Birthdate("Birthdate"),
	Email("Email"),
	DateCreate("DateCreate"),
	LastLogin("LastLogin"),
	Sex("Sex"),
	Description("Description"),
	Avatar("Avatar"),
	State("State");

	  private final String text;

	    /**
	     * @param text
	     */
	    private AccountEnum(final String text) {
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
