package com.example.bean;

public class NewModInfo {
	private boolean mod_Add;
	private boolean mod_Alter;
	private boolean mod_Amt;
	private boolean mod_Cst;
	private boolean mod_Del;
	private boolean mod_GP;
	private boolean mod_GPR;
	private String mod_ID;
	private String mod_Name;
	private boolean mod_Out;
	private boolean mod_Prt;
	private boolean mod_Qty;
	private boolean mod_Query;
	private boolean mod_Up;

	public boolean isMod_Add() {
		return mod_Add;
	}

	public void setMod_Add(boolean mod_Add) {
		this.mod_Add = mod_Add;
	}

	public boolean isMod_Alter() {
		return mod_Alter;
	}

	public void setMod_Alter(boolean mod_Alter) {
		this.mod_Alter = mod_Alter;
	}

	public boolean isMod_Amt() {
		return mod_Amt;
	}

	public void setMod_Amt(boolean mod_Amt) {
		this.mod_Amt = mod_Amt;
	}

	public boolean isMod_Cst() {
		return mod_Cst;
	}

	public void setMod_Cst(boolean mod_Cst) {
		this.mod_Cst = mod_Cst;
	}

	public boolean isMod_Del() {
		return mod_Del;
	}

	public void setMod_Del(boolean mod_Del) {
		this.mod_Del = mod_Del;
	}

	public boolean isMod_GP() {
		return mod_GP;
	}

	public void setMod_GP(boolean mod_GP) {
		this.mod_GP = mod_GP;
	}

	public boolean isMod_GPR() {
		return mod_GPR;
	}

	public void setMod_GPR(boolean mod_GPR) {
		this.mod_GPR = mod_GPR;
	}

	public String getMod_ID() {
		return mod_ID;
	}

	public void setMod_ID(String mod_ID) {
		this.mod_ID = mod_ID;
	}

	public String getMod_Name() {
		return mod_Name;
	}

	public void setMod_Name(String mod_Name) {
		this.mod_Name = mod_Name;
	}

	public boolean isMod_Out() {
		return mod_Out;
	}

	public void setMod_Out(boolean mod_Out) {
		this.mod_Out = mod_Out;
	}

	public boolean isMod_Prt() {
		return mod_Prt;
	}

	public void setMod_Prt(boolean mod_Prt) {
		this.mod_Prt = mod_Prt;
	}

	public boolean isMod_Qty() {
		return mod_Qty;
	}

	public void setMod_Qty(boolean mod_Qty) {
		this.mod_Qty = mod_Qty;
	}

	public boolean isMod_Query() {
		return mod_Query;
	}

	public void setMod_Query(boolean mod_Query) {
		this.mod_Query = mod_Query;
	}

	public boolean isMod_Up() {
		return mod_Up;
	}

	public void setMod_Up(boolean mod_Up) {
		this.mod_Up = mod_Up;
	}

	@Override
	public String toString() {
		return "NewModInfo [mod_Add=" + mod_Add + ", mod_Alter=" + mod_Alter
				+ ", mod_Amt=" + mod_Amt + ", mod_Cst=" + mod_Cst
				+ ", mod_Del=" + mod_Del + ", mod_GP=" + mod_GP + ", mod_GPR="
				+ mod_GPR + ", mod_ID=" + mod_ID + ", mod_Name=" + mod_Name
				+ ", mod_Out=" + mod_Out + ", mod_Prt=" + mod_Prt
				+ ", mod_Qty=" + mod_Qty + ", mod_Query=" + mod_Query
				+ ", mod_Up=" + mod_Up + "]";
	}

	public NewModInfo(String mod_ID, String mod_Name, boolean mod_Add,
			boolean mod_Alter, boolean mod_Amt, boolean mod_Cst,
			boolean mod_Del, boolean mod_GP, boolean mod_GPR, boolean mod_Out,
			boolean mod_Prt, boolean mod_Qty, boolean mod_Query, boolean mod_Up) {
		super();
		this.mod_Add = mod_Add;
		this.mod_Alter = mod_Alter;
		this.mod_Amt = mod_Amt;
		this.mod_Cst = mod_Cst;
		this.mod_Del = mod_Del;
		this.mod_GP = mod_GP;
		this.mod_GPR = mod_GPR;
		this.mod_ID = mod_ID;
		this.mod_Name = mod_Name;
		this.mod_Out = mod_Out;
		this.mod_Prt = mod_Prt;
		this.mod_Qty = mod_Qty;
		this.mod_Query = mod_Query;
		this.mod_Up = mod_Up;
	}

}
