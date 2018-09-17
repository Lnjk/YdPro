package com.example.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by 李宁 on 2018/6/26.
 */

public class PriceLoadInfo {
    private boolean RLO;
    private List<Prdt> Prdt;

    public void setRLO(boolean RLO) {
        this.RLO = RLO;
    }

    public boolean getRLO() {
        return RLO;
    }

    public void setPrdt(List<Prdt> Prdt) {
        this.Prdt = Prdt;
    }

    public List<Prdt> getPrdt() {
        return Prdt;
    }

    public static class Prdt {

        private String XH;//自定义
        private String ZC_DJ;//自定义
        private String ZC_ZK;//自定义
        private String ZC_ZXQK;//自定义
        private String ZC_BZXX;//自定义
        private String ACC_NO_IN;
        private String ACC_NO_OUT;
        private String AMTN_YG;
        private String BAR_CODE;
        private String BOM_ID;
        private String CCC;
        private String CHK_BAT;
        private String CHK_MAN;
        private String CHK_NUM;
        private String CHK_TAX;
        private String CHK_YL;
        private String CLS_DATE;
        private String CODENAME;
        private String CST_ADD;
        private String CST_SAL;
        private String CST_STD;
        private String DEP;
        private String DFORMULA;
        private String DFORMULA1;
        private String DFU_UT;
        private String EFF_DD;
        private String FORMULA;
        private String IDX1;
        private String IDX2;
        private String INTQTY;
        private String INV_NAME;
        private String KND;
        private String LOCK_PRE_DAYS;
        private String LOCK_STOCK_QTY;
        private String MASTER_MA;
        private String MRK;
        private String MTL_SORT;
        private String NAME;
        private String NAME_ENG;
        private String NEED_DAYS;
        private String NOUSE_DD;
        private String OBJ_TYPE;
        private String OLEFIELD;
        private int PAK_EXC;
        private String PAK_GW;
        private String PAK_MEAST;
        private String PAK_MEAST_UNIT;
        private String PAK_NW;
        private String PAK_UNIT;
        private String PAK_WEIGHT_UNIT;
        private int PDM_ID;
        private int PDM_VERSION;
        private String PIC;
        private int PK2_QTY;
        private String PK2_UT;
        private int PK2_WT;
        private int PK3_QTY;
        private String PK3_UT;
        private int PK3_WT;
        private String PL;
        private String PO_DAYS;
        private String PO_ID;
        private Date PRD_DD;
        private String PRD_LEVEL;
        private String PRD_NO;
        private String PRD_NO_EXC;
        private String PRD_PACK;
        private String PRE_DAYS;
        private String QTY_LOW;
        private int QTY_MAX;
        private int QTY_MIN;
        private String QTY_MIN_ML;
        private String QTY_ODR;
        private String RB_RATE;
        private String REM;
        private String SAL_NO;
        private String SELNAME;
        private String SELNO;
        private String SMALLPIC;
        private String SNM;
        private String SPC;
        private String SPC_PRD;
        private String SPC_TAX;
        private String STOP_ID;
        private String SUP1;
        private String SUP2;
        private String TIME_UNIT;
        private String TW_ID;
        private String UBPR;
        private double UPR;
        private int UPR_LEVEL1;
        private int UPR_LEVEL2;
        private int UPR_LEVEL3;
        private int UPR_LEVEL4;
        private int UPR_LEVEL5;
        private double UPR_TP;
        private String UP_MAX;


        //        private int UP_MIN;
//        private int UP_SAL;
        private double UP_MIN;
        private double UP_SAL;
        private String USED_TIME;
        private String USR;
        private String UT;
        private String UT1;
        private String UT_TIME;
        private int VALID_DAYS;
        private String WH;
        private String WTUT;

        public Prdt(String XH, String PRD_NO, String NAME, double UP_SAL, double UPR, double UP_MIN, String ZC_DJ, String ZC_ZK, String ZC_ZXQK, String REM) {
            this.XH = XH;
            this.PRD_NO = PRD_NO;
            this.NAME = NAME;
            this.UP_SAL = UP_SAL;
            this.UPR = UPR;
            this.UP_MIN = UP_MIN;
            this.ZC_DJ = ZC_DJ;
            this.ZC_ZK = ZC_ZK;
            this.ZC_ZXQK = ZC_ZXQK;
            this.REM = REM;
        }

