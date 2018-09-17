package com.example.bean;

import java.io.Serializable;

public class ModRoot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private boolean amt;
	private boolean cst;
	private boolean del;
	private int id;
	private String modName;
	private boolean pr;
	private boolean prg;
	private boolean prt;
	private boolean qty;
	private boolean querry;
	private boolean qut;
	private boolean set;
	private boolean up;

	public ModRoot() {
	}

	public ModRoot(int paramInt, String paramString, boolean paramBoolean1,
			boolean paramBoolean2, boolean paramBoolean3,
			boolean paramBoolean4, boolean paramBoolean5,
			boolean paramBoolean6, boolean paramBoolean7,
			boolean paramBoolean8, boolean paramBoolean9,
			boolean paramBoolean10, boolean paramBoolean11,
			boolean paramBoolean12) {
		this.id = paramInt;
		this.modName = paramString;
		this.querry = paramBoolean1;
		this.add = paramBoolean2;
		this.set = paramBoolean3;
		this.del = paramBoolean4;
		this.prt = paramBoolean5;
		this.qut = paramBoolean6;
		this.qty = paramBoolean7;
		this.up = paramBoolean8;
		this.amt = paramBoolean9;
		this.cst = paramBoolean10;
		this.pr = paramBoolean11;
		this.prg = paramBoolean12;
	}

	public int getId() {
		return this.id;
	}

	public String getModName() {
		return this.modName;
	}

	public boolean isAdd() {
		return this.add;
	}

	public boolean isAmt() {
		return this.amt;
	}

	public boolean isCst() {
		return this.cst;
	}

	public boolean isDel() {
		return this.del;
	}

	public boolean isPr() {
		return this.pr;
	}

	public boolean isPrg() {
		return this.prg;
	}

	public boolean isPrt() {
		return this.prt;
	}

	public boolean isQty() {
		return this.qty;
	}

	public boolean isQuerry() {
		return this.querry;
	}

	public boolean isQut() {
		return this.qut;
	}

	public boolean isSet() {
		return this.set;
	}

	public boolean isUp() {
		return this.up;
	}

	public void setAdd(boolean paramBoolean) {
		this.add = paramBoolean;
	}

	public void setAmt(boolean paramBoolean) {
		this.amt = paramBoolean;
	}

	public void setCst(boolean paramBoolean) {
		this.cst = paramBoolean;
	}

	public void setDel(boolean paramBoolean) {
		this.del = paramBoolean;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setModName(String paramString) {
		this.modName = paramString;
	}

	public void setPr(boolean paramBoolean) {
		this.pr = paramBoolean;
	}

	public void setPrg(boolean paramBoolean) {
		this.prg = paramBoolean;
	}

	public void setPrt(boolean paramBoolean) {
		this.prt = paramBoolean;
	}

	public void setQty(boolean paramBoolean) {
		this.qty = paramBoolean;
	}

	public void setQuerry(boolean paramBoolean) {
		this.querry = paramBoolean;
	}

	public void setQut(boolean paramBoolean) {
		this.qut = paramBoolean;
	}

	public void setSet(boolean paramBoolean) {
		this.set = paramBoolean;
	}

	public void setUp(boolean paramBoolean) {
		this.up = paramBoolean;
	}
}
