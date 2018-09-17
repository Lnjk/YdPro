package com.example.bean;

import java.io.Serializable;

public class NewPeople implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String dep;
	private int id;
	private String name;
	private String pwd;

	public NewPeople() {
	}

	public NewPeople(int paramInt, String paramString1, String paramString2,
			String paramString3, String paramString4) {
		this.id = paramInt;
		this.name = paramString1;
		this.pwd = paramString2;
		this.account = paramString3;
		this.dep = paramString4;
	}

	public String getAccount() {
		return this.account;
	}

	public String getDep() {
		return this.dep;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setAccount(String paramString) {
		this.account = paramString;
	}

	public void setDep(String paramString) {
		this.dep = paramString;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setPwd(String paramString) {
		this.pwd = paramString;
	}
}