        public String getXH() {
            return XH;
        }

        public void setXH(String XH) {
            this.XH = XH;
        }

        public String getZC_DJ() {
            return ZC_DJ;
        }

        public void setZC_DJ(String ZC_DJ) {
            this.ZC_DJ = ZC_DJ;
        }

        public String getZC_ZK() {
            return ZC_ZK;
        }

        public void setZC_ZK(String ZC_ZK) {
            this.ZC_ZK = ZC_ZK;
        }

        public String getZC_ZXQK() {
            return ZC_ZXQK;
        }

        public void setZC_ZXQK(String ZC_ZXQK) {
            this.ZC_ZXQK = ZC_ZXQK;
        }

        public String getZC_BZXX() {
            return ZC_BZXX;
        }

        public void setZC_BZXX(String ZC_BZXX) {
            this.ZC_BZXX = ZC_BZXX;
        }

        public void setACC_NO_IN(String ACC_NO_IN) {
            this.ACC_NO_IN = ACC_NO_IN;
        }

        public String getACC_NO_IN() {
            return ACC_NO_IN;
        }

        public void setACC_NO_OUT(String ACC_NO_OUT) {
            this.ACC_NO_OUT = ACC_NO_OUT;
        }

        public String getACC_NO_OUT() {
            return ACC_NO_OUT;
        }

        public void setAMTN_YG(String AMTN_YG) {
            this.AMTN_YG = AMTN_YG;
        }

        public String getAMTN_YG() {
            return AMTN_YG;
        }

        public void setBAR_CODE(String BAR_CODE) {
            this.BAR_CODE = BAR_CODE;
        }

        public String getBAR_CODE() {
            return BAR_CODE;
        }

        public void setBOM_ID(String BOM_ID) {
            this.BOM_ID = BOM_ID;
        }

        public String getBOM_ID() {
            return BOM_ID;
        }

        public void setCCC(String CCC) {
            this.CCC = CCC;
        }

        public String getCCC() {
            return CCC;
        }

        public void setCHK_BAT(String CHK_BAT) {
            this.CHK_BAT = CHK_BAT;
        }

        public String getCHK_BAT() {
            return CHK_BAT;
        }

        public void setCHK_MAN(String CHK_MAN) {
            this.CHK_MAN = CHK_MAN;
        }

        public String getCHK_MAN() {
            return CHK_MAN;
        }

        public void setCHK_NUM(String CHK_NUM) {
            this.CHK_NUM = CHK_NUM;
        }

        public String getCHK_NUM() {
            return CHK_NUM;
        }

        public void setCHK_TAX(String CHK_TAX) {
            this.CHK_TAX = CHK_TAX;
        }

        public String getCHK_TAX() {
            return CHK_TAX;
        }

        public void setCHK_YL(String CHK_YL) {
            this.CHK_YL = CHK_YL;
        }

        public String getCHK_YL() {
            return CHK_YL;
        }

        public void setCLS_DATE(String CLS_DATE) {
            this.CLS_DATE = CLS_DATE;
        }

        public String getCLS_DATE() {
            return CLS_DATE;
        }

        public void setCODENAME(String CODENAME) {
            this.CODENAME = CODENAME;
        }

        public String getCODENAME() {
            return CODENAME;
        }

        public void setCST_ADD(String CST_ADD) {
            this.CST_ADD = CST_ADD;
        }

        public String getCST_ADD() {
            return CST_ADD;
        }

        public void setCST_SAL(String CST_SAL) {
            this.CST_SAL = CST_SAL;
        }

        public String getCST_SAL() {
            return CST_SAL;
        }

        public void setCST_STD(String CST_STD) {
            this.CST_STD = CST_STD;
        }

        public String getCST_STD() {
            return CST_STD;
        }

        public void setDEP(String DEP) {
            this.DEP = DEP;
        }

        public String getDEP() {
            return DEP;
        }

        public void setDFORMULA(String DFORMULA) {
            this.DFORMULA = DFORMULA;
        }

        public String getDFORMULA() {
            return DFORMULA;
        }

        public void setDFORMULA1(String DFORMULA1) {
            this.DFORMULA1 = DFORMULA1;
        }

