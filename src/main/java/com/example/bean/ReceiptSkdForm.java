package com.example.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/3/19 0019.
 */

public class ReceiptSkdForm {
    private int SumShowRow;
    private List<Mf_monList> mf_monList;
    private long SumRow;
    private int ShowRow;

    public int getSumShowRow() {
        return SumShowRow;
    }

    public void setSumShowRow(int sumShowRow) {
        SumShowRow = sumShowRow;
    }

    public List<Mf_monList> getMf_monList() {
        return mf_monList;
    }

    public void setMf_monList(List<Mf_monList> mf_monList) {
        this.mf_monList = mf_monList;
    }

    public long getSumRow() {
        return SumRow;
    }

    public void setSumRow(long sumRow) {
        SumRow = sumRow;
    }

    public int getShowRow() {
        return ShowRow;
    }

    public void setShowRow(int showRow) {
        ShowRow = showRow;
    }
    public class MF_MON {

    }
    public class Tf_CHK {

        private String chk_ID;
        private String chk_MAN;
        private String chk_NO;
        private String chk_REM;
        private String chk_STS;
        private Date cls_DATE;
        private Date eff_DD;
        private String itm;
//        private String mf_CHK;
private List<Mf_CHK> mf_CHK;
        private String pre_ITM;
        private Date trs_DD;
        private String trs_MAN;
        public void setChk_ID(String chk_ID) {
            this.chk_ID = chk_ID;
        }
        public String getChk_ID() {
            return chk_ID;
        }

        public void setChk_MAN(String chk_MAN) {
            this.chk_MAN = chk_MAN;
        }
        public String getChk_MAN() {
            return chk_MAN;
        }

        public void setChk_NO(String chk_NO) {
            this.chk_NO = chk_NO;
        }
        public String getChk_NO() {
            return chk_NO;
        }

        public void setChk_REM(String chk_REM) {
            this.chk_REM = chk_REM;
        }
        public String getChk_REM() {
            return chk_REM;
        }

        public void setChk_STS(String chk_STS) {
            this.chk_STS = chk_STS;
        }
        public String getChk_STS() {
            return chk_STS;
        }

        public void setCls_DATE(Date cls_DATE) {
            this.cls_DATE = cls_DATE;
        }
        public Date getCls_DATE() {
            return cls_DATE;
        }

        public void setEff_DD(Date eff_DD) {
            this.eff_DD = eff_DD;
        }
        public Date getEff_DD() {
            return eff_DD;
        }

        public void setItm(String itm) {
            this.itm = itm;
        }
        public String getItm() {
            return itm;
        }

//        public void setMf_CHK(String mf_CHK) {
//            this.mf_CHK = mf_CHK;
//        }
//        public String getMf_CHK() {
//            return mf_CHK;
//        }

        public List<Mf_CHK> getMf_CHK() {
            return mf_CHK;
        }

        public void setMf_CHK(List<Mf_CHK> mf_CHK) {
            this.mf_CHK = mf_CHK;
        }

        public void setPre_ITM(String pre_ITM) {
            this.pre_ITM = pre_ITM;
        }
        public String getPre_ITM() {
            return pre_ITM;
        }

        public void setTrs_DD(Date trs_DD) {
            this.trs_DD = trs_DD;
        }
        public Date getTrs_DD() {
            return trs_DD;
        }

        public void setTrs_MAN(String trs_MAN) {
            this.trs_MAN = trs_MAN;
        }
        public String getTrs_MAN() {
            return trs_MAN;
        }

    }
    public class Mf_BAC {

    }
    public class Tf_MON1 {

    }
    public class Tf_MON3 {

    }
    public class Tc_MON {

    }
    public class Mf_CHK {

        private String amt;
        private String amtn;
        private String bank_NO;
        private String bil_ID;
        private Date cah_DD;
        private String cash_ID;
        private String chk_ID;
        private String chk_KND;
        private String chk_MAN;
        private String chk_NO;
        private String chk_STS;
        private Date cls_DATE;
        private String cus_NO;
        private String dep;
        private Date eff_DD;
        private Date end_DD;
        private String exc_RTO;
        private String int_STS;
//        private String mf_MON;
        private MF_MON mf_MON;
        private String opn_ID;
        private String pc_FLAG;
        private Date rcv_DD;
        private Date record_DD;
        private String rpc_NO;
        private String sal_NO;
        private Tf_CHK tf_CHK;
        private Date trs_DD;
        private String usr;
        public void setAmt(String amt) {
            this.amt = amt;
        }
        public String getAmt() {
            return amt;
        }

