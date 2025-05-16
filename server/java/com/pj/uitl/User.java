package com.pj.uitl;

import lombok.Data;

@Data
public class User {
    private String zh;
    private String mm;

    // Getters and Setters
    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }
}