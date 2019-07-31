package com.cc.skillapp.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cc.skillapp.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class LoadLocalWebActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url;
    private Map<String,String> header = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initData();
        initView();
    }




    private void initData() {


        url = "http://test.d.3fang.com/ndb/static";

//        url = "http://test.d.3fang.com/ndb/sharereport?token=b324801e509aa9b386a7c707b5c9c02f6710789cf1f9c5b879a606e01f531da2450f8b4f17de810eb1f5f9ad63f83ec84ebd4a4e83c02f01609fd4f317db1a3aaba527661cf5f17b48e1705254b0fb4f050fb7948c1632404262998152feed2998d8463fae96b9e5030fc55326e51f7d88c8469d2c9649c97acb09e3de5059d33b6af66a186ec6eea3c5939bd6887a75f19a44eb2612b557f33b871b1e1d0c99b6ea318768bed78b90b675b64ab8693a1a5c1b20b54308d087c224dffc107dd2e075b217ad63a24c8d71e8105c14037f";
    }

    private void initView() {
        mWebView = findViewById(R.id.wv_report);

        WebSettings webSettings = mWebView.getSettings();
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


        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // 步骤1:判断拦截资源的条件，即判断url里的图片资源的文件名
                String path = request.getUrl().toString();



                if(path.contains("app-static")){
                    int index = path.indexOf("app-static/");
                    String fileName = path.substring(index+11);
                    String localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/soufun/res/app-static/"+fileName;


                    String mimeType = "";
                    if(fileName.contains("js")){
                        mimeType = "application/x-javascript";
                    }else if(fileName.contains("css")){
                        mimeType = "text/css";
                    }else if(fileName.contains("png")){
                        mimeType = "image/png";
                    }else if(fileName.contains("gif")){
                        mimeType = " image/gif ";
                    }


                    // 步骤4:替换资源
                    WebResourceResponse response = null;


                    try {
                        response = new WebResourceResponse(mimeType, "utf-8", new FileInputStream(localPath));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // 参数1：http请求里该图片的Content-Type,此处图片为image/png

                    // 参数2：编码类型

                    // 参数3：存放着替换资源的输入流（上面创建的那个）
                    return response;

                }
                return super.shouldInterceptRequest(view, request);
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if(url.contains("app-static")){
                    int index = url.indexOf("app-static/");
                    String fileName = url.substring(index+11);
                    String localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/soufun/res/app-static/"+fileName;


                    String mimeType = "";
                    if(fileName.contains("js")){
                        mimeType = "application/x-javascript";
                    }else if(fileName.contains("css")){
                        mimeType = "text/css";
                    }else if(fileName.contains("png")){
                        mimeType = "image/png";
                    }else if(fileName.contains("gif")){
                        mimeType = " image/gif ";
                    }


                    // 步骤4:替换资源
                    WebResourceResponse response = null;


                    try {
                        response = new WebResourceResponse(mimeType, "utf-8", new FileInputStream(localPath));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // 参数1：http请求里该图片的Content-Type,此处图片为image/png

                    // 参数2：编码类型

                    // 参数3：存放着替换资源的输入流（上面创建的那个）
                    return response;

                }
                return super.shouldInterceptRequest(view, url);

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WebStorage.getInstance().deleteAllData();
        mWebView.clearCache(true);
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        finish();
        Process.killProcess(Process.myPid());
    }
}