        public void setAmtn(String amtn) {
            this.amtn = amtn;
        }
        public String getAmtn() {
            return amtn;
        }

        public void setBank_NO(String bank_NO) {
            this.bank_NO = bank_NO;
        }
        public String getBank_NO() {
            return bank_NO;
        }

        public void setBil_ID(String bil_ID) {
            this.bil_ID = bil_ID;
        }
        public String getBil_ID() {
            return bil_ID;
        }

        public void setCah_DD(Date cah_DD) {
            this.cah_DD = cah_DD;
        }
        public Date getCah_DD() {
            return cah_DD;
        }

        public void setCash_ID(String cash_ID) {
            this.cash_ID = cash_ID;
        }
        public String getCash_ID() {
            return cash_ID;
        }

        public void setChk_ID(String chk_ID) {
            this.chk_ID = chk_ID;
        }
        public String getChk_ID() {
            return chk_ID;
        }

        public void setChk_KND(String chk_KND) {
            this.chk_KND = chk_KND;
        }
        public String getChk_KND() {
            return chk_KND;
        }

        public void setChk_MAN(String chk_MAN) {
            this.chk_MAN = chk_MAN;
        }
        public String getChk_MAN() {
            return chk_MAN;
        }

        public void setChk_NO(String chk_NO) {
            this.chk_NO = chk_NO;
        }
        public String getChk_NO() {
            return chk_NO;
        }

        public void setChk_STS(String chk_STS) {
            this.chk_STS = chk_STS;
        }
        public String getChk_STS() {
            return chk_STS;
        }

        public void setCls_DATE(Date cls_DATE) {
            this.cls_DATE = cls_DATE;
        }
        public Date getCls_DATE() {
            return cls_DATE;
        }

        public void setCus_NO(String cus_NO) {
            this.cus_NO = cus_NO;
        }
        public String getCus_NO() {
            return cus_NO;
        }

        public void setDep(String dep) {
            this.dep = dep;
        }
        public String getDep() {
            return dep;
        }

        public void setEff_DD(Date eff_DD) {
            this.eff_DD = eff_DD;
        }
        public Date getEff_DD() {
            return eff_DD;
        }

        public void setEnd_DD(Date end_DD) {
            this.end_DD = end_DD;
        }
        public Date getEnd_DD() {
            return end_DD;
        }

        public void setExc_RTO(String exc_RTO) {
            this.exc_RTO = exc_RTO;
        }
        public String getExc_RTO() {
            return exc_RTO;
        }

        public void setInt_STS(String int_STS) {
            this.int_STS = int_STS;
        }
        public String getInt_STS() {
            return int_STS;
        }

//        public void setMf_MON(String mf_MON) {
//            this.mf_MON = mf_MON;
//        }
//        public String getMf_MON() {
//            return mf_MON;
//        }

        public MF_MON getMf_MON() {
            return mf_MON;
        }

        public void setMf_MON(MF_MON mf_MON) {
            this.mf_MON = mf_MON;
        }

        public void setOpn_ID(String opn_ID) {
            this.opn_ID = opn_ID;
        }
        public String getOpn_ID() {
            return opn_ID;
        }

        public void setPc_FLAG(String pc_FLAG) {
            this.pc_FLAG = pc_FLAG;
        }
        public String getPc_FLAG() {
            return pc_FLAG;
        }

        public void setRcv_DD(Date rcv_DD) {
            this.rcv_DD = rcv_DD;
        }
        public Date getRcv_DD() {
            return rcv_DD;
        }

        public void setRecord_DD(Date record_DD) {
            this.record_DD = record_DD;
        }
        public Date getRecord_DD() {
            return record_DD;
        }

        public void setRpc_NO(String rpc_NO) {
            this.rpc_NO = rpc_NO;
        }
        public String getRpc_NO() {
            return rpc_NO;
        }

        public void setSal_NO(String sal_NO) {
            this.sal_NO = sal_NO;
        }
        public String getSal_NO() {
            return sal_NO;
        }

        public void setTf_CHK(Tf_CHK tf_CHK) {
            this.tf_CHK = tf_CHK;
        }
        public Tf_CHK getTf_CHK() {
            return tf_CHK;
        }

        public void setTrs_DD(Date trs_DD) {
            this.trs_DD = trs_DD;
        }
        public Date getTrs_DD() {
            return trs_DD;
        }

        public void setUsr(String usr) {
            this.usr = usr;
        }
        public String getUsr() {
            return usr;
        }

    }
    public class Tf_MON {

