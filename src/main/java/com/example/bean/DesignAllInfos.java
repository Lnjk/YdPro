package com.example.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/5 0005.
 */

public class DesignAllInfos {
    private List<VipList> VipList;

    public void setVipList(List<VipList> VipList){
        this.VipList = VipList;
    }
    public List<VipList> getVipList(){
        return this.VipList;
    }

    @Override
    public String toString() {
        return "DesignAllInfos{" +
                "VipList=" + VipList +
                '}';
    }

    public class VipList implements Serializable
    {
        private List<AlteList> alteList;

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

        public void setAlteList(List<AlteList> alteList){
            this.alteList = alteList;
        }
        public List<AlteList> getAlteList(){
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

        @Override
        public String toString() {
            return "VipList{" +
                    "alteList=" + alteList +
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
    public class AlteList implements Serializable
    {
        private String ITM;

        private String action;

        private String alte_Cont;

        private String alte_DD;

        private String db_Id;

        private String user_no;

        private String vip_No;

        public void setITM(String ITM){
            this.ITM = ITM;
        }
        public String getITM(){
            return this.ITM;
        }
        public void setAction(String action){
            this.action = action;
        }
        public String getAction(){
            return this.action;
        }
        public void setAlte_Cont(String alte_Cont){
            this.alte_Cont = alte_Cont;
        }
        public String getAlte_Cont(){
            return this.alte_Cont;
        }
        public void setAlte_DD(String alte_DD){
            this.alte_DD = alte_DD;
        }
        public String getAlte_DD(){
            return this.alte_DD;
        }
        public void setDb_Id(String db_Id){
            this.db_Id = db_Id;
        }
        public String getDb_Id(){
            return this.db_Id;
        }
        public void setUser_no(String user_no){
            this.user_no = user_no;
        }
        public String getUser_no(){
            return this.user_no;
        }
        public void setVip_No(String vip_No){
            this.vip_No = vip_No;
        }
        public String getVip_No(){
            return this.vip_No;
        }

        @Override
        public String toString() {
            return "AlteList{" +
                    "ITM='" + ITM + '\'' +
                    ", action='" + action + '\'' +
                    ", alte_Cont='" + alte_Cont + '\'' +
                    ", alte_DD='" + alte_DD + '\'' +
                    ", db_Id='" + db_Id + '\'' +
                    ", user_no='" + user_no + '\'' +
                    ", vip_No='" + vip_No + '\'' +
                    '}';
        }
    }
}
