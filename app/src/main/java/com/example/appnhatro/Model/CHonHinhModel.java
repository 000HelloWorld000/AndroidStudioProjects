package com.example.appnhatro.Model;

public class CHonHinhModel {

    String duongDan;
    boolean ischeck;

    public CHonHinhModel(String duongDan, boolean ischeck) {
        this.duongDan = duongDan;
        this.ischeck = ischeck;
    }

    public String getDuongDan() {
        return duongDan;
    }

    public void setDuongDan(String duongDan) {
        this.duongDan = duongDan;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
