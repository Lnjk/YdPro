package com.example.bean;

import java.util.List;

public class AccountInfo {
	 private List<IdNameList> idNameList;
	    private boolean RLO;
	public List<IdNameList> getIdnamelist() {
		return idNameList;
	}

	public void setIdnamelist(List<IdNameList> idnamelist) {
		this.idNameList = idnamelist;
	}

	public boolean isRlo() {
		return RLO;
	}

	public void setRlo(boolean rlo) {
		this.RLO = rlo;
	}

	public static class IdNameList {

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
		private String id;
		private String name;

		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDb_Driver() {
			return db_Driver;
		}

		public void setDb_Driver(String db_Driver) {
			this.db_Driver = db_Driver;
		}

		public String getDb_Id() {
			return db_Id;
		}

		public void setDb_Id(String db_Id) {
			this.db_Id = db_Id;
		}

		public String getDb_Name() {
			return db_Name;
		}

		public void setDb_Name(String db_Name) {
			this.db_Name = db_Name;
		}

		public int getDb_Prot() {
			return db_Prot;
		}

		public void setDb_Prot(int db_Prot) {
			this.db_Prot = db_Prot;
		}

		public String getDb_Pwd() {
			return db_Pwd;
		}

		public void setDb_Pwd(String db_Pwd) {
			this.db_Pwd = db_Pwd;
		}

		public String getDb_Type() {
			return db_Type;
		}

		public void setDb_Type(String db_Type) {
			this.db_Type = db_Type;
		}

		public String getDb_Url() {
			return db_Url;
		}

		public void setDb_Url(String db_Url) {
			this.db_Url = db_Url;
		}

		public String getDb_User() {
			return db_User;
		}

		public void setDb_User(String db_User) {
			this.db_User = db_User;
		}

		public String getServer_Address() {
			return server_Address;
		}

		public void setServer_Address(String server_Address) {
			this.server_Address = server_Address;
		}

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

		@Override
		public String toString() {
			return "IdNameList [db_Driver=" + db_Driver + ", db_Id=" + db_Id
					+ ", db_Name=" + db_Name + ", db_Prot=" + db_Prot
					+ ", db_Pwd=" + db_Pwd + ", db_Type=" + db_Type
					+ ", db_Url=" + db_Url + ", db_User=" + db_User
					+ ", server_Address=" + server_Address + ", user_Mod="
					+ user_Mod + ", user_Query=" + user_Query + ", id=" + id
					+ ", name=" + name + "]";
		}

		


		

	}
}
