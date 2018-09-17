package com.example.bean;

public class TestQx {

	private String DB_ID;
	private QX qx;

	public String getDB_ID() {
		return DB_ID;
	}

	public void setDB_ID(String dB_ID) {
		DB_ID = dB_ID;
	}

	public QX getQx() {
		return qx;
	}

	public void setQx(QX qx) {
		this.qx = qx;
	}

	public static class QX {
		public String zc;
		public String dy;
		public String cb;
		public String ml;
		public String mll;

		public String getZc() {
			return zc;
		}

		public void setZc(String zc) {
			this.zc = zc;
		}

		public String getDy() {
			return dy;
		}

		public void setDy(String dy) {
			this.dy = dy;
		}

		public String getCb() {
			return cb;
		}

		public void setCb(String cb) {
			this.cb = cb;
		}

		public String getMl() {
			return ml;
		}

		public void setMl(String ml) {
			this.ml = ml;
		}

		public String getMll() {
			return mll;
		}

		public void setMll(String mll) {
			this.mll = mll;
		}

	}
}
