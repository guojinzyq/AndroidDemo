package com.example.qw.logintest.View;

/**
 * Created by qw on 2018/9/18.
 */

public interface UserView {
    public void onClearText();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);
}
