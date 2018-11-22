package com.example.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/11/7 0007.
 */

public class CustAllObjectInfos {
    private List<CustList> CustList;
    public void setCustList(List<CustList> CustList) {
        this.CustList = CustList;
    }
    public List<CustList> getCustList() {
        return CustList;
    }
    public class CustList implements Serializable{

        private Date card_DD;
        private String cons_lev;
        private String cus_No_ERP;
        private List<CustConObject> custConObject;
        private List<CustFollObject> custFollObject;
        private String cust_Acc;
        private String cust_Con;
        private String cust_Name;
        private String cust_No;
        private String cust_Pro;
        private String cust_Sex;
        private String cust_Src;
        private String cust_Tel;
        private String cust_age;
        private List<DecoVipObject> decoVipObject;
        private String deco_Bud;
        private String deco_Class;
        private String deco_Com;
        private Date deco_DD;
        private String deco_Sch;
        private String deco_Style;
        private String deco_Vip_ID;
        private String hous_Map;
        private String hous_Stru;
        private String hous_Type;
        private String mak_ERP;
        private String mate_App;
        private String sal_No_ERP;
        private String user_ID_ERP;
        public void setCard_DD(Date card_DD) {
            this.card_DD = card_DD;
        }
        public Date getCard_DD() {
            return card_DD;
        }

        public void setCons_lev(String cons_lev) {
            this.cons_lev = cons_lev;
        }
        public String getCons_lev() {
            return cons_lev;
        }

        public void setCus_No_ERP(String cus_No_ERP) {
            this.cus_No_ERP = cus_No_ERP;
        }
        public String getCus_No_ERP() {
            return cus_No_ERP;
        }

        public void setCustConObject(List<CustConObject> custConObject) {
            this.custConObject = custConObject;
        }
        public List<CustConObject> getCustConObject() {
            return custConObject;
        }

        public void setCustFollObject(List<CustFollObject> custFollObject) {
            this.custFollObject = custFollObject;
        }
        public List<CustFollObject> getCustFollObject() {
            return custFollObject;
        }

        public void setCust_Acc(String cust_Acc) {
            this.cust_Acc = cust_Acc;
        }
        public String getCust_Acc() {
            return cust_Acc;
        }

        public void setCust_Con(String cust_Con) {
            this.cust_Con = cust_Con;
        }
        public String getCust_Con() {
            return cust_Con;
        }

        public void setCust_Name(String cust_Name) {
            this.cust_Name = cust_Name;
        }
        public String getCust_Name() {
            return cust_Name;
        }

        public void setCust_No(String cust_No) {
            this.cust_No = cust_No;
        }
        public String getCust_No() {
            return cust_No;
        }

        public void setCust_Pro(String cust_Pro) {
            this.cust_Pro = cust_Pro;
        }
        public String getCust_Pro() {
            return cust_Pro;
        }