        private String amtn_BB;
        private String amtn_BC;
        private String amtn_CHK1;
        private String amtn_CHK2;
        private String amtn_CHK3;
        private String amtn_CHK4;
        private String amtn_CHK5;
        private String amtn_CLS;
        private String amtn_IRP;
        private String bacc_NO;
        private String bb_NO;
        private String bc_NO;
        private String bil_TYPE;
        private String bz_ID;
        private String cacc_NO;
        private String check_MAN;
        private String chk1_NO;
        private String chk2_NO;
        private String chk3_NO;
        private String chk4_NO;
        private String chk5_NO;
        private String chk_MAN;
        private Date cls_DATE;
        private String cls_ID;
        private String cus_NO;
        private String dep;
        private Date eff_DD;
        private String exc_RTO;
        private String includeson;
        private String irp_ID;
        private String itm;
//        private String mf_BAC;
        private List<Mf_BAC> mf_BAC;
        private List<Mf_CHK> mf_CHK;
        private String mf_MON;
        private String prt_SW;
        private Date record_DD;
        private String rem;
        private Date rp_DD;
        private String rp_ID;
        private String rp_NO;
        private String sor_ID;
        private Tc_MON tc_MON;
        private Tf_MON1 tf_MON1;
        private Tf_MON3 tf_MON3;
        private String usr;
        private String usr_NO;
        //自定义
        private String yis_bccx;
        private String yis_khmc;
        private String yis_wcjy;
        private String yis_ysph;
        private String yis_yshl;
        private String yis_kzbz;

        public String getYis_bccx() {
            return yis_bccx;
        }

        public void setYis_bccx(String yis_bccx) {
            this.yis_bccx = yis_bccx;
        }

        public String getYis_khmc() {
            return yis_khmc;
        }

        public void setYis_khmc(String yis_khmc) {
            this.yis_khmc = yis_khmc;
        }

        public String getYis_wcjy() {
            return yis_wcjy;
        }

        public void setYis_wcjy(String yis_wcjy) {
            this.yis_wcjy = yis_wcjy;
        }

        public String getYis_ysph() {
            return yis_ysph;
        }

        public void setYis_ysph(String yis_ysph) {
            this.yis_ysph = yis_ysph;
        }

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

        public void setAmtn_BB(String amtn_BB) {
            this.amtn_BB = amtn_BB;
        }
        public String getAmtn_BB() {
            return amtn_BB;
        }

        public void setAmtn_BC(String amtn_BC) {
            this.amtn_BC = amtn_BC;
        }
        public String getAmtn_BC() {
            return amtn_BC;
        }

        public void setAmtn_CHK1(String amtn_CHK1) {
            this.amtn_CHK1 = amtn_CHK1;
        }
        public String getAmtn_CHK1() {
            return amtn_CHK1;
        }

        public void setAmtn_CHK2(String amtn_CHK2) {
            this.amtn_CHK2 = amtn_CHK2;
        }
        public String getAmtn_CHK2() {
            return amtn_CHK2;
        }

        public void setAmtn_CHK3(String amtn_CHK3) {
            this.amtn_CHK3 = amtn_CHK3;
        }
        public String getAmtn_CHK3() {
            return amtn_CHK3;
        }

        public void setAmtn_CHK4(String amtn_CHK4) {
            this.amtn_CHK4 = amtn_CHK4;
        }
        public String getAmtn_CHK4() {
            return amtn_CHK4;
        }

        public void setAmtn_CHK5(String amtn_CHK5) {
            this.amtn_CHK5 = amtn_CHK5;
        }
        public String getAmtn_CHK5() {
            return amtn_CHK5;
        }

        public void setAmtn_CLS(String amtn_CLS) {
            this.amtn_CLS = amtn_CLS;
        }
        public String getAmtn_CLS() {
            return amtn_CLS;
        }

        public void setAmtn_IRP(String amtn_IRP) {
            this.amtn_IRP = amtn_IRP;
        }
        public String getAmtn_IRP() {
            return amtn_IRP;
        }

        public void setBacc_NO(String bacc_NO) {
            this.bacc_NO = bacc_NO;
        }
        public String getBacc_NO() {
            return bacc_NO;
        }

        public void setBb_NO(String bb_NO) {
            this.bb_NO = bb_NO;
        }
        public String getBb_NO() {
            return bb_NO;
        }

        public void setBc_NO(String bc_NO) {
            this.bc_NO = bc_NO;
        }
        public String getBc_NO() {
            return bc_NO;
        }

