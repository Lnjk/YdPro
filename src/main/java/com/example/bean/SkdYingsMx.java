package com.example.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/3/28 0028.
 */

public class SkdYingsMx {
    private ArpResult arpResult;
    public void setArpResult(ArpResult arpResult) {
        this.arpResult = arpResult;
    }
    public ArpResult getArpResult() {
        return arpResult;
    }
    public class ArpResult {


    private List<MfArpList> MfArpList;
    public void setMfArpList(List<MfArpList> MfArpList) {
        this.MfArpList = MfArpList;
    }
    public List<MfArpList> getMfArpList() {
        return MfArpList;
    }
    }
    public static class MfArpList {

        private String amtn;
        private String amtn_NET;
        private String amtn_RCV;
        private String arp_ID;
        private String arp_NO;
        private Date bil_DD;
        private String bil_ID;
        private String bil_ITM;
        private String bil_NO;
        private String check_MAN;
        private String close_ID;
        private Date cls_DD;
        private String cus_NO;
        private String dep;
        private String exc_RTO;
        private String iscls_BAL;
        private Date lz_DD;
        private String opn_ID;
        private Date pay_DD;
        private String rem;
        private String rpt_NO;
        private String sal_NO;
        private String tax;
        private String usr_NO;
        //自定义
        private String yis_bccx;
        private String yis_khmc;
        private String yis_wcjy;
        private String yis_ysph;
        private String yis_yshl;
        private String yis_kzbz;

        public String getYis_yshl() {
            return yis_yshl;
        }

        public void setYis_yshl(String yis_yshl) {
            this.yis_yshl = yis_yshl;
        }

        public String getYis_kzbz() {
            return yis_kzbz;
        }

        public void setYis_kzbz(String yis_kzbz) {
            this.yis_kzbz = yis_kzbz;
        }

        public String getYis_ysph() {
            return yis_ysph;
        }

        public void setYis_ysph(String yis_ysph) {
            this.yis_ysph = yis_ysph;
        }

        public String getYis_wcjy() {
            return yis_wcjy;
        }

        public void setYis_wcjy(String yis_wcjy) {
            this.yis_wcjy = yis_wcjy;
        }

        public String getYis_khmc() {
            return yis_khmc;
        }

        public void setYis_khmc(String yis_khmc) {
            this.yis_khmc = yis_khmc;
        }

        public String getAmtn() {
            return amtn;
        }

        public void setAmtn(String amtn) {
            this.amtn = amtn;
        }

        public String getAmtn_NET() {
            return amtn_NET;
        }

        public void setAmtn_NET(String amtn_NET) {
            this.amtn_NET = amtn_NET;
        }

        public String getAmtn_RCV() {
            return amtn_RCV;
        }

        public void setAmtn_RCV(String amtn_RCV) {
            this.amtn_RCV = amtn_RCV;
        }

        public String getArp_ID() {
            return arp_ID;
        }

        public void setArp_ID(String arp_ID) {
            this.arp_ID = arp_ID;
        }

        public String getArp_NO() {
            return arp_NO;
        }

        public void setArp_NO(String arp_NO) {
            this.arp_NO = arp_NO;
        }

        public Date getBil_DD() {
            return bil_DD;
        }

        public void setBil_DD(Date bil_DD) {
            this.bil_DD = bil_DD;
        }

        public String getBil_ID() {
            return bil_ID;
        }

        public void setBil_ID(String bil_ID) {
            this.bil_ID = bil_ID;
        }

        public String getBil_ITM() {
            return bil_ITM;
        }

        public void setBil_ITM(String bil_ITM) {
            this.bil_ITM = bil_ITM;
        }

        public String getBil_NO() {
            return bil_NO;
        }

        public void setBil_NO(String bil_NO) {
            this.bil_NO = bil_NO;
        }

        public String getCheck_MAN() {
            return check_MAN;
        }

        public void setCheck_MAN(String check_MAN) {
            this.check_MAN = check_MAN;
        }

        public String getClose_ID() {
            return close_ID;
        }

        public void setClose_ID(String close_ID) {
            this.close_ID = close_ID;
        }

        public Date getCls_DD() {
            return cls_DD;
        }

        public void setCls_DD(Date cls_DD) {
            this.cls_DD = cls_DD;
        }

        public String getCus_NO() {
            return cus_NO;
        }

        public void setCus_NO(String cus_NO) {
            this.cus_NO = cus_NO;
        }

        public String getDep() {
            return dep;
        }

        public void setDep(String dep) {
            this.dep = dep;
        }

        public String getExc_RTO() {
            return exc_RTO;
        }

        public void setExc_RTO(String exc_RTO) {
            this.exc_RTO = exc_RTO;
        }

        public String getIscls_BAL() {
            return iscls_BAL;
        }

        public void setIscls_BAL(String iscls_BAL) {
            this.iscls_BAL = iscls_BAL;
        }

        public Date getLz_DD() {
            return lz_DD;
        }

        public void setLz_DD(Date lz_DD) {
            this.lz_DD = lz_DD;
        }

        public String getOpn_ID() {
            return opn_ID;
        }

        public void setOpn_ID(String opn_ID) {
            this.opn_ID = opn_ID;
        }

        public Date getPay_DD() {
            return pay_DD;
        }

        public void setPay_DD(Date pay_DD) {
            this.pay_DD = pay_DD;
        }

        public String getRem() {
            return rem;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }

        public String getRpt_NO() {
            return rpt_NO;
        }

        public void setRpt_NO(String rpt_NO) {
            this.rpt_NO = rpt_NO;
        }

        public String getSal_NO() {
            return sal_NO;
        }

        public void setSal_NO(String sal_NO) {
            this.sal_NO = sal_NO;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getUsr_NO() {
            return usr_NO;
        }

        public void setUsr_NO(String usr_NO) {
            this.usr_NO = usr_NO;
        }

        public String getYis_bccx() {
            return yis_bccx;
        }

        public void setYis_bccx(String yis_bccx) {
            this.yis_bccx = yis_bccx;
        }
    }
}