        public void setCust_Sex(String cust_Sex) {
            this.cust_Sex = cust_Sex;
        }
        public String getCust_Sex() {
            return cust_Sex;
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

        public void setCust_age(String cust_age) {
            this.cust_age = cust_age;
        }
        public String getCust_age() {
            return cust_age;
        }

        public void setDecoVipObject(List<DecoVipObject> decoVipObject) {
            this.decoVipObject = decoVipObject;
        }
        public List<DecoVipObject> getDecoVipObject() {
            return decoVipObject;
        }

        public void setDeco_Bud(String deco_Bud) {
            this.deco_Bud = deco_Bud;
        }
        public String getDeco_Bud() {
            return deco_Bud;
        }

        public void setDeco_Class(String deco_Class) {
            this.deco_Class = deco_Class;
        }
        public String getDeco_Class() {
            return deco_Class;
        }

        public void setDeco_Com(String deco_Com) {
            this.deco_Com = deco_Com;
        }
        public String getDeco_Com() {
            return deco_Com;
        }

        public void setDeco_DD(Date deco_DD) {
            this.deco_DD = deco_DD;
        }
        public Date getDeco_DD() {
            return deco_DD;
        }

        public void setDeco_Sch(String deco_Sch) {
            this.deco_Sch = deco_Sch;
        }
        public String getDeco_Sch() {
            return deco_Sch;
        }

        public void setDeco_Style(String deco_Style) {
            this.deco_Style = deco_Style;
        }
        public String getDeco_Style() {
            return deco_Style;
        }

        public void setDeco_Vip_ID(String deco_Vip_ID) {
            this.deco_Vip_ID = deco_Vip_ID;
        }
        public String getDeco_Vip_ID() {
            return deco_Vip_ID;
        }

        public void setHous_Map(String hous_Map) {
            this.hous_Map = hous_Map;
        }
        public String getHous_Map() {
            return hous_Map;
        }

        public void setHous_Stru(String hous_Stru) {
            this.hous_Stru = hous_Stru;
        }
        public String getHous_Stru() {
            return hous_Stru;
        }

        public void setHous_Type(String hous_Type) {
            this.hous_Type = hous_Type;
        }
        public String getHous_Type() {
            return hous_Type;
        }

        public void setMak_ERP(String mak_ERP) {
            this.mak_ERP = mak_ERP;
        }
        public String getMak_ERP() {
            return mak_ERP;
        }

        public void setMate_App(String mate_App) {
            this.mate_App = mate_App;
        }
        public String getMate_App() {
            return mate_App;
        }

        public void setSal_No_ERP(String sal_No_ERP) {
            this.sal_No_ERP = sal_No_ERP;
        }
        public String getSal_No_ERP() {
            return sal_No_ERP;
        }

        public void setUser_ID_ERP(String user_ID_ERP) {
            this.user_ID_ERP = user_ID_ERP;
        }
        public String getUser_ID_ERP() {
            return user_ID_ERP;
        }

        @Override
        public String toString() {
            return "CustList{" +
                    "card_DD=" + card_DD +
                    ", cons_lev='" + cons_lev + '\'' +
                    ", cus_No_ERP='" + cus_No_ERP + '\'' +
                    ", custConObject=" + custConObject +
                    ", custFollObject=" + custFollObject +
                    ", cust_Acc='" + cust_Acc + '\'' +
                    ", cust_Con='" + cust_Con + '\'' +
                    ", cust_Name='" + cust_Name + '\'' +
                    ", cust_No='" + cust_No + '\'' +
                    ", cust_Pro='" + cust_Pro + '\'' +
                    ", cust_Sex='" + cust_Sex + '\'' +
                    ", cust_Src='" + cust_Src + '\'' +
                    ", cust_Tel='" + cust_Tel + '\'' +
                    ", cust_age='" + cust_age + '\'' +
                    ", decoVipObject=" + decoVipObject +
                    ", deco_Bud='" + deco_Bud + '\'' +
                    ", deco_Class='" + deco_Class + '\'' +
                    ", deco_Com='" + deco_Com + '\'' +
                    ", deco_DD=" + deco_DD +
                    ", deco_Sch='" + deco_Sch + '\'' +
                    ", deco_Style='" + deco_Style + '\'' +
                    ", deco_Vip_ID='" + deco_Vip_ID + '\'' +
                    ", hous_Map='" + hous_Map + '\'' +
                    ", hous_Stru='" + hous_Stru + '\'' +
                    ", hous_Type='" + hous_Type + '\'' +
                    ", mak_ERP='" + mak_ERP + '\'' +
                    ", mate_App='" + mate_App + '\'' +
                    ", sal_No_ERP='" + sal_No_ERP + '\'' +
                    ", user_ID_ERP='" + user_ID_ERP + '\'' +
                    '}';
        }
    }
    public class DecoVipObject implements Serializable{

//        private List<AlteList> alteList;//时间包含T
        private String bank_Deposit;
        private String bank_No;
        private String card_Num;
        private String comp;
        private String con_Tel;
        private String con_Tel2;
        private String db_Id;
        private String for_Name;
        private String guat_name;
        private String sal_No;
        private String salesTerminal_No;
        private String stat;
        private String type_Id;
        private String user_CHK;
        private String user_No;
        private String vip_NO;
        private String vip_Name;
//        public void setAlteList(List<AlteList> alteList) {
//            this.alteList = alteList;
//        }
//        public List<AlteList> getAlteList() {
//            return alteList;
//        }

        public void setBank_Deposit(String bank_Deposit) {
            this.bank_Deposit = bank_Deposit;
        }
        public String getBank_Deposit() {
            return bank_Deposit;
        }

        public void setBank_No(String bank_No) {
            this.bank_No = bank_No;
        }
        public String getBank_No() {
            return bank_No;
        }

        public void setCard_Num(String card_Num) {
            this.card_Num = card_Num;
        }
        public String getCard_Num() {
            return card_Num;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }
        public String getComp() {
            return comp;
        }

