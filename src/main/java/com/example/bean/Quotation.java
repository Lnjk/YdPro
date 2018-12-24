package com.example.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/4 0004.
 */

public class Quotation {
//    private String QT_DD;
//    private String QT_NO;
//    private String Cust_Src;
//    private String Remd;
//    private String BIL_TYPE;//单据类别
//    private String CUS_OS_NO; //合同单号：
//    private String CUS_NO; //终端ID：
//    private String CUS_NAME; //销售终端名字：
//    private String DEP_NO; //销售部门ID：
//    private String DEP_NAME; //销售部门名字：
//    private String SAL_NO; //销售顾问ID：
//    private String SAL_NAME; //销售顾问名字：
//    private String SAL_Tel;//顾问电话：
//    private String SECE;//服务管家：
//    private String SECE_TEL;//管家电话：
//    private String STYL;//制图设计：
//    private String STYL_TEL;//设计电话：
//    private String PLAW;//审图业务
//    private String AFFI;//确单业务：
//    private String DEP_RED;//预收货款：
//    private String PRT_SW;//打印状态：
//    private String Cust_Name;//客户姓名：
//    private String Cust_Con; //客户电话：
//    private String Hous_Type;//楼盘类型
//    private String Deco_Com;//装修公司：
//    private String SEND_MTH;//交货方式：
//    private String Con_Per;//收货人：
//    private String Con_Tel;//收货电话:
//    private String SHPP_ADD;//配送地址：
//    private String Con_Crt;//省/市
//    private String Con_Spa;//区/县
//    private String Con_Add;//Map详细地址
//    private String SHPP;//配送信息：
//    private String REMD;//销售备注
//    private String chk_dd;//审核日期：
//    private String AFFI_DD;//确单日期：
//    private String usr;//制单人：
//    private String usr_chk;//审核人
private List<QuotationList> QuotationList;

    public void setQuotationList(List<QuotationList> QuotationList){
        this.QuotationList = QuotationList;
    }
    public List<QuotationList> getQuotationList(){
        return this.QuotationList;
    }
    public class QuotationList implements Serializable
    {
        private String AFFI;

        private String AFFI_DD;

        private String BIL_TYPE;

        private String CUS_NAME;

        private String CUS_NO;

        private String CUS_OS_NO;

        private String DB_ID;

        private String DEP_NAME;

        private String DEP_NO;

        private String DEP_RED;

        private String PLAW;

        private String PRT_SW;

        private String QT_DD;

        private String QT_DD2;

        private String QT_NO;

        private String REM;

        private String SAL_NAME;

        private String SAL_NO;

        private String SAL_Tel;

        private String SECE;

        private String SECE_TEL;

        private String SEND_MTH;

        private String SHPP;

        private String STYL;

        private String STYL_TEL;

        private String chk_dd;

        private String con_Add;

        private String con_Crt;

        private String con_Per;

        private String con_Spa;

        private String con_Tel;

        private String cust_Con;

        private String cust_Name;

        private String cust_Src;

        private String deco_Com;

        private String hous_Type;

        private List<QuotationT> quotationT;

        private String remd;

        private String usr;

        private String usr_chk;

