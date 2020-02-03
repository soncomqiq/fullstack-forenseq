package io.forensic.springboot.Other;

import java.util.List;

public class StatGraphInfo {
	private List<String> categories;
	private List<String> data;
	
	public StatGraphInfo() {
		
	}

	public StatGraphInfo(List<String> categories, List<String> data) {
		super();
		this.categories = categories;
		this.data = data;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
