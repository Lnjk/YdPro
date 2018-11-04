package com.example.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/4 0004.
 */

public class CustPsxxInfos {
    private List<CustConInfList> CustConInfList;
    public void setCustConInfList(List<CustConInfList> CustConInfList) {
        this.CustConInfList = CustConInfList;
    }
    public List<CustConInfList> getCustConInfList() {
        return CustConInfList;
    }
    public class CustConInfList {

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
            return "CustConInfList{" +
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
