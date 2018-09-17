package com.example.bean;

public class ModId {

	private boolean RLO;
	private Module module;

	public boolean isRLO() {
		return RLO;
	}

	public void setRLO(boolean rLO) {
		RLO = rLO;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public class Module {

		private String mod_ID;
		private String mod_Level;
		private String mod_Name;
		private String user_Mod;

		public String getMod_ID() {
			return mod_ID;
		}

		public void setMod_ID(String mod_ID) {
			this.mod_ID = mod_ID;
		}

		public String getMod_Level() {
			return mod_Level;
		}

		public void setMod_Level(String mod_Level) {
			this.mod_Level = mod_Level;
		}

		public String getMod_Name() {
			return mod_Name;
		}

		public void setMod_Name(String mod_Name) {
			this.mod_Name = mod_Name;
		}

		public String getUser_Mod() {
			return user_Mod;
		}

		public void setUser_Mod(String user_Mod) {
			this.user_Mod = user_Mod;
		}
	}

}