        public void setAFFI(String AFFI){
            this.AFFI = AFFI;
        }
        public String getAFFI(){
            return this.AFFI;
        }
        public void setAFFI_DD(String AFFI_DD){
            this.AFFI_DD = AFFI_DD;
        }
        public String getAFFI_DD(){
            return this.AFFI_DD;
        }
        public void setBIL_TYPE(String BIL_TYPE){
            this.BIL_TYPE = BIL_TYPE;
        }
        public String getBIL_TYPE(){
            return this.BIL_TYPE;
        }
        public void setCUS_NAME(String CUS_NAME){
            this.CUS_NAME = CUS_NAME;
        }
        public String getCUS_NAME(){
            return this.CUS_NAME;
        }
        public void setCUS_NO(String CUS_NO){
            this.CUS_NO = CUS_NO;
        }
        public String getCUS_NO(){
            return this.CUS_NO;
        }
        public void setCUS_OS_NO(String CUS_OS_NO){
            this.CUS_OS_NO = CUS_OS_NO;
        }
        public String getCUS_OS_NO(){
            return this.CUS_OS_NO;
        }
        public void setDB_ID(String DB_ID){
            this.DB_ID = DB_ID;
        }
        public String getDB_ID(){
            return this.DB_ID;
        }
        public void setDEP_NAME(String DEP_NAME){
            this.DEP_NAME = DEP_NAME;
        }
        public String getDEP_NAME(){
            return this.DEP_NAME;
        }
        public void setDEP_NO(String DEP_NO){
            this.DEP_NO = DEP_NO;
        }
        public String getDEP_NO(){
            return this.DEP_NO;
        }
        public void setDEP_RED(String DEP_RED){
            this.DEP_RED = DEP_RED;
        }
        public String getDEP_RED(){
            return this.DEP_RED;
        }
        public void setPLAW(String PLAW){
            this.PLAW = PLAW;
        }
        public String getPLAW(){
            return this.PLAW;
        }
        public void setPRT_SW(String PRT_SW){
            this.PRT_SW = PRT_SW;
        }
        public String getPRT_SW(){
            return this.PRT_SW;
        }
        public void setQT_DD(String QT_DD){
            this.QT_DD = QT_DD;
        }
        public String getQT_DD(){
            return this.QT_DD;
        }
        public void setQT_DD2(String QT_DD2){
            this.QT_DD2 = QT_DD2;
        }
        public String getQT_DD2(){
            return this.QT_DD2;
        }
        public void setQT_NO(String QT_NO){
            this.QT_NO = QT_NO;
        }
        public String getQT_NO(){
            return this.QT_NO;
        }
        public void setREM(String REM){
            this.REM = REM;
        }
        public String getREM(){
            return this.REM;
        }
        public void setSAL_NAME(String SAL_NAME){
            this.SAL_NAME = SAL_NAME;
        }
        public String getSAL_NAME(){
            return this.SAL_NAME;
        }
        public void setSAL_NO(String SAL_NO){
            this.SAL_NO = SAL_NO;
        }
        public String getSAL_NO(){
            return this.SAL_NO;
        }
        public void setSAL_Tel(String SAL_Tel){
            this.SAL_Tel = SAL_Tel;
        }
        public String getSAL_Tel(){
            return this.SAL_Tel;
        }
        public void setSECE(String SECE){
            this.SECE = SECE;
        }
        public String getSECE(){
            return this.SECE;
        }
        public void setSECE_TEL(String SECE_TEL){
            this.SECE_TEL = SECE_TEL;
        }
        public String getSECE_TEL(){
            return this.SECE_TEL;
        }
        public void setSEND_MTH(String SEND_MTH){
            this.SEND_MTH = SEND_MTH;
        }
        public String getSEND_MTH(){
            return this.SEND_MTH;
        }
        public void setSHPP(String SHPP){
            this.SHPP = SHPP;
        }
        public String getSHPP(){
            return this.SHPP;
        }
        public void setSTYL(String STYL){
            this.STYL = STYL;
        }
        public String getSTYL(){
            return this.STYL;
        }
        public void setSTYL_TEL(String STYL_TEL){
            this.STYL_TEL = STYL_TEL;
        }
        public String getSTYL_TEL(){
            return this.STYL_TEL;
        }
        public void setChk_dd(String chk_dd){
            this.chk_dd = chk_dd;
        }
        public String getChk_dd(){
            return this.chk_dd;
        }
        public void setCon_Add(String con_Add){
            this.con_Add = con_Add;
        }
        public String getCon_Add(){
            return this.con_Add;
        }
        public void setCon_Crt(String con_Crt){
            this.con_Crt = con_Crt;
        }
        public String getCon_Crt(){
            return this.con_Crt;
        }
        public void setCon_Per(String con_Per){
            this.con_Per = con_Per;
        }
        public String getCon_Per(){
            return this.con_Per;
        }
        public void setCon_Spa(String con_Spa){
            this.con_Spa = con_Spa;
        }
        public String getCon_Spa(){
            return this.con_Spa;
        }
        public void setCon_Tel(String con_Tel){
            this.con_Tel = con_Tel;
        }
        public String getCon_Tel(){
            return this.con_Tel;
        }
        public void setCust_Con(String cust_Con){
            this.cust_Con = cust_Con;
        }
        public String getCust_Con(){
            return this.cust_Con;
        }
        public void setCust_Name(String cust_Name){
            this.cust_Name = cust_Name;
        }
        public String getCust_Name(){
            return this.cust_Name;
        }
        public void setCust_Src(String cust_Src){
            this.cust_Src = cust_Src;
        }
        public String getCust_Src(){
            return this.cust_Src;
        }
        public void setDeco_Com(String deco_Com){
            this.deco_Com = deco_Com;
        }
        public String getDeco_Com(){
            return this.deco_Com;
        }
        public void setHous_Type(String hous_Type){
            this.hous_Type = hous_Type;
        }
        public String getHous_Type(){
            return this.hous_Type;
        }
        public void setQuotationT(List<QuotationT> quotationT){
            this.quotationT = quotationT;
        }
        public List<QuotationT> getQuotationT(){
            return this.quotationT;
        }
        public void setRemd(String remd){
            this.remd = remd;
        }
        public String getRemd(){
            return this.remd;
        }
        public void setUsr(String usr){
            this.usr = usr;
        }
        public String getUsr(){
            return this.usr;
        }
        public void setUsr_chk(String usr_chk){
            this.usr_chk = usr_chk;
        }
        public String getUsr_chk(){
            return this.usr_chk;
        }

