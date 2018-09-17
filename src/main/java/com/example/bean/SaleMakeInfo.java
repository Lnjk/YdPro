package com.example.bean;

import java.util.List;

public class SaleMakeInfo {
	private String Head;
	private List<Data> data;

	public String getHead() {
		return Head;
	}

	public void setHead(String head) {
		Head = head;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public static class Data {

		private String affiliateName;
		private String affiliateNo;
		private String brandNO;
		private String brandName;
		private String gP;
		private String gPM;
		private String prdIndx;
		private String prdNo;
		private String saAmtn;
		private String saCst;
		private String salesChannelName;
		private String salesChannelNo;
		private String salesDepartmentName;
		private String salesDepartmentNo;
		private String salesTerminalName;
		private String salesTerminalNo;
		private String sbAmtn;
		private String sbCst;
		private String sbsAmatn;
		private String sdAmtn;
		private String sellingOperationName;
		private String sellingOperationNo;
		private String snAmtn;
		private String snCst;
		private String ssAmtn;

		public String getAffiliateName() {
			return affiliateName;
		}

		public void setAffiliateName(String affiliateName) {
			this.affiliateName = affiliateName;
		}

		public String getAffiliateNo() {
			return affiliateNo;
		}

		public void setAffiliateNo(String affiliateNo) {
			this.affiliateNo = affiliateNo;
		}

		public String getBrandNO() {
			return brandNO;
		}

		public void setBrandNO(String brandNO) {
			this.brandNO = brandNO;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public String getgP() {
			return gP;
		}

		public void setgP(String gP) {
			this.gP = gP;
		}

		public String getgPM() {
			return gPM;
		}

		public void setgPM(String gPM) {
			this.gPM = gPM;
		}

		public String getPrdIndx() {
			return prdIndx;
		}

		public void setPrdIndx(String prdIndx) {
			this.prdIndx = prdIndx;
		}

		public String getPrdNo() {
			return prdNo;
		}

		public void setPrdNo(String prdNo) {
			this.prdNo = prdNo;
		}

		public String getSaAmtn() {
			return saAmtn;
		}

		public void setSaAmtn(String saAmtn) {
			this.saAmtn = saAmtn;
		}

		public String getSaCst() {
			return saCst;
		}

		public void setSaCst(String saCst) {
			this.saCst = saCst;
		}

		public String getSalesChannelName() {
			return salesChannelName;
		}

		public void setSalesChannelName(String salesChannelName) {
			this.salesChannelName = salesChannelName;
		}

		public String getSalesChannelNo() {
			return salesChannelNo;
		}

		public void setSalesChannelNo(String salesChannelNo) {
			this.salesChannelNo = salesChannelNo;
		}

		public String getSalesDepartmentName() {
			return salesDepartmentName;
		}

		public void setSalesDepartmentName(String salesDepartmentName) {
			this.salesDepartmentName = salesDepartmentName;
		}

		public String getSalesDepartmentNo() {
			return salesDepartmentNo;
		}

		public void setSalesDepartmentNo(String salesDepartmentNo) {
			this.salesDepartmentNo = salesDepartmentNo;
		}

		public String getSalesTerminalName() {
			return salesTerminalName;
		}

		public void setSalesTerminalName(String salesTerminalName) {
			this.salesTerminalName = salesTerminalName;
		}

		public String getSalesTerminalNo() {
			return salesTerminalNo;
		}

		public void setSalesTerminalNo(String salesTerminalNo) {
			this.salesTerminalNo = salesTerminalNo;
		}

		public String getSbAmtn() {
			return sbAmtn;
		}

		public void setSbAmtn(String sbAmtn) {
			this.sbAmtn = sbAmtn;
		}

		public String getSbCst() {
			return sbCst;
		}

		public void setSbCst(String sbCst) {
			this.sbCst = sbCst;
		}

		public String getSbsAmatn() {
			return sbsAmatn;
		}

		public void setSbsAmatn(String sbsAmatn) {
			this.sbsAmatn = sbsAmatn;
		}

		public String getSdAmtn() {
			return sdAmtn;
		}

		public void setSdAmtn(String sdAmtn) {
			this.sdAmtn = sdAmtn;
		}

		public String getSellingOperationName() {
			return sellingOperationName;
		}

		public void setSellingOperationName(String sellingOperationName) {
			this.sellingOperationName = sellingOperationName;
		}

		public String getSellingOperationNo() {
			return sellingOperationNo;
		}

		public void setSellingOperationNo(String sellingOperationNo) {
			this.sellingOperationNo = sellingOperationNo;
		}

		public String getSnAmtn() {
			return snAmtn;
		}

		public void setSnAmtn(String snAmtn) {
			this.snAmtn = snAmtn;
		}

		public String getSnCst() {
			return snCst;
		}

		public void setSnCst(String snCst) {
			this.snCst = snCst;
		}

		public String getSsAmtn() {
			return ssAmtn;
		}

		public void setSsAmtn(String ssAmtn) {
			this.ssAmtn = ssAmtn;
		}

		@Override
		public String toString() {
			return "Data [affiliateName=" + affiliateName + ", affiliateNo="
					+ affiliateNo + ", brandNO=" + brandNO + ", brandName="
					+ brandName + ", gP=" + gP + ", gPM=" + gPM + ", prdIndx="
					+ prdIndx + ", prdNo=" + prdNo + ", saAmtn=" + saAmtn
					+ ", saCst=" + saCst + ", salesChannelName="
					+ salesChannelName + ", salesChannelNo=" + salesChannelNo
					+ ", salesDepartmentName=" + salesDepartmentName
					+ ", salesDepartmentNo=" + salesDepartmentNo
					+ ", salesTerminalName=" + salesTerminalName
					+ ", salesTerminalNo=" + salesTerminalNo + ", sbAmtn="
					+ sbAmtn + ", sbCst=" + sbCst + ", sbsAmatn=" + sbsAmatn
					+ ", sdAmtn=" + sdAmtn + ", sellingOperationName="
					+ sellingOperationName + ", sellingOperationNo="
					+ sellingOperationNo + ", snAmtn=" + snAmtn + ", snCst="
					+ snCst + ", ssAmtn=" + ssAmtn + "]";
		}

	}
}