        public void setCon_Tel(String con_Tel) {
            this.con_Tel = con_Tel;
        }
        public String getCon_Tel() {
            return con_Tel;
        }

        public void setCon_Tel2(String con_Tel2) {
            this.con_Tel2 = con_Tel2;
        }
        public String getCon_Tel2() {
            return con_Tel2;
        }

        public void setDb_Id(String db_Id) {
            this.db_Id = db_Id;
        }
        public String getDb_Id() {
            return db_Id;
        }

        public void setFor_Name(String for_Name) {
            this.for_Name = for_Name;
        }
        public String getFor_Name() {
            return for_Name;
        }

        public void setGuat_name(String guat_name) {
            this.guat_name = guat_name;
        }
        public String getGuat_name() {
            return guat_name;
        }

        public void setSal_No(String sal_No) {
            this.sal_No = sal_No;
        }
        public String getSal_No() {
            return sal_No;
        }

        public void setSalesTerminal_No(String salesTerminal_No) {
            this.salesTerminal_No = salesTerminal_No;
        }
        public String getSalesTerminal_No() {
            return salesTerminal_No;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }
        public String getStat() {
            return stat;
        }

        public void setType_Id(String type_Id) {
            this.type_Id = type_Id;
        }
        public String getType_Id() {
            return type_Id;
        }

        public void setUser_CHK(String user_CHK) {
            this.user_CHK = user_CHK;
        }
        public String getUser_CHK() {
            return user_CHK;
        }

        public void setUser_No(String user_No) {
            this.user_No = user_No;
        }
        public String getUser_No() {
            return user_No;
        }

        public void setVip_NO(String vip_NO) {
            this.vip_NO = vip_NO;
        }
        public String getVip_NO() {
            return vip_NO;
        }

        public void setVip_Name(String vip_Name) {
            this.vip_Name = vip_Name;
        }
        public String getVip_Name() {
            return vip_Name;
        }

        @Override
        public String toString() {
            return "DecoVipObject{" +
//                    "alteList=" + alteList +
                    ", bank_Deposit='" + bank_Deposit + '\'' +
                    ", bank_No='" + bank_No + '\'' +
                    ", card_Num='" + card_Num + '\'' +
                    ", comp='" + comp + '\'' +
                    ", con_Tel='" + con_Tel + '\'' +
                    ", con_Tel2='" + con_Tel2 + '\'' +
                    ", db_Id='" + db_Id + '\'' +
                    ", for_Name='" + for_Name + '\'' +
                    ", guat_name='" + guat_name + '\'' +
                    ", sal_No='" + sal_No + '\'' +
                    ", salesTerminal_No='" + salesTerminal_No + '\'' +
                    ", stat='" + stat + '\'' +
                    ", type_Id='" + type_Id + '\'' +
                    ", user_CHK='" + user_CHK + '\'' +
                    ", user_No='" + user_No + '\'' +
                    ", vip_NO='" + vip_NO + '\'' +
                    ", vip_Name='" + vip_Name + '\'' +
                    '}';
        }
    }
    public class AlteList implements Serializable{

        private String ITM;
        private String action;
        private String alte_Cont;
        private Date alte_DD;
        private String db_Id;
        private String user_no;
        private String vip_No;
        public void setITM(String ITM) {
            this.ITM = ITM;
        }
        public String getITM() {
            return ITM;
        }

        public void setAction(String action) {
            this.action = action;
        }
        public String getAction() {
            return action;
        }

        public void setAlte_Cont(String alte_Cont) {
            this.alte_Cont = alte_Cont;
        }
        public String getAlte_Cont() {
            return alte_Cont;
        }

        public void setAlte_DD(Date alte_DD) {
            this.alte_DD = alte_DD;
        }
        public Date getAlte_DD() {
            return alte_DD;
        }

        public void setDb_Id(String db_Id) {
            this.db_Id = db_Id;
        }
        public String getDb_Id() {
            return db_Id;
        }

        public void setUser_no(String user_no) {
            this.user_no = user_no;
        }
        public String getUser_no() {
            return user_no;
        }

        public void setVip_No(String vip_No) {
            this.vip_No = vip_No;
        }
        public String getVip_No() {
            return vip_No;
        }

