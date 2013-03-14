package de.egore911.xdp.model;

import java.util.ArrayList;
import java.util.List;

public class Form {

	private String name;
	private List<Page> pages = new ArrayList<Page>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Form[name=");
		buffer.append(name);
		buffer.append("]");
		return buffer.toString();
	}
}