        public String getDFORMULA1() {
            return DFORMULA1;
        }

        public void setDFU_UT(String DFU_UT) {
            this.DFU_UT = DFU_UT;
        }

        public String getDFU_UT() {
            return DFU_UT;
        }

        public void setEFF_DD(String EFF_DD) {
            this.EFF_DD = EFF_DD;
        }

        public String getEFF_DD() {
            return EFF_DD;
        }

        public void setFORMULA(String FORMULA) {
            this.FORMULA = FORMULA;
        }

        public String getFORMULA() {
            return FORMULA;
        }

        public void setIDX1(String IDX1) {
            this.IDX1 = IDX1;
        }

        public String getIDX1() {
            return IDX1;
        }

        public void setIDX2(String IDX2) {
            this.IDX2 = IDX2;
        }

        public String getIDX2() {
            return IDX2;
        }

        public void setINTQTY(String INTQTY) {
            this.INTQTY = INTQTY;
        }

        public String getINTQTY() {
            return INTQTY;
        }

        public void setINV_NAME(String INV_NAME) {
            this.INV_NAME = INV_NAME;
        }

        public String getINV_NAME() {
            return INV_NAME;
        }

        public void setKND(String KND) {
            this.KND = KND;
        }

        public String getKND() {
            return KND;
        }

        public void setLOCK_PRE_DAYS(String LOCK_PRE_DAYS) {
            this.LOCK_PRE_DAYS = LOCK_PRE_DAYS;
        }

        public String getLOCK_PRE_DAYS() {
            return LOCK_PRE_DAYS;
        }

        public void setLOCK_STOCK_QTY(String LOCK_STOCK_QTY) {
            this.LOCK_STOCK_QTY = LOCK_STOCK_QTY;
        }

        public String getLOCK_STOCK_QTY() {
            return LOCK_STOCK_QTY;
        }

        public void setMASTER_MA(String MASTER_MA) {
            this.MASTER_MA = MASTER_MA;
        }

        public String getMASTER_MA() {
            return MASTER_MA;
        }

        public void setMRK(String MRK) {
            this.MRK = MRK;
        }

        public String getMRK() {
            return MRK;
        }

        public void setMTL_SORT(String MTL_SORT) {
            this.MTL_SORT = MTL_SORT;
        }

