package com.cc.skillapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cc.skillapp.R;

public class ReportActivity extends AppCompatActivity {

    private WebView wvReport;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initData();
        initView();
    }




    private void initData() {
//        url = "http://test.d.3fang.com/ndb/report?token=b324801e509aa9b386a7c707b5c9c02f6710789cf1f9c5b879a606e01f531da2450f8b4f17de810eb1f5f9ad63f83ec84ebd4a4e83c02f01609fd4f317db1a3aaba527661cf5f17b48e1705254b0fb4f050fb7948c1632404262998152feed2998d8463fae96b9e5030fc55326e51f7d88c8469d2c9649c97acb09e3de5059d33b6af66a186ec6ee2a0813deb0e13c3e39960ea6e41e600da545a36316646ef0";
        url = "http://test.d.3fang.com/ndb/sharereport?token=b324801e509aa9b386a7c707b5c9c02f6710789cf1f9c5b879a606e01f531da2450f8b4f17de810eb1f5f9ad63f83ec84ebd4a4e83c02f01609fd4f317db1a3aaba527661cf5f17b48e1705254b0fb4f050fb7948c1632404262998152feed2998d8463fae96b9e5030fc55326e51f7d88c8469d2c9649c97acb09e3de5059d33b6af66a186ec6eea3c5939bd6887a75f19a44eb2612b557f33b871b1e1d0c99b6ea318768bed78b90b675b64ab8693a1a5c1b20b54308d087c224dffc107dd2e075b217ad63a24c8d71e8105c14037f";
    }

    private void initView() {
        wvReport = findViewById(R.id.wv_report);

        WebSettings webSettings = wvReport.getSettings();
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


        wvReport.loadUrl(url);

        wvReport.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wvReport.loadUrl(url);
                return true;
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }
}
