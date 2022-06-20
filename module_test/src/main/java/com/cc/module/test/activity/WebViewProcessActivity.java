package com.cc.module.test.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.library.base.util.StringUtils;
import com.cc.library.base.util.Utils;
import com.cc.library.base.util.WebViewUtils;
import com.cc.module.test.R;
import com.cc.module.test.databinding.TestWebViewBinding;

public class WebViewProcessActivity extends AppCompatActivity {

    TestWebViewBinding mBinding;
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("url");
        if(!StringUtils.isNullOrEmpty(url)){
            Utils.log(getClass(),"url="+url);
        }else{
            finish();
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.test_activity_webview_process);
        webView = WebViewUtils.getInstance().getWebView();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 允许加载javascript
        webSettings.setSupportZoom(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBlockNetworkImage(false); // 解决图片不显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            webSettings.setMixedContentMode(webSettings.getMixedContentMode());
        }
        webView.loadUrl(url);
        mBinding.llRoot.addView(webView);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.llRoot.removeAllViews();
        webView.destroy();
        webView = null;

//        System.exit(0);
    }
}
