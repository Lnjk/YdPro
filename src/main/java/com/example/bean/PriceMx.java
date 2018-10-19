package com.example.bean;

import java.util.List;
import java.util.Date;

/**
 * Created by ${宁.金珂} on 2018/7/9.
 */

public class PriceMx {
    private Price price;
    public void setPrice(Price price) {
        this.price = price;
    }
    public Price getPrice() {
        return price;
    }
    public class Price {
        private String ERPprice_Id;
        private String accepted;
        private String biln_user;
        private String compDep;
        private String compDepName;
        private String db_Id;
        private String db_Name;
        private String discount;
        private String obj_Type;
        private String operator;
        private String operator_Val;
        private List<PrdtUp> prdtUp;
        private String priceId;
        private String priceName;
        private Date price_DD;
        private String rem;
        private String salesTerminal_Name;
        private String salesTerminal_No;
        private String stopDate;
        private String sup_Name;
        private String sup_No;
        private String useDate;

        public String getERPprice_Id() {
            return ERPprice_Id;
        }

        public void setERPprice_Id(String ERPprice_Id) {
            this.ERPprice_Id = ERPprice_Id;
        }

        public void setAccepted(String accepted) {
            this.accepted = accepted;
        }

        public String getAccepted() {
            return accepted;
        }

        public void setBiln_user(String biln_user) {
            this.biln_user = biln_user;
        }

        public String getBiln_user() {
            return biln_user;
        }

        public void setCompDep(String compDep) {
            this.compDep = compDep;
        }

        public String getCompDep() {
            return compDep;
        }

        public void setCompDepName(String compDepName) {
            this.compDepName = compDepName;
        }

        public String getCompDepName() {
            return compDepName;
        }

        public void setDb_Id(String db_Id) {
            this.db_Id = db_Id;
        }

        public String getDb_Id() {
            return db_Id;
        }

        public void setDb_Name(String db_Name) {
            this.db_Name = db_Name;
        }

        public String getDb_Name() {
            return db_Name;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount() {
            return discount;
        }

        public void setObj_Type(String obj_Type) {
            this.obj_Type = obj_Type;
        }

        public String getObj_Type() {
            return obj_Type;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator_Val(String operator_Val) {
            this.operator_Val = operator_Val;
        }

        public String getOperator_Val() {
            return operator_Val;
        }

        public void setPrdtUp(List<PrdtUp> prdtUp) {
            this.prdtUp = prdtUp;
        }

        public List<PrdtUp> getPrdtUp() {
            return prdtUp;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceName(String priceName) {
            this.priceName = priceName;
        }

        public String getPriceName() {
            return priceName;
        }

        public void setPrice_DD(Date price_DD) {
            this.price_DD = price_DD;
        }

        public Date getPrice_DD() {
            return price_DD;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }

        public String getRem() {
            return rem;
        }

        public void setSalesTerminal_Name(String salesTerminal_Name) {
            this.salesTerminal_Name = salesTerminal_Name;
        }

        public String getSalesTerminal_Name() {
            return salesTerminal_Name;
        }

        public void setSalesTerminal_No(String salesTerminal_No) {
            this.salesTerminal_No = salesTerminal_No;
        }

        public String getSalesTerminal_No() {
            return salesTerminal_No;
        }

        public void setStopDate(String stopDate) {
            this.stopDate = stopDate;
        }

        public String getStopDate() {
            return stopDate;
        }

        public void setSup_Name(String sup_Name) {
            this.sup_Name = sup_Name;
        }

        public String getSup_Name() {
            return sup_Name;
        }

        public void setSup_No(String sup_No) {
            this.sup_No = sup_No;
        }

        public String getSup_No() {
            return sup_No;
        }

        public void setUseDate(String useDate) {
            this.useDate = useDate;
        }

        public String getUseDate() {
            return useDate;
        }
    }
    public static class PrdtUp {

        private int ITM;//序号
        private String MIN_UP;//最低售价
        private String REM;//备注信息
        private String UPR;//政策定价
        private String YN;//执行
        private String cst_Up;//单位成本
        private String dis_CNT;//折扣
        private String prdNo;
        private String prdUp;//统一定价
        private String price;//名称
        private String price_Id;//编号
        private String ZC_ZK;//自定义
        private String NAME_ZDY;//自定义

        public PrdtUp(int ITM,String prdNo,String price,String cst_Up,String UPR,String MIN_UP,String prdUp,String dis_CNT,String YN,String REM) {
            this.ITM = ITM;
            this.MIN_UP = MIN_UP;
            this.REM = REM;
            this.UPR = UPR;
            this.YN = YN;
            this.prdNo = prdNo;
            this.cst_Up = cst_Up;
            this.prdUp = prdUp;
            this.price = price;
            this.dis_CNT = dis_CNT;
//            this.NAME_ZDY = NAEM;
        }

        public String getNAME_ZDY() {
            return NAME_ZDY;
        }

        public void setNAME_ZDY(String NAME_ZDY) {
            this.NAME_ZDY = NAME_ZDY;
        }

        public String getZC_ZK() {
            return ZC_ZK;
        }

        public void setZC_ZK(String ZC_ZK) {
            this.ZC_ZK = ZC_ZK;
        }

        public void setITM(int ITM) {
            this.ITM = ITM;
        }
        public int getITM() {
            return ITM;
        }

        public void setMIN_UP(String MIN_UP) {
            this.MIN_UP = MIN_UP;
        }
        public String getMIN_UP() {
            return MIN_UP;
        }

        public void setREM(String REM) {
            this.REM = REM;
        }
        public String getREM() {
            return REM;
        }

        public void setUPR(String UPR) {
            this.UPR = UPR;
        }
        public String getUPR() {
            return UPR;
        }

        public void setYN(String YN) {
            this.YN = YN;
        }
        public String getYN() {
            return YN;
        }

        public void setCst_Up(String cst_Up) {
            this.cst_Up = cst_Up;
        }
        public String getCst_Up() {
            return cst_Up;
        }

        public void setDis_CNT(String dis_CNT) {
            this.dis_CNT = dis_CNT;
        }
        public String getDis_CNT() {
            return dis_CNT;
        }

        public void setPrdNo(String prdNo) {
            this.prdNo = prdNo;
        }
        public String getPrdNo() {
            return prdNo;
        }

        public void setPrdUp(String prdUp) {
            this.prdUp = prdUp;
        }
        public String getPrdUp() {
            return prdUp;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public String getPrice() {
            return price;
        }

        public void setPrice_Id(String price_Id) {
            this.price_Id = price_Id;
        }
        public String getPrice_Id() {
            return price_Id;
        }

        @Override
        public String toString() {
            return "PrdtUp{" +
                    "ITM=" + ITM +
                    ", MIN_UP='" + MIN_UP + '\'' +
                    ", REM='" + REM + '\'' +
                    ", UPR='" + UPR + '\'' +
                    ", YN='" + YN + '\'' +
                    ", cst_Up='" + cst_Up + '\'' +
                    ", dis_CNT='" + dis_CNT + '\'' +
                    ", prdNo='" + prdNo + '\'' +
                    ", prdUp='" + prdUp + '\'' +
                    ", price='" + price + '\'' +
                    ", price_Id='" + price_Id + '\'' +
                    ", ZC_ZK='" + ZC_ZK + '\'' +
                    ", NAME_ZDY='" + NAME_ZDY + '\'' +
                    '}';
        }
    }
}
