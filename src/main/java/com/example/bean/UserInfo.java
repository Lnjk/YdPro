package com.example.bean;

import java.util.Date;
import java.util.List;

public class UserInfo {
	private String signaturePWD;
	private String device_Id;
	private String device_image;
	private String signature;
	private String user_Cust;
	private String user_DB;
	private String user_Dep;
	private String user_Id;
	private List<User_Mod> user_Mod;
	private String user_Name;
	private String user_Pwd;
	private List<User_Query> user_Query;
	private Date user_date;
	private String user_erpUser;

	public String getDevice_Id() {
		return device_Id;
	}

	public void setDevice_Id(String device_Id) {
		this.device_Id = device_Id;
	}

	public String getDevice_image() {
		return device_image;
	}

	public void setDevice_image(String device_image) {
		this.device_image = device_image;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUser_Cust() {
		return user_Cust;
	}

	public void setUser_Cust(String user_Cust) {
		this.user_Cust = user_Cust;
	}

	public String getUser_DB() {
		return user_DB;
	}

	public void setUser_DB(String user_DB) {
		this.user_DB = user_DB;
	}

	public String getUser_Dep() {
		return user_Dep;
	}

	public void setUser_Dep(String user_Dep) {
		this.user_Dep = user_Dep;
	}

	public String getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}

	public List<User_Mod> getUser_Mod() {
		return user_Mod;
	}

	public void setUser_Mod(List<User_Mod> user_Mod) {
		this.user_Mod = user_Mod;
	}

	public String getUser_Name() {
		return user_Name;
	}

	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}

	public String getUser_Pwd() {
		return user_Pwd;
	}

	public void setUser_Pwd(String user_Pwd) {
		this.user_Pwd = user_Pwd;
	}

	public List<User_Query> getUser_Query() {
		return user_Query;
	}

	public void setUser_Query(List<User_Query> user_Query) {
		this.user_Query = user_Query;
	}

	public Date getUser_date() {
		return user_date;
	}

	public void setUser_date(Date user_date) {
		this.user_date = user_date;
	}

	public String getUser_erpUser() {
		return user_erpUser;
	}

	public void setUser_erpUser(String user_erpUser) {
		this.user_erpUser = user_erpUser;
	}

	public String getSignaturePWD() {
		return signaturePWD;
	}

	public void setSignaturePWD(String signaturePWD) {
		this.signaturePWD = signaturePWD;
	}

	@Override
	public String toString() {
		return "UserInfo [signaturePWD=" + signaturePWD + ", device_Id="
				+ device_Id + ", device_image=" + device_image + ", signature="
				+ signature + ", user_Cust=" + user_Cust + ", user_DB="
				+ user_DB + ", user_Dep=" + user_Dep + ", user_Id=" + user_Id
				+ ", user_Mod=" + user_Mod + ", user_Name=" + user_Name
				+ ", user_Pwd=" + user_Pwd + ", user_Query=" + user_Query
				+ ", user_date=" + user_date + ", user_erpUser=" + user_erpUser
				+ "]";
	}

	public class User_Mod {
		//--------
		private String accountDB;
		private String db_ID;
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
		private String module_List;
		private String user;
		private String user_ID;

		public String getAccountDB() {
			return accountDB;
		}

		public void setAccountDB(String accountDB) {
			this.accountDB = accountDB;
		}

		public String getDb_ID() {
			return db_ID;
		}

		public void setDb_ID(String db_ID) {
			this.db_ID = db_ID;
		}

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

		public String getModule_List() {
			return module_List;
		}

		public void setModule_List(String module_List) {
			this.module_List = module_List;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getUser_ID() {
			return user_ID;
		}

		public void setUser_ID(String user_ID) {
			this.user_ID = user_ID;
		}

		@Override
		public String toString() {
			return "User_Mod [accountDB=" + accountDB + ", db_ID=" + db_ID
					+ ", mod_Add=" + mod_Add + ", mod_Set=" + mod_Alter
					+ ", mod_Amt=" + mod_Amt + ", mod_Cst=" + mod_Cst
					+ ", mod_Del=" + mod_Del + ", mod_GP=" + mod_GP
					+ ", mod_GPR=" + mod_GPR + ", mod_ID=" + mod_ID
					+ ", mod_Name=" + mod_Name + ", mod_Out=" + mod_Out
					+ ", mod_Prt=" + mod_Prt + ", mod_Qty=" + mod_Qty
					+ ", mod_Query=" + mod_Query + ", mod_Up=" + mod_Up
					+ ", module_List=" + module_List + ", user=" + user
					+ ", user_ID=" + user_ID + "]";
		}

	}

	public class User_Query {

		private String accountDB;
		private String query_Cust;
		private String query_DB;
		private String query_CompDep;
		private String query_Dep;
		private String query_Sup;
		private String query_User;
		private String user;
		private String user_id;

		public String getQuery_CompDep() {
			return query_CompDep;
		}

		public void setQuery_CompDep(String query_CompDep) {
			this.query_CompDep = query_CompDep;
		}

		public String getAccountDB() {
			return accountDB;
		}

		public void setAccountDB(String accountDB) {
			this.accountDB = accountDB;
		}

		public String getQuery_Cust() {
			return query_Cust;
		}

		public void setQuery_Cust(String query_Cust) {
			this.query_Cust = query_Cust;
		}

		public String getQuery_DB() {
			return query_DB;
		}

		public void setQuery_DB(String query_DB) {
			this.query_DB = query_DB;
		}

		public String getQuery_Dep() {
			return query_Dep;
		}

		public void setQuery_Dep(String query_Dep) {
			this.query_Dep = query_Dep;
		}

		public String getQuery_Sup() {
			return query_Sup;
		}

		public void setQuery_Sup(String query_Sup) {
			this.query_Sup = query_Sup;
		}

		public String getQuery_User() {
			return query_User;
		}

		public void setQuery_User(String query_User) {
			this.query_User = query_User;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		@Override
		public String toString() {
			return "User_Query [accountDB=" + accountDB + ", query_Cust="
					+ query_Cust + ", query_DB=" + query_DB
					+ ", query_CompDep=" + query_CompDep + ", query_Dep="
					+ query_Dep + ", query_Sup=" + query_Sup + ", query_User="
					+ query_User + ", user=" + user + ", user_id=" + user_id
					+ "]";
		}

		

	}
}
