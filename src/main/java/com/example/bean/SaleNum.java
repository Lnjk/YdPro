package com.example.bean;

public class SaleNum {
	private String id;
	private String idNum;

	public SaleNum(String id, String idNum) {
		super();
		this.id = id;
		this.idNum = idNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Override
	public String toString() {
		return "SaleNum [id=" + id + ", idNum=" + idNum + "]";
	}


}
