package com.example.qw.logintest.Model;

/**
 * Created by qw on 2018/9/18.
 */

public class UserModelImp implements UserModel{
    private String name2;
    private String password2;
    public UserModelImp(String name,String password){
        this.name2=name;
        this.password2=password;

    }
    public int checkUserValidity(String name,String password){
        if((name.equals(name2)&&password.equals(password2)))
        {
            return 0;
        }
        else
            return 1;
    }
}
