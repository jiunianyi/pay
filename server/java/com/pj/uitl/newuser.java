package com.pj.uitl;

import lombok.Data;

@Data
public class newuser {
    private String username;
    private String phone;
    private String pwd;
    public String getpwd()
    {
        return pwd;
    }
    public void setpwd(String pwd)
    {
        this.pwd = pwd;
    }
    public String getphone()
    {
        return phone;
    }
    public void setphone(String phone)
    {
        this.phone = phone;
    }
    public String getusername() {
        return username;
    }
    public void setusername(String username) {
        this.username = username;
    }

}
