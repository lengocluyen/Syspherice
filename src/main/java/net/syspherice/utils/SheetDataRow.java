package net.syspherice.utils;

import java.util.ArrayList;
import java.util.List;

public class SheetDataRow {
	List<SheetDataCell> colomns;

	public List<SheetDataCell> getColomns() {
		return colomns;
	}

	public void setColomns(List<SheetDataCell> colomns) {
		this.colomns = colomns;
	}
	public SheetDataRow() {
		// TODO Auto-generated constructor stub
		this.colomns = new ArrayList<SheetDataCell>();
	}
	public void addRow(SheetDataCell data){
		this.colomns.add(data);
	}
}
