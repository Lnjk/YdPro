package com.example.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/4 0004.
 */

public class JfMxBean {
    private List<Data> data;
    private List<QueryResult> queryResult;

    private int SumShowRow;

    private int SumRow;

    private int ShowRow;

    public List<QueryResult> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<QueryResult> queryResult) {
        this.queryResult = queryResult;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getSumShowRow() {
        return SumShowRow;
    }

    public void setSumShowRow(int sumShowRow) {
        SumShowRow = sumShowRow;
    }

    public int getSumRow() {
        return SumRow;
    }

    public void setSumRow(int sumRow) {
        SumRow = sumRow;
    }

    public int getShowRow() {
        return ShowRow;
    }

    public void setShowRow(int showRow) {
        ShowRow = showRow;
    }
    public class Data {
        private String amtn;
        private String chk_No;

        private String cls_Id;

        private Depco_Vip depco_Vip;

        private String item_Name;

        private String points;

        private String points_Date;

        private String points_Type;

        private String rem;

        private String source_No;

        private String usr_No;

        private String vp_No;

        public String getAmtn() {
            return amtn;
        }

        public void setAmtn(String amtn) {
            this.amtn = amtn;
        }

        public String getChk_No() {
            return chk_No;
        }

        public void setChk_No(String chk_No) {
            this.chk_No = chk_No;
        }

        public String getCls_Id() {
            return cls_Id;
        }

        public void setCls_Id(String cls_Id) {
            this.cls_Id = cls_Id;
        }

        public Depco_Vip getDepco_Vip() {
            return depco_Vip;
        }

        public void setDepco_Vip(Depco_Vip depco_Vip) {
            this.depco_Vip = depco_Vip;
        }

        public String getItem_Name() {
            return item_Name;
        }

        public void setItem_Name(String item_Name) {
            this.item_Name = item_Name;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getPoints_Date() {
            return points_Date;
        }

        public void setPoints_Date(String points_Date) {
            this.points_Date = points_Date;
        }

        public String getPoints_Type() {
            return points_Type;
        }

        public void setPoints_Type(String points_Type) {
            this.points_Type = points_Type;
        }

        public String getRem() {
            return rem;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }

        public String getSource_No() {
            return source_No;
        }

        public void setSource_No(String source_No) {
            this.source_No = source_No;
        }

        public String getUsr_No() {
            return usr_No;
        }

        public void setUsr_No(String usr_No) {
            this.usr_No = usr_No;
        }

        public String getVp_No() {
            return vp_No;
        }

        public void setVp_No(String vp_No) {
            this.vp_No = vp_No;
        }
    }
    public class QueryResult {
        private String chk_No;

        private String cls_Id;

        private Depco_Vip depco_Vip;

        private String item_Name;

        private String points;

        private String points_Date;

        private String points_Type;

        private String rem;

        private String source_No;

        private String usr_No;

        private String vp_No;

        public String getChk_No() {
            return chk_No;
        }

        public void setChk_No(String chk_No) {
            this.chk_No = chk_No;
        }

        public String getCls_Id() {
            return cls_Id;
        }

        public void setCls_Id(String cls_Id) {
            this.cls_Id = cls_Id;
        }

        public Depco_Vip getDepco_Vip() {
            return depco_Vip;
        }

        public void setDepco_Vip(Depco_Vip depco_Vip) {
            this.depco_Vip = depco_Vip;
        }

        public String getItem_Name() {
            return item_Name;
        }

        public void setItem_Name(String item_Name) {
            this.item_Name = item_Name;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getPoints_Date() {
            return points_Date;
        }

        public void setPoints_Date(String points_Date) {
            this.points_Date = points_Date;
        }

        public String getPoints_Type() {
            return points_Type;
        }

        public void setPoints_Type(String points_Type) {
            this.points_Type = points_Type;
        }

        public String getRem() {
            return rem;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }

        public String getSource_No() {
            return source_No;
        }

        public void setSource_No(String source_No) {
            this.source_No = source_No;
        }

        public String getUsr_No() {
            return usr_No;
        }

        public void setUsr_No(String usr_No) {
            this.usr_No = usr_No;
        }

        public String getVp_No() {
            return vp_No;
        }

        public void setVp_No(String vp_No) {
            this.vp_No = vp_No;
        }
    }
    public class Depco_Vip {
        private String alteList;

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

        public String getAlteList() {
            return alteList;
        }

        public void setAlteList(String alteList) {
            this.alteList = alteList;
        }

        public String getBank_Deposit() {
            return bank_Deposit;
        }

        public void setBank_Deposit(String bank_Deposit) {
            this.bank_Deposit = bank_Deposit;
        }

        public String getBank_No() {
            return bank_No;
        }

        public void setBank_No(String bank_No) {
            this.bank_No = bank_No;
        }

        public String getCard_Num() {
            return card_Num;
        }

        public void setCard_Num(String card_Num) {
            this.card_Num = card_Num;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getCon_Tel() {
            return con_Tel;
        }

        public void setCon_Tel(String con_Tel) {
            this.con_Tel = con_Tel;
        }

        public String getCon_Tel2() {
            return con_Tel2;
        }

        public void setCon_Tel2(String con_Tel2) {
            this.con_Tel2 = con_Tel2;
        }

        public String getDb_Id() {
            return db_Id;
        }

        public void setDb_Id(String db_Id) {
            this.db_Id = db_Id;
        }

        public String getFor_Name() {
            return for_Name;
        }

        public void setFor_Name(String for_Name) {
            this.for_Name = for_Name;
        }

        public String getGuat_name() {
            return guat_name;
        }

        public void setGuat_name(String guat_name) {
            this.guat_name = guat_name;
        }

        public String getSal_No() {
            return sal_No;
        }

        public void setSal_No(String sal_No) {
            this.sal_No = sal_No;
        }

        public String getSalesTerminal_No() {
            return salesTerminal_No;
        }

        public void setSalesTerminal_No(String salesTerminal_No) {
            this.salesTerminal_No = salesTerminal_No;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getType_Id() {
            return type_Id;
        }

        public void setType_Id(String type_Id) {
            this.type_Id = type_Id;
        }

        public String getUser_CHK() {
            return user_CHK;
        }

        public void setUser_CHK(String user_CHK) {
            this.user_CHK = user_CHK;
        }

        public String getUser_No() {
            return user_No;
        }

        public void setUser_No(String user_No) {
            this.user_No = user_No;
        }

        public String getVip_NO() {
            return vip_NO;
        }

        public void setVip_NO(String vip_NO) {
            this.vip_NO = vip_NO;
        }

        public String getVip_Name() {
            return vip_Name;
        }

        public void setVip_Name(String vip_Name) {
            this.vip_Name = vip_Name;
        }
    }
}
