package com.example.bean;


public class Brand {
	private String bandname;
	private int id;

	public Brand() {
		super();
	}

	public Brand(String bandname) {
		this.bandname = bandname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBandname() {
		return bandname;
	}

	public void setBandname(String bandname) {
		this.bandname = bandname;
	}

}
