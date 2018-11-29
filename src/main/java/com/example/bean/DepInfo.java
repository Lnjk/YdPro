package com.example.bean;

import java.util.List;

public class DepInfo {
	private boolean RLO;
	private List<IdNameList> idNameList;

	public boolean isRLO() {
		return RLO;
	}

	public void setRLO(boolean rLO) {
		RLO = rLO;
	}

	public DepInfo(List<IdNameList> idNameList) {
		this.idNameList = idNameList;
	}

	public List<IdNameList> getIdNameList() {
		return idNameList;
	}

	public void setIdNameList(List<IdNameList> idNameList) {
		this.idNameList = idNameList;
	}



	public static class IdNameList {
		public String id;
		public String name;

		public IdNameList(String id, String name) {
			this.id = id;
			this.name = name;
		}

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

		@Override
		public String toString() {
			return "IdNameList [id=" + id + ", name=" + name + "]";
		}

	}
}