        @Override
        public String toString() {
            return "QuotationList{" +
                    "AFFI='" + AFFI + '\'' +
                    ", AFFI_DD='" + AFFI_DD + '\'' +
                    ", BIL_TYPE='" + BIL_TYPE + '\'' +
                    ", CUS_NAME='" + CUS_NAME + '\'' +
                    ", CUS_NO='" + CUS_NO + '\'' +
                    ", CUS_OS_NO='" + CUS_OS_NO + '\'' +
                    ", DB_ID='" + DB_ID + '\'' +
                    ", DEP_NAME='" + DEP_NAME + '\'' +
                    ", DEP_NO='" + DEP_NO + '\'' +
                    ", DEP_RED='" + DEP_RED + '\'' +
                    ", PLAW='" + PLAW + '\'' +
                    ", PRT_SW='" + PRT_SW + '\'' +
                    ", QT_DD='" + QT_DD + '\'' +
                    ", QT_DD2='" + QT_DD2 + '\'' +
                    ", QT_NO='" + QT_NO + '\'' +
                    ", REM='" + REM + '\'' +
                    ", SAL_NAME='" + SAL_NAME + '\'' +
                    ", SAL_NO='" + SAL_NO + '\'' +
                    ", SAL_Tel='" + SAL_Tel + '\'' +
                    ", SECE='" + SECE + '\'' +
                    ", SECE_TEL='" + SECE_TEL + '\'' +
                    ", SEND_MTH='" + SEND_MTH + '\'' +
                    ", SHPP='" + SHPP + '\'' +
                    ", STYL='" + STYL + '\'' +
                    ", STYL_TEL='" + STYL_TEL + '\'' +
                    ", chk_dd='" + chk_dd + '\'' +
                    ", con_Add='" + con_Add + '\'' +
                    ", con_Crt='" + con_Crt + '\'' +
                    ", con_Per='" + con_Per + '\'' +
                    ", con_Spa='" + con_Spa + '\'' +
                    ", con_Tel='" + con_Tel + '\'' +
                    ", cust_Con='" + cust_Con + '\'' +
                    ", cust_Name='" + cust_Name + '\'' +
                    ", cust_Src='" + cust_Src + '\'' +
                    ", deco_Com='" + deco_Com + '\'' +
                    ", hous_Type='" + hous_Type + '\'' +
                    ", quotationT=" + quotationT +
                    ", remd='" + remd + '\'' +
                    ", usr='" + usr + '\'' +
                    ", usr_chk='" + usr_chk + '\'' +
                    '}';
        }
    }
    public static class QuotationT implements Serializable
    {


