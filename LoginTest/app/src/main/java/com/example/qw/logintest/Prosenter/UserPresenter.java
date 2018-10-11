package com.example.qw.logintest.Prosenter;

/**
 * Created by qw on 2018/9/18.
 */

public interface UserPresenter {
    void clear();
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
}
