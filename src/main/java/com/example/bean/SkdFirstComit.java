package com.example.bean;

/**
 * Created by Administrator on 2019/5/8 0008.
 */

public class SkdFirstComit {
    private boolean Reconstruction;
    private String Rp_No;
    private String Success;

    public boolean isReconstruction() {
        return Reconstruction;
    }

    public void setReconstruction(boolean reconstruction) {
        Reconstruction = reconstruction;
    }

    public String getRp_No() {
        return Rp_No;
    }

    public void setRp_No(String rp_No) {
        Rp_No = rp_No;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    @Override
    public String toString() {
        return "SkdFirstComit{" +
                "Reconstruction=" + Reconstruction +
                ", Rp_No='" + Rp_No + '\'' +
                ", Success='" + Success + '\'' +
                '}';
    }
}
