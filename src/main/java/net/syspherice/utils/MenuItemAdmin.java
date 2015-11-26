package net.syspherice.utils;

import java.util.ArrayList;

public class MenuItemAdmin {
	MenuItem item;
	ArrayList<MenuItem> enfants;
	public MenuItem getItem() {
		return item;
	}
	public void setItem(MenuItem item) {
		this.item = item;
	}
	public ArrayList<MenuItem> getEnfants() {
		return enfants;
	}
	public void setEnfants(ArrayList<MenuItem> enfants) {
		this.enfants = enfants;
	}
	public MenuItemAdmin(MenuItem item, ArrayList<MenuItem> en) {
		this.setItem(item);
		this.setEnfants(en);
	}
}