        public void setBil_TYPE(String bil_TYPE) {
            this.bil_TYPE = bil_TYPE;
        }
        public String getBil_TYPE() {
            return bil_TYPE;
        }

        public void setBz_ID(String bz_ID) {
            this.bz_ID = bz_ID;
        }
        public String getBz_ID() {
            return bz_ID;
        }

        public void setCacc_NO(String cacc_NO) {
            this.cacc_NO = cacc_NO;
        }
        public String getCacc_NO() {
            return cacc_NO;
        }

        public void setCheck_MAN(String check_MAN) {
            this.check_MAN = check_MAN;
        }
        public String getCheck_MAN() {
            return check_MAN;
        }

        public void setChk1_NO(String chk1_NO) {
            this.chk1_NO = chk1_NO;
        }
        public String getChk1_NO() {
            return chk1_NO;
        }

        public void setChk2_NO(String chk2_NO) {
            this.chk2_NO = chk2_NO;
        }
        public String getChk2_NO() {
            return chk2_NO;
        }

        public void setChk3_NO(String chk3_NO) {
            this.chk3_NO = chk3_NO;
        }
        public String getChk3_NO() {
            return chk3_NO;
        }

        public void setChk4_NO(String chk4_NO) {
            this.chk4_NO = chk4_NO;
        }
        public String getChk4_NO() {
            return chk4_NO;
        }

        public void setChk5_NO(String chk5_NO) {
            this.chk5_NO = chk5_NO;
        }
        public String getChk5_NO() {
            return chk5_NO;
        }

        public void setChk_MAN(String chk_MAN) {
            this.chk_MAN = chk_MAN;
        }
        public String getChk_MAN() {
            return chk_MAN;
        }

        public void setCls_DATE(Date cls_DATE) {
            this.cls_DATE = cls_DATE;
        }
        public Date getCls_DATE() {
            return cls_DATE;
        }

        public void setCls_ID(String cls_ID) {
            this.cls_ID = cls_ID;
        }
        public String getCls_ID() {
            return cls_ID;
        }

        public void setCus_NO(String cus_NO) {
            this.cus_NO = cus_NO;
        }
        public String getCus_NO() {
            return cus_NO;
        }

        public void setDep(String dep) {
            this.dep = dep;
        }
        public String getDep() {
            return dep;
        }

        public void setEff_DD(Date eff_DD) {
            this.eff_DD = eff_DD;
        }
        public Date getEff_DD() {
            return eff_DD;
        }

        public void setExc_RTO(String exc_RTO) {
            this.exc_RTO = exc_RTO;
        }
        public String getExc_RTO() {
            return exc_RTO;
        }

        public void setIncludeson(String includeson) {
            this.includeson = includeson;
        }
        public String getIncludeson() {
            return includeson;
        }

        public void setIrp_ID(String irp_ID) {
            this.irp_ID = irp_ID;
        }
        public String getIrp_ID() {
            return irp_ID;
        }

        public void setItm(String itm) {
            this.itm = itm;
        }
        public String getItm() {
            return itm;
        }

//        public void setMf_BAC(String mf_BAC) {
//            this.mf_BAC = mf_BAC;
//        }
//        public String getMf_BAC() {
//            return mf_BAC;
//        }

        public List<Mf_BAC> getMf_BAC() {
            return mf_BAC;
        }

        public void setMf_BAC(List<Mf_BAC> mf_BAC) {
            this.mf_BAC = mf_BAC;
        }

        public void setMf_CHK(List<Mf_CHK> mf_CHK) {
            this.mf_CHK = mf_CHK;
        }
        public List<Mf_CHK> getMf_CHK() {
            return mf_CHK;
        }

        public void setMf_MON(String mf_MON) {
            this.mf_MON = mf_MON;
        }
        public String getMf_MON() {
            return mf_MON;
        }

        public void setPrt_SW(String prt_SW) {
            this.prt_SW = prt_SW;
        }
        public String getPrt_SW() {
            return prt_SW;
        }

        public void setRecord_DD(Date record_DD) {
            this.record_DD = record_DD;
        }
        public Date getRecord_DD() {
            return record_DD;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }
        public String getRem() {
            return rem;
        }

        public void setRp_DD(Date rp_DD) {
            this.rp_DD = rp_DD;
        }
        public Date getRp_DD() {
            return rp_DD;
        }

        public void setRp_ID(String rp_ID) {
            this.rp_ID = rp_ID;
        }
        public String getRp_ID() {
            return rp_ID;
        }

