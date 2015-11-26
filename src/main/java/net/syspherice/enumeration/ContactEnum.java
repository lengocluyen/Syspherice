package net.syspherice.enumeration;

public enum ContactEnum {
	ContactID("_id"), 
	FirstName("FirstName"), 
	LastName("LastName"), 
	Email("Email"), 
	Message("Message"), 
	IsRead("IsRead"),
	DateSend("DateSend");
	private final String text;

	/**
	 * @param text
	 */
	private ContactEnum(final String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}
