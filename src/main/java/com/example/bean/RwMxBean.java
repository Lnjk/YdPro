package com.example.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/8 0008.
 */

public class RwMxBean {
    private List<Data> data;

    private int SumShowRow;

    private int SumRow;

    private int ShowRow;

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
    public class Data
    {
        private String beg_Date;

        private String chk_No;

        private Depco_Vip depco_Vip;

        private String end_Date;

        private String isTran;

        private String task_Date;

        private String task_Name;

        private String task_Points;

        private String usr_No;

        private String vt_No;

        public void setBeg_Date(String beg_Date){
            this.beg_Date = beg_Date;
        }
        public String getBeg_Date(){
            return this.beg_Date;
        }
        public void setChk_No(String chk_No){
            this.chk_No = chk_No;
        }
        public String getChk_No(){
            return this.chk_No;
        }
        public void setDepco_Vip(Depco_Vip depco_Vip){
            this.depco_Vip = depco_Vip;
        }
        public Depco_Vip getDepco_Vip(){
            return this.depco_Vip;
        }
        public void setEnd_Date(String end_Date){
            this.end_Date = end_Date;
        }
        public String getEnd_Date(){
            return this.end_Date;
        }
        public void setIsTran(String isTran){
            this.isTran = isTran;
        }
        public String getIsTran(){
            return this.isTran;
        }
        public void setTask_Date(String task_Date){
            this.task_Date = task_Date;
        }
        public String getTask_Date(){
            return this.task_Date;
        }
        public void setTask_Name(String task_Name){
            this.task_Name = task_Name;
        }
        public String getTask_Name(){
            return this.task_Name;
        }
        public void setTask_Points(String task_Points){
            this.task_Points = task_Points;
        }
        public String getTask_Points(){
            return this.task_Points;
        }
        public void setUsr_No(String usr_No){
            this.usr_No = usr_No;
        }
        public String getUsr_No(){
            return this.usr_No;
        }
        public void setVt_No(String vt_No){
            this.vt_No = vt_No;
        }
        public String getVt_No(){
            return this.vt_No;
        }
    }
    public class Depco_Vip
    {
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

        public void setAlteList(String alteList){
            this.alteList = alteList;
        }
        public String getAlteList(){
            return this.alteList;
        }
        public void setBank_Deposit(String bank_Deposit){
            this.bank_Deposit = bank_Deposit;
        }
        public String getBank_Deposit(){
            return this.bank_Deposit;
        }
        public void setBank_No(String bank_No){
            this.bank_No = bank_No;
        }
        public String getBank_No(){
            return this.bank_No;
        }
        public void setCard_Num(String card_Num){
            this.card_Num = card_Num;
        }
        public String getCard_Num(){
            return this.card_Num;
        }
        public void setComp(String comp){
            this.comp = comp;
        }
        public String getComp(){
            return this.comp;
        }
        public void setCon_Tel(String con_Tel){
            this.con_Tel = con_Tel;
        }
        public String getCon_Tel(){
            return this.con_Tel;
        }
        public void setCon_Tel2(String con_Tel2){
            this.con_Tel2 = con_Tel2;
        }
        public String getCon_Tel2(){
            return this.con_Tel2;
        }
        public void setDb_Id(String db_Id){
            this.db_Id = db_Id;
        }
        public String getDb_Id(){
            return this.db_Id;
        }
        public void setFor_Name(String for_Name){
            this.for_Name = for_Name;
        }
        public String getFor_Name(){
            return this.for_Name;
        }
        public void setGuat_name(String guat_name){
            this.guat_name = guat_name;
        }
        public String getGuat_name(){
            return this.guat_name;
        }
        public void setSal_No(String sal_No){
            this.sal_No = sal_No;
        }
        public String getSal_No(){
            return this.sal_No;
        }
        public void setSalesTerminal_No(String salesTerminal_No){
            this.salesTerminal_No = salesTerminal_No;
        }
        public String getSalesTerminal_No(){
            return this.salesTerminal_No;
        }
        public void setStat(String stat){
            this.stat = stat;
        }
        public String getStat(){
            return this.stat;
        }
        public void setType_Id(String type_Id){
            this.type_Id = type_Id;
        }
        public String getType_Id(){
            return this.type_Id;
        }
        public void setUser_CHK(String user_CHK){
            this.user_CHK = user_CHK;
        }
        public String getUser_CHK(){
            return this.user_CHK;
        }
        public void setUser_No(String user_No){
            this.user_No = user_No;
        }
        public String getUser_No(){
            return this.user_No;
        }
        public void setVip_NO(String vip_NO){
            this.vip_NO = vip_NO;
        }
        public String getVip_NO(){
            return this.vip_NO;
        }
        public void setVip_Name(String vip_Name){
            this.vip_Name = vip_Name;
        }
        public String getVip_Name(){
            return this.vip_Name;
        }
    }
}
