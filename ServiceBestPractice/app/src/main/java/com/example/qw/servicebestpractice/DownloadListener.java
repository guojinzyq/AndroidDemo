package com.example.qw.servicebestpractice;

/**
 * Created by qw on 2018/9/22.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
