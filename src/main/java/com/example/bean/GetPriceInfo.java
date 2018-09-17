package com.example.bean;

import java.util.List;
import java.util.Date;

/**
 * Created by ${宁.金珂} on 2018/7/9.
 */

public class GetPriceInfo {
    private List<Prices> prices;
    public void setPrices(List<Prices> prices) {
        this.prices = prices;
    }
    public List<Prices> getPrices() {
        return prices;
    }
    public class Prices {

        private String accepted;
        private String biln_user;
        private String compDep;
        private String compDepName;
        private String db_Id;
        private String db_Name;
        private String discount;
        private String obj_Type;
        private String operator;
        private String operator_Val;
        private String prdtUp;
        private String priceId;
        private String priceName;
        private Date price_DD;
        private String rem;
        private String salesTerminal_Name;
        private String salesTerminal_No;
        private String stopDate;
        private String sup_Name;
        private String sup_No;
        private String useDate;
        public void setAccepted(String accepted) {
            this.accepted = accepted;
        }
        public String getAccepted() {
            return accepted;
        }

        public void setBiln_user(String biln_user) {
            this.biln_user = biln_user;
        }
        public String getBiln_user() {
            return biln_user;
        }

        public void setCompDep(String compDep) {
            this.compDep = compDep;
        }
        public String getCompDep() {
            return compDep;
        }

        public void setCompDepName(String compDepName) {
            this.compDepName = compDepName;
        }
        public String getCompDepName() {
            return compDepName;
        }

        public void setDb_Id(String db_Id) {
            this.db_Id = db_Id;
        }
        public String getDb_Id() {
            return db_Id;
        }

        public void setDb_Name(String db_Name) {
            this.db_Name = db_Name;
        }
        public String getDb_Name() {
            return db_Name;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
        public String getDiscount() {
            return discount;
        }

        public void setObj_Type(String obj_Type) {
            this.obj_Type = obj_Type;
        }
        public String getObj_Type() {
            return obj_Type;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }
        public String getOperator() {
            return operator;
        }

        public void setOperator_Val(String operator_Val) {
            this.operator_Val = operator_Val;
        }
        public String getOperator_Val() {
            return operator_Val;
        }

        public void setPrdtUp(String prdtUp) {
            this.prdtUp = prdtUp;
        }
        public String getPrdtUp() {
            return prdtUp;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }
        public String getPriceId() {
            return priceId;
        }

        public void setPriceName(String priceName) {
            this.priceName = priceName;
        }
        public String getPriceName() {
            return priceName;
        }

        public void setPrice_DD(Date price_DD) {
            this.price_DD = price_DD;
        }
        public Date getPrice_DD() {
            return price_DD;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }
        public String getRem() {
            return rem;
        }

        public void setSalesTerminal_Name(String salesTerminal_Name) {
            this.salesTerminal_Name = salesTerminal_Name;
        }
        public String getSalesTerminal_Name() {
            return salesTerminal_Name;
        }

        public void setSalesTerminal_No(String salesTerminal_No) {
            this.salesTerminal_No = salesTerminal_No;
        }
        public String getSalesTerminal_No() {
            return salesTerminal_No;
        }

        public void setStopDate(String stopDate) {
            this.stopDate = stopDate;
        }
        public String getStopDate() {
            return stopDate;
        }

        public void setSup_Name(String sup_Name) {
            this.sup_Name = sup_Name;
        }
        public String getSup_Name() {
            return sup_Name;
        }

        public void setSup_No(String sup_No) {
            this.sup_No = sup_No;
        }
        public String getSup_No() {
            return sup_No;
        }

        public void setUseDate(String useDate) {
            this.useDate = useDate;
        }
        public String getUseDate() {
            return useDate;
        }

    }
}
