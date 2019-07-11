package com.cc.skillapp.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cc.skillapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadAssetsWebActivity extends AppCompatActivity {

    private WebView wvReport;
    private String url;
    private Map<String,String> header = new HashMap<>();

    private List<String> mPicList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initData();
        initView();
    }




    private void initData() {

        mPicList.add("echarts.min.js");
        mPicList.add("jquery-1.9.1.min.js");
        mPicList.add("loading.gif");

        url = "http://test.d.3fang.com/ndb/static";

//        url = "http://test.d.3fang.com/ndb/sharereport?token=b324801e509aa9b386a7c707b5c9c02f6710789cf1f9c5b879a606e01f531da2450f8b4f17de810eb1f5f9ad63f83ec84ebd4a4e83c02f01609fd4f317db1a3aaba527661cf5f17b48e1705254b0fb4f050fb7948c1632404262998152feed2998d8463fae96b9e5030fc55326e51f7d88c8469d2c9649c97acb09e3de5059d33b6af66a186ec6eea3c5939bd6887a75f19a44eb2612b557f33b871b1e1d0c99b6ea318768bed78b90b675b64ab8693a1a5c1b20b54308d087c224dffc107dd2e075b217ad63a24c8d71e8105c14037f";
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


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // 步骤1:判断拦截资源的条件，即判断url里的图片资源的文件名
                String path = request.getUrl().toString();
                if(path.contains("app-static")){
                    int index = path.indexOf("app-static/");
                    String fileName = path.substring(index+11);


                    // 假设网页里该图片资源的地址为：http://abc.com/imgage/logo.gif
                    // 图片的资源文件名为:logo.gif

                    InputStream is = null;
                    // 步骤2:创建一个输入流
                    try {
                        is =getApplicationContext().getAssets().open("webres/"+fileName);
                        // 步骤3:获得需要替换的资源(存放在assets文件夹里)
                        // a. 先在app/src/main下创建一个assets文件夹
                        // a. 先在app/src/main下创建一个assets文件夹
                        // c. 在images文件夹
                        // 放上需要替换的资源（此处替换的是abc.png图片）

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    WebResourceResponse response = new WebResourceResponse(mimeType, "utf-8", is);
                    // 参数1：http请求里该图片的Content-Type,此处图片为image/png

                    // 参数2：编码类型

                    // 参数3：存放着替换资源的输入流（上面创建的那个）
                    return response;

                }
                return super.shouldInterceptRequest(view, request);
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                // 步骤1:判断拦截资源的条件，即判断url里的图片资源的文件名
                if(url.contains("app-static")){
                    int index = url.indexOf("app-static/");
                    String fileName = url.substring(index+11);


                    // 假设网页里该图片资源的地址为：http://abc.com/imgage/logo.gif
                    // 图片的资源文件名为:logo.gif

                    InputStream is = null;
                    // 步骤2:创建一个输入流
                    try {
                        is =getApplicationContext().getAssets().open("webres/"+fileName);
                        // 步骤3:获得需要替换的资源(存放在assets文件夹里)
                        // a. 先在app/src/main下创建一个assets文件夹
                        // a. 先在app/src/main下创建一个assets文件夹
                        // c. 在images文件夹
                        // 放上需要替换的资源（此处替换的是abc.png图片）

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    WebResourceResponse response = new WebResourceResponse(mimeType, "utf-8", is);
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

        wvReport.stopLoading();
        wvReport.removeAllViews();
        wvReport.destroy();
        finish();
    }
}
