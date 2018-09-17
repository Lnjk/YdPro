package com.example.bean;

import java.util.List;


public class AccountDB1 {
	private boolean RLO;
	private List<QueryListBean> queryList;

	public List<QueryListBean> getQueryList() {
		return this.queryList;
	}

	public boolean isRLO() {
		return this.RLO;
	}

	public void setQueryList(List<QueryListBean> paramList) {
		this.queryList = paramList;
	}

	public void setRLO(boolean paramBoolean) {
		this.RLO = paramBoolean;
	}

	public static class QueryListBean {
		private String db_Driver;
		private String db_Id;
		private String db_Name;
		private int db_Prot;
		private String db_Pwd;
		private String db_Type;
		private String db_Url;
		private String db_User;
		private String server_Address;
		private String user_Mod;
	    private String user_Query;
		public String getUser_Mod() {
			return user_Mod;
		}

		public void setUser_Mod(String user_Mod) {
			this.user_Mod = user_Mod;
		}

		public String getUser_Query() {
			return user_Query;
		}

		public void setUser_Query(String user_Query) {
			this.user_Query = user_Query;
		}

		public String getDb_Driver() {
			return this.db_Driver;
		}

		public String getDb_Id() {
			return this.db_Id;
		}

		public String getDb_Name() {
			return this.db_Name;
		}

		public int getDb_Prot() {
			return this.db_Prot;
		}

		public String getDb_Pwd() {
			return this.db_Pwd;
		}

		public String getDb_Type() {
			return this.db_Type;
		}

		public String getDb_Url() {
			return this.db_Url;
		}

		public String getDb_User() {
			return this.db_User;
		}

		public String getServer_Address() {
			return this.server_Address;
		}

		public void setDb_Driver(String paramString) {
			this.db_Driver = paramString;
		}

		public void setDb_Id(String paramString) {
			this.db_Id = paramString;
		}

		public void setDb_Name(String paramString) {
			this.db_Name = paramString;
		}

		public void setDb_Prot(int paramInt) {
			this.db_Prot = paramInt;
		}

		public void setDb_Pwd(String paramString) {
			this.db_Pwd = paramString;
		}

		public void setDb_Type(String paramString) {
			this.db_Type = paramString;
		}

		public void setDb_Url(String paramString) {
			this.db_Url = paramString;
		}

		public void setDb_User(String paramString) {
			this.db_User = paramString;
		}

		public void setServer_Address(String paramString) {
			this.server_Address = paramString;
		}
	}
}
