package net.syspherice.utils;


public class MenuItem {
	String url;
	String name;
	String isCurrent;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public MenuItem(String url, String name, String isCurrent) {
		this.url = url;
		this.name = name;
		this.isCurrent = isCurrent;
	}
}
