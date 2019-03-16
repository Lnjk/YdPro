package com.example.bean;

/**
 * Created by Administrator on 2019/3/15 0015.
 */

public class BrandSame {
    private String name;
    private String jxsr;
    private String ml;
    private String mll;

    public BrandSame(String name, String jxsr, String ml, String mll) {
        this.name = name;
        this.jxsr = jxsr;
        this.ml = ml;
        this.mll = mll;
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

    @Override
    public String toString() {
        return "BrandSame{" +
                "name='" + name + '\'' +
                ", jxsr='" + jxsr + '\'' +
                ", ml='" + ml + '\'' +
                ", mll='" + mll + '\'' +
                '}';
    }
}