        private String AFFIX;

        private String AMTN;

        private String COM_NW;

        private String CST_STD;

        private String DIS_CNT;

        private String EST_SUB;

        private String ITM;

        private String OS_QTY;

        private String PAKG;

        private String PAK_EXC;

        private String PAK_NW;

        private String PAK_QTY;

        private String PAK_UNIT;

        private String PRD_NAME;

        private String PRD_NO;

        private String PRICE_ID;

        private String QTY;

        private String QT_DD;

        private String QT_NO;

        private String REM;

        private String RPD_MARK;

        private String SC_QTY;

        private String SPC;

        private String SUB_GPR;

        private String UNIT;

        private String UP;

        private String UPR;

        private String UP_MIN;

        private String WH;

        private String prdUp;

        private String quotation;

        public void setAFFIX(String AFFIX){
            this.AFFIX = AFFIX;
        }
        public String getAFFIX(){
            return this.AFFIX;
        }
        public void setAMTN(String AMTN){
            this.AMTN = AMTN;
        }
        public String getAMTN(){
            return this.AMTN;
        }
        public void setCOM_NW(String COM_NW){
            this.COM_NW = COM_NW;
        }
        public String getCOM_NW(){
            return this.COM_NW;
        }
        public void setCST_STD(String CST_STD){
            this.CST_STD = CST_STD;
        }
        public String getCST_STD(){
            return this.CST_STD;
        }
        public void setDIS_CNT(String DIS_CNT){
            this.DIS_CNT = DIS_CNT;
        }
        public String getDIS_CNT(){
            return this.DIS_CNT;
        }
        public void setEST_SUB(String EST_SUB){
            this.EST_SUB = EST_SUB;
        }
        public String getEST_SUB(){
            return this.EST_SUB;
        }
        public void setITM(String ITM){
            this.ITM = ITM;
        }
        public String getITM(){
            return this.ITM;
        }
        public void setOS_QTY(String OS_QTY){
            this.OS_QTY = OS_QTY;
        }
        public String getOS_QTY(){
            return this.OS_QTY;
        }
        public void setPAKG(String PAKG){
            this.PAKG = PAKG;
        }
        public String getPAKG(){
            return this.PAKG;
        }
        public void setPAK_EXC(String PAK_EXC){
            this.PAK_EXC = PAK_EXC;
        }
        public String getPAK_EXC(){
            return this.PAK_EXC;
        }
        public void setPAK_NW(String PAK_NW){
            this.PAK_NW = PAK_NW;
        }
        public String getPAK_NW(){
            return this.PAK_NW;
        }
        public void setPAK_QTY(String PAK_QTY){
            this.PAK_QTY = PAK_QTY;
        }
        public String getPAK_QTY(){
            return this.PAK_QTY;
        }
        public void setPAK_UNIT(String PAK_UNIT){
            this.PAK_UNIT = PAK_UNIT;
        }
        public String getPAK_UNIT(){
            return this.PAK_UNIT;
        }
        public void setPRD_NAME(String PRD_NAME){
            this.PRD_NAME = PRD_NAME;
        }
        public String getPRD_NAME(){
            return this.PRD_NAME;
        }
        public void setPRD_NO(String PRD_NO){
            this.PRD_NO = PRD_NO;
        }
        public String getPRD_NO(){
            return this.PRD_NO;
        }
        public void setPRICE_ID(String PRICE_ID){
            this.PRICE_ID = PRICE_ID;
        }
        public String getPRICE_ID(){
            return this.PRICE_ID;
        }
        public void setQTY(String QTY){
            this.QTY = QTY;
        }
        public String getQTY(){
            return this.QTY;
        }
        public void setQT_DD(String QT_DD){
            this.QT_DD = QT_DD;
        }
        public String getQT_DD(){
            return this.QT_DD;
        }
        public void setQT_NO(String QT_NO){
            this.QT_NO = QT_NO;
        }
        public String getQT_NO(){
            return this.QT_NO;
        }
        public void setREM(String REM){
            this.REM = REM;
        }
        public String getREM(){
            return this.REM;
        }
        public void setRPD_MARK(String RPD_MARK){
            this.RPD_MARK = RPD_MARK;
        }
        public String getRPD_MARK(){
            return this.RPD_MARK;
        }
        public void setSC_QTY(String SC_QTY){
            this.SC_QTY = SC_QTY;
        }
        public String getSC_QTY(){
            return this.SC_QTY;
        }
        public void setSPC(String SPC){
            this.SPC = SPC;
        }
        public String getSPC(){
            return this.SPC;
        }
        public void setSUB_GPR(String SUB_GPR){
            this.SUB_GPR = SUB_GPR;
        }
        public String getSUB_GPR(){
            return this.SUB_GPR;
        }
        public void setUNIT(String UNIT){
            this.UNIT = UNIT;
        }
        public String getUNIT(){
            return this.UNIT;
        }
        public void setUP(String UP){
            this.UP = UP;
        }
        public String getUP(){
            return this.UP;
        }
        public void setUPR(String UPR){
            this.UPR = UPR;
        }
        public String getUPR(){
            return this.UPR;
        }
        public void setUP_MIN(String UP_MIN){
            this.UP_MIN = UP_MIN;
        }
        public String getUP_MIN(){
            return this.UP_MIN;
        }
        public void setWH(String WH){
            this.WH = WH;
        }
        public String getWH(){
            return this.WH;
        }
        public void setPrdUp(String prdUp){
            this.prdUp = prdUp;
        }
        public String getPrdUp(){
            return this.prdUp;
        }
        public void setQuotation(String quotation){
            this.quotation = quotation;
        }
        public String getQuotation(){
            return this.quotation;
        }

