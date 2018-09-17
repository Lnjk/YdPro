package com.example.bean;

public class ModBean {
	private String userid;
	private String AMT;
	private String UP;
	private String OUT;
	private String DEL;
	private String SET;
	private String modNAME;
	private String modID;
	private String PRG;
	private String QTY;
	private String PR;
	private String CST;
	private String PRT;
	private String Querry;
	private String Add;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAMT() {
		return AMT;
	}

	public void setAMT(String aMT) {
		AMT = aMT;
	}

	public String getUP() {
		return UP;
	}

	public void setUP(String uP) {
		UP = uP;
	}

	public String getOUT() {
		return OUT;
	}

	public void setOUT(String oUT) {
		OUT = oUT;
	}

	public String getDEL() {
		return DEL;
	}

	public void setDEL(String dEL) {
		DEL = dEL;
	}

	public String getSET() {
		return SET;
	}

	public void setSET(String sET) {
		SET = sET;
	}

	public String getModNAME() {
		return modNAME;
	}

	public void setModNAME(String modNAME) {
		this.modNAME = modNAME;
	}

	public String getModID() {
		return modID;
	}

	public void setModID(String modID) {
		this.modID = modID;
	}

	public String getPRG() {
		return PRG;
	}

	public void setPRG(String pRG) {
		PRG = pRG;
	}

	public String getQTY() {
		return QTY;
	}

	public void setQTY(String qTY) {
		QTY = qTY;
	}

	public String getPR() {
		return PR;
	}

	public void setPR(String pR) {
		PR = pR;
	}

	public String getCST() {
		return CST;
	}

	public void setCST(String cST) {
		CST = cST;
	}

	public String getPRT() {
		return PRT;
	}

	public void setPRT(String pRT) {
		PRT = pRT;
	}

	public String getQuerry() {
		return Querry;
	}

	public void setQuerry(String querry) {
		Querry = querry;
	}

	public String getAdd() {
		return Add;
	}

	public void setAdd(String add) {
		Add = add;
	}

	@Override
	public String toString() {
		return "ModBean [userid=" + userid + ", AMT=" + AMT + ", UP=" + UP
				+ ", OUT=" + OUT + ", DEL=" + DEL + ", SET=" + SET
				+ ", modNAME=" + modNAME + ", modID=" + modID + ", PRG=" + PRG
				+ ", QTY=" + QTY + ", PR=" + PR + ", CST=" + CST + ", PRT="
				+ PRT + ", Querry=" + Querry + ", Add=" + Add + "]";
	}

}
