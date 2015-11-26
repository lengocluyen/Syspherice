package net.syspherice.enumeration;

import java.util.Map;

public enum ItemTagEnum {
	ItemTagID("_id"), ItemID("ItemID"), TagID("TagID"), AccountID("AccountID"),CollectionName("CollectionName");

	private final String text;

	/**
	 * @param text
	 */
	private ItemTagEnum(final String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