        @Override
        public String toString() {
            return "AlteList{" +
                    "ITM='" + ITM + '\'' +
                    ", action='" + action + '\'' +
                    ", alte_Cont='" + alte_Cont + '\'' +
                    ", alte_DD=" + alte_DD +
                    ", db_Id='" + db_Id + '\'' +
                    ", user_no='" + user_no + '\'' +
                    ", vip_No='" + vip_No + '\'' +
                    '}';
        }
    }
    public class CustFollObject implements Serializable{

        private String cust_Acc;
        private String cust_No;
        private String foll_Case;
        private Date foll_DD;
        private String foll_No;
        private String foll_Per;
        private String foll_Them;
        private String foll_Way;
        private String stag_Class;
        private String user_No;
        public void setCust_Acc(String cust_Acc) {
            this.cust_Acc = cust_Acc;
        }
        public String getCust_Acc() {
            return cust_Acc;
        }

        public void setCust_No(String cust_No) {
            this.cust_No = cust_No;
        }
        public String getCust_No() {
            return cust_No;
        }

        public void setFoll_Case(String foll_Case) {
            this.foll_Case = foll_Case;
        }
        public String getFoll_Case() {
            return foll_Case;
        }

        public void setFoll_DD(Date foll_DD) {
            this.foll_DD = foll_DD;
        }
        public Date getFoll_DD() {
            return foll_DD;
        }

        public void setFoll_No(String foll_No) {
            this.foll_No = foll_No;
        }
        public String getFoll_No() {
            return foll_No;
        }

        public void setFoll_Per(String foll_Per) {
            this.foll_Per = foll_Per;
        }
        public String getFoll_Per() {
            return foll_Per;
        }

        public void setFoll_Them(String foll_Them) {
            this.foll_Them = foll_Them;
        }
        public String getFoll_Them() {
            return foll_Them;
        }

        public void setFoll_Way(String foll_Way) {
            this.foll_Way = foll_Way;
        }
        public String getFoll_Way() {
            return foll_Way;
        }

        public void setStag_Class(String stag_Class) {
            this.stag_Class = stag_Class;
        }
        public String getStag_Class() {
            return stag_Class;
        }

        public void setUser_No(String user_No) {
            this.user_No = user_No;
        }
        public String getUser_No() {
            return user_No;
        }

        @Override
        public String toString() {
            return "CustFollObject{" +
                    "cust_Acc='" + cust_Acc + '\'' +
                    ", cust_No='" + cust_No + '\'' +
                    ", foll_Case='" + foll_Case + '\'' +
                    ", foll_DD=" + foll_DD +
                    ", foll_No='" + foll_No + '\'' +
                    ", foll_Per='" + foll_Per + '\'' +
                    ", foll_Them='" + foll_Them + '\'' +
                    ", foll_Way='" + foll_Way + '\'' +
                    ", stag_Class='" + stag_Class + '\'' +
                    ", user_No='" + user_No + '\'' +
                    '}';
        }
    }
    public class CustConObject implements Serializable{

        private String con_Add;
        private String con_Crt;
        private String con_Per;
        private String con_Spa;
        private String con_Tel;
        private String cust_Acc;
        private String cust_No;
        private boolean def;
        private String iTM;
        private String user_ID;
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

        public void setCust_Acc(String cust_Acc) {
            this.cust_Acc = cust_Acc;
        }
        public String getCust_Acc() {
            return cust_Acc;
        }

        public void setCust_No(String cust_No) {
            this.cust_No = cust_No;
        }
        public String getCust_No() {
            return cust_No;
        }

        public void setDef(boolean def) {
            this.def = def;
        }
        public boolean getDef() {
            return def;
        }

        public void setITM(String iTM) {
            this.iTM = iTM;
        }
        public String getITM() {
            return iTM;
        }

        public void setUser_ID(String user_ID) {
            this.user_ID = user_ID;
        }
        public String getUser_ID() {
            return user_ID;
        }

        @Override
        public String toString() {
            return "CustConObject{" +
                    "con_Add='" + con_Add + '\'' +
                    ", con_Crt='" + con_Crt + '\'' +
                    ", con_Per='" + con_Per + '\'' +
                    ", con_Spa='" + con_Spa + '\'' +
                    ", con_Tel='" + con_Tel + '\'' +
                    ", cust_Acc='" + cust_Acc + '\'' +
                    ", cust_No='" + cust_No + '\'' +
                    ", def=" + def +
                    ", iTM='" + iTM + '\'' +
                    ", user_ID='" + user_ID + '\'' +
                    '}';
        }
    }
}
