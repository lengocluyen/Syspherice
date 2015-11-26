package net.syspherice.utils;

import java.util.List;

public class PagedGenericView<E> {

	private Navigation nav = new Navigation();
	private List<E> entities;

	public Navigation getNav() {
		return nav;
	}

	public void setNav(Navigation nav) {
		this.nav = nav;
	}

	public List<E> getEntities() {
		return entities;
	}

	public void setEntities(List<E> ob) {
		this.entities = ob;
	}

	public E getObjet(int i) {
		return (E) entities.get(i);
	}

	public void setObjet(int i, E ob) {
		this.entities.add(i, ob);
	}
}
