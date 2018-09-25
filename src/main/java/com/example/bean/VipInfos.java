package com.example.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/25 0025.
 */

public class VipInfos {
    private boolean RLO;
    private List<TypeList> TypeList;
    public void setRLO(boolean RLO) {
        this.RLO = RLO;
    }
    public boolean getRLO() {
        return RLO;
    }

    public void setTypeList(List<TypeList> TypeList) {
        this.TypeList = TypeList;
    }
    public List<TypeList> getTypeList() {
        return TypeList;
    }
    public class TypeList {

        private String biln_Type;
        private String db_Id;
        private String type_ID;
        private String type_Name;
        public void setBiln_Type(String biln_Type) {
            this.biln_Type = biln_Type;
        }
        public String getBiln_Type() {
            return biln_Type;
        }

        public void setDb_Id(String db_Id) {
            this.db_Id = db_Id;
        }
        public String getDb_Id() {
            return db_Id;
        }

        public void setType_ID(String type_ID) {
            this.type_ID = type_ID;
        }
        public String getType_ID() {
            return type_ID;
        }

        public void setType_Name(String type_Name) {
            this.type_Name = type_Name;
        }
        public String getType_Name() {
            return type_Name;
        }

    }
}