        public String getMTL_SORT() {
            return MTL_SORT;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME_ENG(String NAME_ENG) {
            this.NAME_ENG = NAME_ENG;
        }

        public String getNAME_ENG() {
            return NAME_ENG;
        }

        public void setNEED_DAYS(String NEED_DAYS) {
            this.NEED_DAYS = NEED_DAYS;
        }

        public String getNEED_DAYS() {
            return NEED_DAYS;
        }

        public void setNOUSE_DD(String NOUSE_DD) {
            this.NOUSE_DD = NOUSE_DD;
        }

        public String getNOUSE_DD() {
            return NOUSE_DD;
        }

        public void setOBJ_TYPE(String OBJ_TYPE) {
            this.OBJ_TYPE = OBJ_TYPE;
        }

        public String getOBJ_TYPE() {
            return OBJ_TYPE;
        }

        public void setOLEFIELD(String OLEFIELD) {
            this.OLEFIELD = OLEFIELD;
        }

        public String getOLEFIELD() {
            return OLEFIELD;
        }

        public void setPAK_EXC(int PAK_EXC) {
            this.PAK_EXC = PAK_EXC;
        }

        public int getPAK_EXC() {
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

        public void setPAK_MEAST_UNIT(String PAK_MEAST_UNIT) {
            this.PAK_MEAST_UNIT = PAK_MEAST_UNIT;
        }

        public String getPAK_MEAST_UNIT() {
            return PAK_MEAST_UNIT;
        }

        public void setPAK_NW(String PAK_NW) {
            this.PAK_NW = PAK_NW;
        }

        public String getPAK_NW() {
            return PAK_NW;
        }

        public void setPAK_UNIT(String PAK_UNIT) {
            this.PAK_UNIT = PAK_UNIT;
        }

        public String getPAK_UNIT() {
            return PAK_UNIT;
        }

        public void setPAK_WEIGHT_UNIT(String PAK_WEIGHT_UNIT) {
            this.PAK_WEIGHT_UNIT = PAK_WEIGHT_UNIT;
        }

        public String getPAK_WEIGHT_UNIT() {
            return PAK_WEIGHT_UNIT;
        }

        public void setPDM_ID(int PDM_ID) {
            this.PDM_ID = PDM_ID;
        }

        public int getPDM_ID() {
            return PDM_ID;
        }

        public void setPDM_VERSION(int PDM_VERSION) {
            this.PDM_VERSION = PDM_VERSION;
        }

        public int getPDM_VERSION() {
            return PDM_VERSION;
        }

        public void setPIC(String PIC) {
            this.PIC = PIC;
        }

        public String getPIC() {
            return PIC;
        }

        public void setPK2_QTY(int PK2_QTY) {
            this.PK2_QTY = PK2_QTY;
        }

        public int getPK2_QTY() {
            return PK2_QTY;
        }

        public void setPK2_UT(String PK2_UT) {
            this.PK2_UT = PK2_UT;
        }

        public String getPK2_UT() {
            return PK2_UT;
        }

        public void setPK2_WT(int PK2_WT) {
            this.PK2_WT = PK2_WT;
        }

        public int getPK2_WT() {
            return PK2_WT;
        }

        public void setPK3_QTY(int PK3_QTY) {
            this.PK3_QTY = PK3_QTY;
        }

        public int getPK3_QTY() {
            return PK3_QTY;
        }

        public void setPK3_UT(String PK3_UT) {
            this.PK3_UT = PK3_UT;
        }

        public String getPK3_UT() {
            return PK3_UT;
        }

        public void setPK3_WT(int PK3_WT) {
            this.PK3_WT = PK3_WT;
        }

        public int getPK3_WT() {
            return PK3_WT;
        }

        public void setPL(String PL) {
            this.PL = PL;
        }

        public String getPL() {
            return PL;
        }

        public void setPO_DAYS(String PO_DAYS) {
            this.PO_DAYS = PO_DAYS;
        }

        public String getPO_DAYS() {
            return PO_DAYS;
        }

        public void setPO_ID(String PO_ID) {
            this.PO_ID = PO_ID;
        }

        public String getPO_ID() {
            return PO_ID;
        }

        public void setPRD_DD(Date PRD_DD) {
            this.PRD_DD = PRD_DD;
        }

        public Date getPRD_DD() {
            return PRD_DD;
        }

        public void setPRD_LEVEL(String PRD_LEVEL) {
            this.PRD_LEVEL = PRD_LEVEL;
        }

        public String getPRD_LEVEL() {
            return PRD_LEVEL;
        }

        public void setPRD_NO(String PRD_NO) {
            this.PRD_NO = PRD_NO;
        }

        public String getPRD_NO() {
            return PRD_NO;
        }

        public void setPRD_NO_EXC(String PRD_NO_EXC) {
            this.PRD_NO_EXC = PRD_NO_EXC;
        }

        public String getPRD_NO_EXC() {
            return PRD_NO_EXC;
        }

        public void setPRD_PACK(String PRD_PACK) {
            this.PRD_PACK = PRD_PACK;
        }

        public String getPRD_PACK() {
            return PRD_PACK;
        }

        public void setPRE_DAYS(String PRE_DAYS) {
            this.PRE_DAYS = PRE_DAYS;
        }

        public String getPRE_DAYS() {
            return PRE_DAYS;
        }

        public void setQTY_LOW(String QTY_LOW) {
            this.QTY_LOW = QTY_LOW;
        }

        public String getQTY_LOW() {
            return QTY_LOW;
        }

        public void setQTY_MAX(int QTY_MAX) {
            this.QTY_MAX = QTY_MAX;
        }

        public int getQTY_MAX() {
            return QTY_MAX;
        }

        public void setQTY_MIN(int QTY_MIN) {
            this.QTY_MIN = QTY_MIN;
        }

        public int getQTY_MIN() {
            return QTY_MIN;
        }

        public void setQTY_MIN_ML(String QTY_MIN_ML) {
            this.QTY_MIN_ML = QTY_MIN_ML;
        }

        public String getQTY_MIN_ML() {
            return QTY_MIN_ML;
        }

        public void setQTY_ODR(String QTY_ODR) {
            this.QTY_ODR = QTY_ODR;
        }

        public String getQTY_ODR() {
            return QTY_ODR;
        }

        public void setRB_RATE(String RB_RATE) {
            this.RB_RATE = RB_RATE;
        }

        public String getRB_RATE() {
            return RB_RATE;
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

        public void setSELNAME(String SELNAME) {
            this.SELNAME = SELNAME;
        }

        public String getSELNAME() {
            return SELNAME;
        }

        public void setSELNO(String SELNO) {
            this.SELNO = SELNO;
        }

        public String getSELNO() {
            return SELNO;
        }

        public void setSMALLPIC(String SMALLPIC) {
            this.SMALLPIC = SMALLPIC;
        }

        public String getSMALLPIC() {
            return SMALLPIC;
        }

        public void setSNM(String SNM) {
            this.SNM = SNM;
        }

        public String getSNM() {
            return SNM;
        }

        public void setSPC(String SPC) {
            this.SPC = SPC;
        }

        public String getSPC() {
            return SPC;
        }

        public void setSPC_PRD(String SPC_PRD) {
            this.SPC_PRD = SPC_PRD;
        }

        public String getSPC_PRD() {
            return SPC_PRD;
        }

        public void setSPC_TAX(String SPC_TAX) {
            this.SPC_TAX = SPC_TAX;
        }

        public String getSPC_TAX() {
            return SPC_TAX;
        }

        public void setSTOP_ID(String STOP_ID) {
            this.STOP_ID = STOP_ID;
        }

        public String getSTOP_ID() {
            return STOP_ID;
        }

        public void setSUP1(String SUP1) {
            this.SUP1 = SUP1;
        }

        public String getSUP1() {
            return SUP1;
        }

        public void setSUP2(String SUP2) {
            this.SUP2 = SUP2;
        }

        public String getSUP2() {
            return SUP2;
        }

        public void setTIME_UNIT(String TIME_UNIT) {
            this.TIME_UNIT = TIME_UNIT;
        }

        public String getTIME_UNIT() {
            return TIME_UNIT;
        }

        public void setTW_ID(String TW_ID) {
            this.TW_ID = TW_ID;
        }

        public String getTW_ID() {
            return TW_ID;
        }

        public void setUBPR(String UBPR) {
            this.UBPR = UBPR;
        }

        public String getUBPR() {
            return UBPR;
        }

        public void setUPR(double UPR) {
            this.UPR = UPR;
        }

        public double getUPR() {
            return UPR;
        }

        public void setUPR_LEVEL1(int UPR_LEVEL1) {
            this.UPR_LEVEL1 = UPR_LEVEL1;
        }

        public int getUPR_LEVEL1() {
            return UPR_LEVEL1;
        }

        public void setUPR_LEVEL2(int UPR_LEVEL2) {
            this.UPR_LEVEL2 = UPR_LEVEL2;
        }

        public int getUPR_LEVEL2() {
            return UPR_LEVEL2;
        }

        public void setUPR_LEVEL3(int UPR_LEVEL3) {
            this.UPR_LEVEL3 = UPR_LEVEL3;
        }

        public int getUPR_LEVEL3() {
            return UPR_LEVEL3;
        }

        public void setUPR_LEVEL4(int UPR_LEVEL4) {
            this.UPR_LEVEL4 = UPR_LEVEL4;
        }

        public int getUPR_LEVEL4() {
            return UPR_LEVEL4;
        }

        public void setUPR_LEVEL5(int UPR_LEVEL5) {
            this.UPR_LEVEL5 = UPR_LEVEL5;
        }

        public int getUPR_LEVEL5() {
            return UPR_LEVEL5;
        }

        public void setUPR_TP(double UPR_TP) {
            this.UPR_TP = UPR_TP;
        }

        public double getUPR_TP() {
            return UPR_TP;
        }

        public void setUP_MAX(String UP_MAX) {
            this.UP_MAX = UP_MAX;
        }

        public String getUP_MAX() {
            return UP_MAX;
        }

        //        public void setUP_MIN(int UP_MIN) {
//            this.UP_MIN = UP_MIN;
//        }
//        public int getUP_MIN() {
//            return UP_MIN;
//        }
//
//        public void setUP_SAL(int UP_SAL) {
//            this.UP_SAL = UP_SAL;
//        }
//        public int getUP_SAL() {
//            return UP_SAL;
//        }
        public void setUP_MIN(double UP_MIN) {
            this.UP_MIN = UP_MIN;
        }

        public void setUP_SAL(double UP_SAL) {
            this.UP_SAL = UP_SAL;
        }

        public double getUP_MIN() {
            return UP_MIN;
        }

        public double getUP_SAL() {
            return UP_SAL;
        }

        public void setUSED_TIME(String USED_TIME) {
            this.USED_TIME = USED_TIME;
        }

        public String getUSED_TIME() {
            return USED_TIME;
        }

        public void setUSR(String USR) {
            this.USR = USR;
        }

        public String getUSR() {
            return USR;
        }

        public void setUT(String UT) {
            this.UT = UT;
        }

        public String getUT() {
            return UT;
        }

        public void setUT1(String UT1) {
            this.UT1 = UT1;
        }

        public String getUT1() {
            return UT1;
        }

        public void setUT_TIME(String UT_TIME) {
            this.UT_TIME = UT_TIME;
        }

        public String getUT_TIME() {
            return UT_TIME;
        }

        public void setVALID_DAYS(int VALID_DAYS) {
            this.VALID_DAYS = VALID_DAYS;
        }

        public int getVALID_DAYS() {
            return VALID_DAYS;
        }

        public void setWH(String WH) {
            this.WH = WH;
        }

        public String getWH() {
            return WH;
        }

        public void setWTUT(String WTUT) {
            this.WTUT = WTUT;
        }

        public String getWTUT() {
            return WTUT;
        }

        @Override
        public String toString() {
            return "Prdt{" +
                    "ACC_NO_IN='" + ACC_NO_IN + '\'' +
                    ", ACC_NO_OUT='" + ACC_NO_OUT + '\'' +
                    ", AMTN_YG='" + AMTN_YG + '\'' +
                    ", BAR_CODE='" + BAR_CODE + '\'' +
                    ", BOM_ID='" + BOM_ID + '\'' +
                    ", CCC='" + CCC + '\'' +
                    ", CHK_BAT='" + CHK_BAT + '\'' +
                    ", CHK_MAN='" + CHK_MAN + '\'' +
                    ", CHK_NUM='" + CHK_NUM + '\'' +
                    ", CHK_TAX='" + CHK_TAX + '\'' +
                    ", CHK_YL='" + CHK_YL + '\'' +
                    ", CLS_DATE='" + CLS_DATE + '\'' +
                    ", CODENAME='" + CODENAME + '\'' +
                    ", CST_ADD='" + CST_ADD + '\'' +
                    ", CST_SAL='" + CST_SAL + '\'' +
                    ", CST_STD='" + CST_STD + '\'' +
                    ", DEP='" + DEP + '\'' +
                    ", DFORMULA='" + DFORMULA + '\'' +
                    ", DFORMULA1='" + DFORMULA1 + '\'' +
                    ", DFU_UT='" + DFU_UT + '\'' +
                    ", EFF_DD='" + EFF_DD + '\'' +
                    ", FORMULA='" + FORMULA + '\'' +
                    ", IDX1='" + IDX1 + '\'' +
                    ", IDX2='" + IDX2 + '\'' +
                    ", INTQTY='" + INTQTY + '\'' +
                    ", INV_NAME='" + INV_NAME + '\'' +
                    ", KND='" + KND + '\'' +
                    ", LOCK_PRE_DAYS='" + LOCK_PRE_DAYS + '\'' +
                    ", LOCK_STOCK_QTY='" + LOCK_STOCK_QTY + '\'' +
                    ", MASTER_MA='" + MASTER_MA + '\'' +
                    ", MRK='" + MRK + '\'' +
                    ", MTL_SORT='" + MTL_SORT + '\'' +
                    ", NAME='" + NAME + '\'' +
                    ", NAME_ENG='" + NAME_ENG + '\'' +
                    ", NEED_DAYS='" + NEED_DAYS + '\'' +
                    ", NOUSE_DD='" + NOUSE_DD + '\'' +
                    ", OBJ_TYPE='" + OBJ_TYPE + '\'' +
                    ", OLEFIELD='" + OLEFIELD + '\'' +
                    ", PAK_EXC=" + PAK_EXC +
                    ", PAK_GW='" + PAK_GW + '\'' +
                    ", PAK_MEAST='" + PAK_MEAST + '\'' +
                    ", PAK_MEAST_UNIT='" + PAK_MEAST_UNIT + '\'' +
                    ", PAK_NW='" + PAK_NW + '\'' +
                    ", PAK_UNIT='" + PAK_UNIT + '\'' +
                    ", PAK_WEIGHT_UNIT='" + PAK_WEIGHT_UNIT + '\'' +
                    ", PDM_ID=" + PDM_ID +
                    ", PDM_VERSION=" + PDM_VERSION +
                    ", PIC='" + PIC + '\'' +
                    ", PK2_QTY=" + PK2_QTY +
                    ", PK2_UT='" + PK2_UT + '\'' +
                    ", PK2_WT=" + PK2_WT +
                    ", PK3_QTY=" + PK3_QTY +
                    ", PK3_UT='" + PK3_UT + '\'' +
                    ", PK3_WT=" + PK3_WT +
                    ", PL='" + PL + '\'' +
                    ", PO_DAYS='" + PO_DAYS + '\'' +
                    ", PO_ID='" + PO_ID + '\'' +
                    ", PRD_DD=" + PRD_DD +
                    ", PRD_LEVEL='" + PRD_LEVEL + '\'' +
                    ", PRD_NO='" + PRD_NO + '\'' +
                    ", PRD_NO_EXC='" + PRD_NO_EXC + '\'' +
                    ", PRD_PACK='" + PRD_PACK + '\'' +
                    ", PRE_DAYS='" + PRE_DAYS + '\'' +
                    ", QTY_LOW='" + QTY_LOW + '\'' +
                    ", QTY_MAX=" + QTY_MAX +
                    ", QTY_MIN=" + QTY_MIN +
                    ", QTY_MIN_ML='" + QTY_MIN_ML + '\'' +
                    ", QTY_ODR='" + QTY_ODR + '\'' +
                    ", RB_RATE='" + RB_RATE + '\'' +
                    ", REM='" + REM + '\'' +
                    ", SAL_NO='" + SAL_NO + '\'' +
                    ", SELNAME='" + SELNAME + '\'' +
                    ", SELNO='" + SELNO + '\'' +
                    ", SMALLPIC='" + SMALLPIC + '\'' +
                    ", SNM='" + SNM + '\'' +
                    ", SPC='" + SPC + '\'' +
                    ", SPC_PRD='" + SPC_PRD + '\'' +
                    ", SPC_TAX='" + SPC_TAX + '\'' +
                    ", STOP_ID='" + STOP_ID + '\'' +
                    ", SUP1='" + SUP1 + '\'' +
                    ", SUP2='" + SUP2 + '\'' +
                    ", TIME_UNIT='" + TIME_UNIT + '\'' +
                    ", TW_ID='" + TW_ID + '\'' +
                    ", UBPR='" + UBPR + '\'' +
                    ", UPR=" + UPR +
                    ", UPR_LEVEL1=" + UPR_LEVEL1 +
                    ", UPR_LEVEL2=" + UPR_LEVEL2 +
                    ", UPR_LEVEL3=" + UPR_LEVEL3 +
                    ", UPR_LEVEL4=" + UPR_LEVEL4 +
                    ", UPR_LEVEL5=" + UPR_LEVEL5 +
                    ", UPR_TP=" + UPR_TP +
                    ", UP_MAX='" + UP_MAX + '\'' +
                    ", UP_MIN=" + UP_MIN +
                    ", UP_SAL=" + UP_SAL +
                    ", USED_TIME='" + USED_TIME + '\'' +
                    ", USR='" + USR + '\'' +
                    ", UT='" + UT + '\'' +
                    ", UT1='" + UT1 + '\'' +
                    ", UT_TIME='" + UT_TIME + '\'' +
                    ", VALID_DAYS=" + VALID_DAYS +
                    ", WH='" + WH + '\'' +
                    ", WTUT='" + WTUT + '\'' +
                    '}';
        }
    }
}
