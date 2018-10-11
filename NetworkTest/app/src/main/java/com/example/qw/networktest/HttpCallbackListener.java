package com.example.qw.networktest;

/**
 * Created by qw on 2018/9/21.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
