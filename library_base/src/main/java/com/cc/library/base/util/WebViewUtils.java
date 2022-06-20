package com.cc.library.base.util;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.security.PublicKey;

public class WebViewUtils {


    private WebViewUtils(){};

    private static WebView webView;

    private static WebViewUtils instance;

    public static WebViewUtils getInstance(){
        if(instance == null){
            synchronized (WebViewUtils.class){
                if(instance==null){
                    instance = new WebViewUtils();
                }
            }
        }
        return instance;
    }

    public void init(){
        webView = new WebView(AppContext.getInstance().getContext());

    }

    public WebView getWebView(){
        return webView;
    }

    public void loadUrl(String url){
        webView.loadUrl(url);
    }

}