        public void setRp_NO(String rp_NO) {
            this.rp_NO = rp_NO;
        }
        public String getRp_NO() {
            return rp_NO;
        }

        public void setSor_ID(String sor_ID) {
            this.sor_ID = sor_ID;
        }
        public String getSor_ID() {
            return sor_ID;
        }

        public Tc_MON getTc_MON() {
            return tc_MON;
        }

        public void setTc_MON(Tc_MON tc_MON) {
            this.tc_MON = tc_MON;
        }

        public Tf_MON1 getTf_MON1() {
            return tf_MON1;
        }

        public void setTf_MON1(Tf_MON1 tf_MON1) {
            this.tf_MON1 = tf_MON1;
        }

        public Tf_MON3 getTf_MON3() {
            return tf_MON3;
        }

        public void setTf_MON3(Tf_MON3 tf_MON3) {
            this.tf_MON3 = tf_MON3;
        }

        public void setUsr(String usr) {
            this.usr = usr;
        }
        public String getUsr() {
            return usr;
        }

        public void setUsr_NO(String usr_NO) {
            this.usr_NO = usr_NO;
        }
        public String getUsr_NO() {
            return usr_NO;
        }

    }
    public class Tf_MON_Z {

        private String cus_NO_OS;
        private String itm;
        private String mf_MON;
        private String rp_ID;
        private String rp_NO;
        public void setCus_NO_OS(String cus_NO_OS) {
            this.cus_NO_OS = cus_NO_OS;
        }
        public String getCus_NO_OS() {
            return cus_NO_OS;
        }

        public void setItm(String itm) {
            this.itm = itm;
        }
        public String getItm() {
            return itm;
        }

        public void setMf_MON(String mf_MON) {
            this.mf_MON = mf_MON;
        }
        public String getMf_MON() {
            return mf_MON;
        }

        public void setRp_ID(String rp_ID) {
            this.rp_ID = rp_ID;
        }
        public String getRp_ID() {
            return rp_ID;
        }

        public void setRp_NO(String rp_NO) {
            this.rp_NO = rp_NO;
        }
        public String getRp_NO() {
            return rp_NO;
        }

    }
    public  class Mf_monList {

        private String amtn;
        private String amtn_ARP;
        private String bil_ID;
        private String bil_NO;
        private String dep;
        private String rem;
        private Date rp_DD;
        private String rp_ID;
        private String rp_NO;
        private Tf_MON tf_MON;
        private Tf_MON_Z tf_MON_Z;
        private String usr_NO;


        public void setAmtn(String amtn) {
            this.amtn = amtn;
        }
        public String getAmtn() {
            return amtn;
        }

        public void setAmtn_ARP(String amtn_ARP) {
            this.amtn_ARP = amtn_ARP;
        }
        public String getAmtn_ARP() {
            return amtn_ARP;
        }

        public void setBil_ID(String bil_ID) {
            this.bil_ID = bil_ID;
        }
        public String getBil_ID() {
            return bil_ID;
        }

        public void setBil_NO(String bil_NO) {
            this.bil_NO = bil_NO;
        }
        public String getBil_NO() {
            return bil_NO;
        }

        public void setDep(String dep) {
            this.dep = dep;
        }
        public String getDep() {
            return dep;
        }

        public void setRem(String rem) {
            this.rem = rem;
        }
        public String getRem() {
            return rem;
        }

        public void setRp_DD(Date rp_DD) {
            this.rp_DD = rp_DD;
        }
        public Date getRp_DD() {
            return rp_DD;
        }

        public void setRp_ID(String rp_ID) {
            this.rp_ID = rp_ID;
        }
        public String getRp_ID() {
            return rp_ID;
        }

        public void setRp_NO(String rp_NO) {
            this.rp_NO = rp_NO;
        }
        public String getRp_NO() {
            return rp_NO;
        }

        public void setTf_MON(Tf_MON tf_MON) {
            this.tf_MON = tf_MON;
        }
        public Tf_MON getTf_MON() {
            return tf_MON;
        }

        public void setTf_MON_Z(Tf_MON_Z tf_MON_Z) {
            this.tf_MON_Z = tf_MON_Z;
        }
        public Tf_MON_Z getTf_MON_Z() {
            return tf_MON_Z;
        }

        public void setUsr_NO(String usr_NO) {
            this.usr_NO = usr_NO;
        }
        public String getUsr_NO() {
            return usr_NO;
        }

    }
}