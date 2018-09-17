package com.example.bean;

public class CusterInfo {
	 private String account;
	  private String cust;
	  private String dep;
	  private int id;
	  private String img;
	  private String name;
	  private String pwd;
	  private String user;

	  public CusterInfo()
	  {
	  }

	  public CusterInfo(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
	  {
	    this.id = paramInt;
	    this.name = paramString1;
	    this.pwd = paramString2;
	    this.dep = paramString3;
	    this.account = paramString4;
	    this.cust = paramString5;
	    this.user = paramString6;
	    this.img = paramString7;
	  }

	  public String getAccount()
	  {
	    return this.account;
	  }

	  public String getCust()
	  {
	    return this.cust;
	  }

	  public String getDep()
	  {
	    return this.dep;
	  }

	  public int getId()
	  {
	    return this.id;
	  }

	  public String getImg()
	  {
	    return this.img;
	  }

	  public String getName()
	  {
	    return this.name;
	  }

	  public String getPwd()
	  {
	    return this.pwd;
	  }

	  public String getUser()
	  {
	    return this.user;
	  }

	  public void setAccount(String paramString)
	  {
	    this.account = paramString;
	  }

	  public void setCust(String paramString)
	  {
	    this.cust = paramString;
	  }

	  public void setDep(String paramString)
	  {
	    this.dep = paramString;
	  }

	  public void setId(int paramInt)
	  {
	    this.id = paramInt;
	  }

	  public void setImg(String paramString)
	  {
	    this.img = paramString;
	  }

	  public void setName(String paramString)
	  {
	    this.name = paramString;
	  }

	  public void setPwd(String paramString)
	  {
	    this.pwd = paramString;
	  }

	  public void setUser(String paramString)
	  {
	    this.user = paramString;
	  }
}
