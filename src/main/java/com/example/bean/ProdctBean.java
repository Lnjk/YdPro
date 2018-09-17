package com.example.bean;

import java.io.Serializable;

public class ProdctBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUrl() {
		return url;
	}

	public void setUrl(int url) {
		this.url = url;
	}

	public ProdctBean(String name, int url) {
		super();
		this.name = name;
		this.url = url;
	}

}
