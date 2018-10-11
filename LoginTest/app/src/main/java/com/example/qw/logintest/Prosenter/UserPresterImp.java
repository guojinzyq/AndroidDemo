package com.example.qw.logintest.Prosenter;

import android.os.Looper;

import com.example.qw.logintest.Model.UserModel;
import com.example.qw.logintest.Model.UserModelImp;
import com.example.qw.logintest.View.UserView;

/**
 * Created by qw on 2018/9/18.
 */

public class UserPresterImp implements UserPresenter {
    UserView userView;
    UserModel userModel;
    android.os.Handler handler;
    public void initUser(){
        userModel=new UserModelImp("wjc","wjc");
    }
    public UserPresterImp(UserView userView)
    {
        this.userView=userView;
        initUser();
        handler=new android.os.Handler(Looper.getMainLooper());
    }


    @Override
    public void doLogin(String name, String passwd) {
        boolean isSuccessLogin=true;
        final int code=userModel.checkUserValidity(name,passwd);
        if(code!=0)
            isSuccessLogin=false;
        final boolean result=isSuccessLogin;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                userView.onLoginResult(result, code);
            }
        }, 3000);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        userView.onSetProgressBarVisibility(visiblity);
    }

    @Override
    public void clear() {
        userView.onClearText();
    }
}
