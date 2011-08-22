package com.cellfish.mediadb.account.client.model.search;


public class Search<T> {

	private String name;

	private Integer page;

	private Integer window;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getWindow() {
		return window;
	}

	public void setWindow(Integer window) {
		this.window = window;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