        @Override
        public String toString() {
            return "QuotationT{" +
                    "AFFIX='" + AFFIX + '\'' +
                    ", AMTN='" + AMTN + '\'' +
                    ", COM_NW='" + COM_NW + '\'' +
                    ", CST_STD='" + CST_STD + '\'' +
                    ", DIS_CNT='" + DIS_CNT + '\'' +
                    ", EST_SUB='" + EST_SUB + '\'' +
                    ", ITM='" + ITM + '\'' +
                    ", OS_QTY='" + OS_QTY + '\'' +
                    ", PAKG='" + PAKG + '\'' +
                    ", PAK_EXC='" + PAK_EXC + '\'' +
                    ", PAK_NW='" + PAK_NW + '\'' +
                    ", PAK_QTY='" + PAK_QTY + '\'' +
                    ", PAK_UNIT='" + PAK_UNIT + '\'' +
                    ", PRD_NAME='" + PRD_NAME + '\'' +
                    ", PRD_NO='" + PRD_NO + '\'' +
                    ", PRICE_ID='" + PRICE_ID + '\'' +
                    ", QTY='" + QTY + '\'' +
                    ", QT_DD='" + QT_DD + '\'' +
                    ", QT_NO='" + QT_NO + '\'' +
                    ", REM='" + REM + '\'' +
                    ", RPD_MARK='" + RPD_MARK + '\'' +
                    ", SC_QTY='" + SC_QTY + '\'' +
                    ", SPC='" + SPC + '\'' +
                    ", SUB_GPR='" + SUB_GPR + '\'' +
                    ", UNIT='" + UNIT + '\'' +
                    ", UP='" + UP + '\'' +
                    ", UPR='" + UPR + '\'' +
                    ", UP_MIN='" + UP_MIN + '\'' +
                    ", WH='" + WH + '\'' +
                    ", prdUp='" + prdUp + '\'' +
                    ", quotation='" + quotation + '\'' +
                    '}';
        }
    }
}
