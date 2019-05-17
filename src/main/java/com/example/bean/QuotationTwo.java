package com.example.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class QuotationTwo {
    private List<QuotationList> QuotationList;

    public List<QuotationList> getQuotationList() {
        return QuotationList;
    }

    public void setQuotationList(List<QuotationList> quotationList) {
        QuotationList = quotationList;
    }
    public class QuotationList implements Serializable {

        private Date AFFI_DD;
        private String AFFI_NO;
        private String BIL_TYPE;
        private String BZ_TYPE;
        private Date CHK_DD;
        private String CUS_CON_ITM;
        private String CUS_OS_NO;
        private String DB_ID;
        private String DEP_NO;
        private String DEP_RED;
        private String FILES;
        private String KH_NO;
        private String PRT_SW;
        private Date QT_DD;
        private String QT_NO;
        private String REM;
        private String SAL_NO;
        private String SEND_MTH;
        private String SER_NO;
        private String STYL_NO;
        private String USR;
        private String USR_CHK;
        private String con_Add;
        private String con_Crt;
        private String con_Per;
        private String con_Spa;
        private String con_Tel;
        private String cus_No;
        private String cust_Src;
        private String cust_Tel;
        private String deco_Com;
        private String hous_Type;
        private List<QuotationT> quotationT;
        private String remd;
        private String vip_NO;
        public void setAFFI_DD(Date AFFI_DD) {
            this.AFFI_DD = AFFI_DD;
        }
        public Date getAFFI_DD() {
            return AFFI_DD;
        }

        public void setAFFI_NO(String AFFI_NO) {
            this.AFFI_NO = AFFI_NO;
        }
        public String getAFFI_NO() {
            return AFFI_NO;
        }

        public void setBIL_TYPE(String BIL_TYPE) {
            this.BIL_TYPE = BIL_TYPE;
        }
        public String getBIL_TYPE() {
            return BIL_TYPE;
        }

        public void setBZ_TYPE(String BZ_TYPE) {
            this.BZ_TYPE = BZ_TYPE;
        }
        public String getBZ_TYPE() {
            return BZ_TYPE;
        }

        public void setCHK_DD(Date CHK_DD) {
            this.CHK_DD = CHK_DD;
        }
        public Date getCHK_DD() {
            return CHK_DD;
        }

        public void setCUS_CON_ITM(String CUS_CON_ITM) {
            this.CUS_CON_ITM = CUS_CON_ITM;
        }
        public String getCUS_CON_ITM() {
            return CUS_CON_ITM;
        }

        public void setCUS_OS_NO(String CUS_OS_NO) {
            this.CUS_OS_NO = CUS_OS_NO;
        }
        public String getCUS_OS_NO() {
            return CUS_OS_NO;
        }

        public void setDB_ID(String DB_ID) {
            this.DB_ID = DB_ID;
        }
        public String getDB_ID() {
            return DB_ID;
        }

        public void setDEP_NO(String DEP_NO) {
            this.DEP_NO = DEP_NO;
        }
        public String getDEP_NO() {
            return DEP_NO;
        }

        public void setDEP_RED(String DEP_RED) {
            this.DEP_RED = DEP_RED;
        }
        public String getDEP_RED() {
            return DEP_RED;
        }

        public void setFILES(String FILES) {
            this.FILES = FILES;
        }
        public String getFILES() {
            return FILES;
        }

        public void setKH_NO(String KH_NO) {
            this.KH_NO = KH_NO;
        }
        public String getKH_NO() {
            return KH_NO;
        }

        public void setPRT_SW(String PRT_SW) {
            this.PRT_SW = PRT_SW;
        }
        public String getPRT_SW() {
            return PRT_SW;
        }

        public void setQT_DD(Date QT_DD) {
            this.QT_DD = QT_DD;
        }
        public Date getQT_DD() {
            return QT_DD;
        }

        public void setQT_NO(String QT_NO) {
            this.QT_NO = QT_NO;
        }
        public String getQT_NO() {
            return QT_NO;
        }

        public void setREM(String REM) {
            this.REM = REM;
        }
        public String getREM() {
            return REM;
        }

        public void setSAL_NO(String SAL_NO) {
            this.SAL_NO = SAL_NO;
        }
        public String getSAL_NO() {
            return SAL_NO;
        }

        public void setSEND_MTH(String SEND_MTH) {
            this.SEND_MTH = SEND_MTH;
        }
        public String getSEND_MTH() {
            return SEND_MTH;
        }

        public void setSER_NO(String SER_NO) {
            this.SER_NO = SER_NO;
        }
        public String getSER_NO() {
            return SER_NO;
        }

        public void setSTYL_NO(String STYL_NO) {
            this.STYL_NO = STYL_NO;
        }
        public String getSTYL_NO() {
            return STYL_NO;
        }

        public void setUSR(String USR) {
            this.USR = USR;
        }
        public String getUSR() {
            return USR;
        }

        public void setUSR_CHK(String USR_CHK) {
            this.USR_CHK = USR_CHK;
        }
        public String getUSR_CHK() {
            return USR_CHK;
        }

        public void setCon_Add(String con_Add) {
            this.con_Add = con_Add;
        }
        public String getCon_Add() {
            return con_Add;
        }

        public void setCon_Crt(String con_Crt) {
            this.con_Crt = con_Crt;
        }
        public String getCon_Crt() {
            return con_Crt;
        }

        public void setCon_Per(String con_Per) {
            this.con_Per = con_Per;
        }
        public String getCon_Per() {
            return con_Per;
        }

        public void setCon_Spa(String con_Spa) {
            this.con_Spa = con_Spa;
        }
        public String getCon_Spa() {
            return con_Spa;
        }

        public void setCon_Tel(String con_Tel) {
            this.con_Tel = con_Tel;
        }
        public String getCon_Tel() {
            return con_Tel;
        }

        public void setCus_No(String cus_No) {
            this.cus_No = cus_No;
        }
        public String getCus_No() {
            return cus_No;
        }

        public void setCust_Src(String cust_Src) {
            this.cust_Src = cust_Src;
        }
        public String getCust_Src() {
            return cust_Src;
        }

        public void setCust_Tel(String cust_Tel) {
            this.cust_Tel = cust_Tel;
        }
        public String getCust_Tel() {
            return cust_Tel;
        }

        public void setDeco_Com(String deco_Com) {
            this.deco_Com = deco_Com;
        }
        public String getDeco_Com() {
            return deco_Com;
        }

        public void setHous_Type(String hous_Type) {
            this.hous_Type = hous_Type;
        }
        public String getHous_Type() {
            return hous_Type;
        }

        public void setQuotationT(List<QuotationT> quotationT) {
            this.quotationT = quotationT;
        }
        public List<QuotationT> getQuotationT() {
            return quotationT;
        }

        public void setRemd(String remd) {
            this.remd = remd;
        }
        public String getRemd() {
            return remd;
        }

        public void setVip_NO(String vip_NO) {
            this.vip_NO = vip_NO;
        }
        public String getVip_NO() {
            return vip_NO;
        }

    }
    public  static class QuotationT implements Serializable{
        private String AMTN;
        private String BZ_TYPE;
        private String CST_STD;
        private String DIS_CNT;
        private String EST_SUB;
        private String ISLP;
        private String ITM;
        private String OS_NO;
        private String OS_QTY;
        private String PAK_EXC;
        private String PAK_GW;
        private String PAK_MEAST;
        private String PAK_NW;
        private String PAK_QTY;
        private String PAK_UNIT;
        private String PRD_MARK;
        private String PRD_NAME;
        private String PRD_NO;
        private String PRICE_ID;
        private String QTY;
        private Date QT_DD;
        private String QT_NO;
        private String REMT;
        private String SPC;
        private String SPC_NO;
        private String SPC_QTY;
        private String SUB_GPR;
        private String UNIT;
        private String UP;
        private String UPR;
        private String UP_MIN;
        private String WH;
        private String prdUp;
        private String quotation;
        public void setAMTN(String AMTN) {
            this.AMTN = AMTN;
        }
        public String getAMTN() {
            return AMTN;
        }

        public void setBZ_TYPE(String BZ_TYPE) {
            this.BZ_TYPE = BZ_TYPE;
        }
        public String getBZ_TYPE() {
            return BZ_TYPE;
        }

        public void setCST_STD(String CST_STD) {
            this.CST_STD = CST_STD;
        }
        public String getCST_STD() {
            return CST_STD;
        }

        public void setDIS_CNT(String DIS_CNT) {
            this.DIS_CNT = DIS_CNT;
        }
        public String getDIS_CNT() {
            return DIS_CNT;
        }

        public void setEST_SUB(String EST_SUB) {
            this.EST_SUB = EST_SUB;
        }
        public String getEST_SUB() {
            return EST_SUB;
        }

        public void setISLP(String ISLP) {
            this.ISLP = ISLP;
        }
        public String getISLP() {
            return ISLP;
        }

        public void setITM(String ITM) {
            this.ITM = ITM;
        }
        public String getITM() {
            return ITM;
        }

        public void setOS_NO(String OS_NO) {
            this.OS_NO = OS_NO;
        }
        public String getOS_NO() {
            return OS_NO;
        }

        public void setOS_QTY(String OS_QTY) {
            this.OS_QTY = OS_QTY;
        }
        public String getOS_QTY() {
            return OS_QTY;
        }

        public void setPAK_EXC(String PAK_EXC) {
            this.PAK_EXC = PAK_EXC;
        }
        public String getPAK_EXC() {
            return PAK_EXC;
        }

        public void setPAK_GW(String PAK_GW) {
            this.PAK_GW = PAK_GW;
        }
        public String getPAK_GW() {
            return PAK_GW;
        }

        public void setPAK_MEAST(String PAK_MEAST) {
            this.PAK_MEAST = PAK_MEAST;
        }
        public String getPAK_MEAST() {
            return PAK_MEAST;
        }

        public void setPAK_NW(String PAK_NW) {
            this.PAK_NW = PAK_NW;
        }
        public String getPAK_NW() {
            return PAK_NW;
        }

        public void setPAK_QTY(String PAK_QTY) {
            this.PAK_QTY = PAK_QTY;
        }
        public String getPAK_QTY() {
            return PAK_QTY;
        }

        public void setPAK_UNIT(String PAK_UNIT) {
            this.PAK_UNIT = PAK_UNIT;
        }
        public String getPAK_UNIT() {
            return PAK_UNIT;
        }

        public void setPRD_MARK(String PRD_MARK) {
            this.PRD_MARK = PRD_MARK;
        }
        public String getPRD_MARK() {
            return PRD_MARK;
        }

        public void setPRD_NAME(String PRD_NAME) {
            this.PRD_NAME = PRD_NAME;
        }
        public String getPRD_NAME() {
            return PRD_NAME;
        }

        public void setPRD_NO(String PRD_NO) {
            this.PRD_NO = PRD_NO;
        }
        public String getPRD_NO() {
            return PRD_NO;
        }

        public void setPRICE_ID(String PRICE_ID) {
            this.PRICE_ID = PRICE_ID;
        }
        public String getPRICE_ID() {
            return PRICE_ID;
        }

        public void setQTY(String QTY) {
            this.QTY = QTY;
        }
        public String getQTY() {
            return QTY;
        }

        public void setQT_DD(Date QT_DD) {
            this.QT_DD = QT_DD;
        }
        public Date getQT_DD() {
            return QT_DD;
        }

        public void setQT_NO(String QT_NO) {
            this.QT_NO = QT_NO;
        }
        public String getQT_NO() {
            return QT_NO;
        }

        public void setREMT(String REMT) {
            this.REMT = REMT;
        }
        public String getREMT() {
            return REMT;
        }

        public void setSPC(String SPC) {
            this.SPC = SPC;
        }
        public String getSPC() {
            return SPC;
        }

        public void setSPC_NO(String SPC_NO) {
            this.SPC_NO = SPC_NO;
        }
        public String getSPC_NO() {
            return SPC_NO;
        }

        public void setSPC_QTY(String SPC_QTY) {
            this.SPC_QTY = SPC_QTY;
        }
        public String getSPC_QTY() {
            return SPC_QTY;
        }

        public void setSUB_GPR(String SUB_GPR) {
            this.SUB_GPR = SUB_GPR;
        }
        public String getSUB_GPR() {
            return SUB_GPR;
        }

        public void setUNIT(String UNIT) {
            this.UNIT = UNIT;
        }
        public String getUNIT() {
            return UNIT;
        }

        public void setUP(String UP) {
            this.UP = UP;
        }
        public String getUP() {
            return UP;
        }

        public void setUPR(String UPR) {
            this.UPR = UPR;
        }
        public String getUPR() {
            return UPR;
        }

        public void setUP_MIN(String UP_MIN) {
            this.UP_MIN = UP_MIN;
        }
        public String getUP_MIN() {
            return UP_MIN;
        }

        public void setWH(String WH) {
            this.WH = WH;
        }
        public String getWH() {
            return WH;
        }

        public void setPrdUp(String prdUp) {
            this.prdUp = prdUp;
        }
        public String getPrdUp() {
            return prdUp;
        }

        public void setQuotation(String quotation) {
            this.quotation = quotation;
        }
        public String getQuotation() {
            return quotation;
        }

    }
}
