package com.example.bean;

/**
 * Created by Administrator on 2019/4/20 0020.
 */

public class SjsJf {
    String jfdh;
    String jflx;
    String sjsname;
    String bz;

    public SjsJf(String jfdh, String jflx, String sjsname, String bz) {
        this.jfdh = jfdh;
        this.jflx = jflx;
        this.sjsname = sjsname;
        this.bz = bz;
    }
    public SjsJf(String jfdh,String sjsname, String bz) {
        this.jfdh = jfdh;
        this.sjsname = sjsname;
        this.bz = bz;
    }

    public String getJfdh() {
        return jfdh;
    }

    public void setJfdh(String jfdh) {
        this.jfdh = jfdh;
    }

    public String getJflx() {
        return jflx;
    }

    public void setJflx(String jflx) {
        this.jflx = jflx;
    }

    public String getSjsname() {
        return sjsname;
    }

    public void setSjsname(String sjsname) {
        this.sjsname = sjsname;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
