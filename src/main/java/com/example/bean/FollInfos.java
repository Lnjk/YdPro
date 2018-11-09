package com.example.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/11/8 0008.
 */

public class FollInfos {
    private List<FollList> FollList;
    public void setFollList(List<FollList> FollList) {
        this.FollList = FollList;
    }
    public List<FollList> getFollList() {
        return FollList;
    }
    public class FollList {

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

    }
}
