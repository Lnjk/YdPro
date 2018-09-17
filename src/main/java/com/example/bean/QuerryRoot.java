package com.example.bean;

public class QuerryRoot {
	private int accountId;
	private String rootCust;
	private String rootDep;
	private String rootSup;
	private String rootUsr;

	public QuerryRoot() {
	}

	public QuerryRoot(int paramInt, String paramString1, String paramString2,
			String paramString3, String paramString4) {
		this.accountId = paramInt;
		this.rootDep = paramString1;
		this.rootSup = paramString2;
		this.rootCust = paramString3;
		this.rootUsr = paramString4;
	}

	public int getAccountId() {
		return this.accountId;
	}

	public String getRootCust() {
		return this.rootCust;
	}

	public String getRootDep() {
		return this.rootDep;
	}

	public String getRootSup() {
		return this.rootSup;
	}

	public String getRootUsr() {
		return this.rootUsr;
	}

	public void setAccountId(int paramInt) {
		this.accountId = paramInt;
	}

	public void setRootCust(String paramString) {
		this.rootCust = paramString;
	}

	public void setRootDep(String paramString) {
		this.rootDep = paramString;
	}

	public void setRootSup(String paramString) {
		this.rootSup = paramString;
	}

	public void setRootUsr(String paramString) {
		this.rootUsr = paramString;
	}
}
