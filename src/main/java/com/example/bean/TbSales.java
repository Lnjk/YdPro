package com.example.bean;

/**
 * Created by Administrator on 2019/3/8 0008.
 */

public class TbSales {
    private String name;
    private String jxsr;
    private String ml;
    private String mll;
    private String name_db;
    private String jxsr_db;
    private String ml_db;
    private String mll_db;

    public TbSales() {
    }

    public TbSales(String name, String jxsr, String ml, String mll, String name_db, String jxsr_db, String ml_db, String mll_db) {
        this.name = name;
        this.jxsr = jxsr;
        this.ml = ml;
        this.mll = mll;
        this.name_db = name_db;
        this.jxsr_db = jxsr_db;
        this.ml_db = ml_db;
        this.mll_db = mll_db;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJxsr() {
        return jxsr;
    }

    public void setJxsr(String jxsr) {
        this.jxsr = jxsr;
    }

    public String getMl() {
        return ml;
    }

    public void setMl(String ml) {
        this.ml = ml;
    }

    public String getMll() {
        return mll;
    }

    public void setMll(String mll) {
        this.mll = mll;
    }

    public String getName_db() {
        return name_db;
    }

    public void setName_db(String name_db) {
        this.name_db = name_db;
    }

    public String getJxsr_db() {
        return jxsr_db;
    }

    public void setJxsr_db(String jxsr_db) {
        this.jxsr_db = jxsr_db;
    }

    public String getMl_db() {
        return ml_db;
    }

    public void setMl_db(String ml_db) {
        this.ml_db = ml_db;
    }

    public String getMll_db() {
        return mll_db;
    }

    public void setMll_db(String mll_db) {
        this.mll_db = mll_db;
    }

    @Override
    public String toString() {
        return "TbSales{" +
                "name='" + name + '\'' +
                ", jxsr='" + jxsr + '\'' +
                ", ml='" + ml + '\'' +
                ", mll='" + mll + '\'' +
                ", name_db='" + name_db + '\'' +
                ", jxsr_db='" + jxsr_db + '\'' +
                ", ml_db='" + ml_db + '\'' +
                ", mll_db='" + mll_db + '\'' +
                '}';
    }
}
