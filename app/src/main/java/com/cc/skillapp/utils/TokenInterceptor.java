package com.cc.skillapp.utils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token拦截器，用于请求时添加token请求头，以及保存token响应头
 *
 * @Author zrh
 * @Created 4/20/19
 * @Editor zrh
 * @Edited 5/11/19
 * @Type
 * @Layout
 * @Api
 */
public class TokenInterceptor implements Interceptor {

    private static final String HEADER_KEY = "authority"; // 可选城市接口中的cityDomain字段。

    // 城市分公司
//    private static final String ORGAN_ID = "X-Organ-Id"; // 品牌ID
//    private static final String CITY_CODE = "X-City-Code"; // 城市ID
    // https://wiki.yjzf.com/pages/viewpage.action?pageId=5737763
    private static final String USER_AGENT = "User-Agent"; // APP客户端
    private static final String X_USER_AGENT = "x-user-agent"; // 登录平台
    private static final String X_DEVICE_NAME = "x-device-name"; // 登录设备
    private static final String USER_TOKEN = "authorization"; // 客户端
//    private static final String X_REQUEST_WITH = "X-Requested-With"; // AJAX请求
    // HeaderKeysBean

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        requestBuilder.header(HEADER_KEY, "nn-beta.yjzf.com");

        requestBuilder.header(USER_AGENT, "YJZF/2.11.0 (Redmi_M2007J17C; Android 11; zh_CN)");
        requestBuilder.header(X_USER_AGENT, "YJZF/2.11.0");
        requestBuilder.header(X_DEVICE_NAME, "Redmi_M2007J17C");
        requestBuilder.header("x-tenant-id", "610011560827831929");
        requestBuilder.header("X-City-Code", "450100");
        requestBuilder.header(USER_TOKEN,"eyJhbGciOiJSUzI1NiJ9.eyJkdCI6Im1vYmlsZSIsInR0IjoiMSIsInN1YiI6Ijc4NDMyOTE3MzI4NzA0NzE2OCIsIlVOb25jZSI6IjI2MjAxNTZjLTA2ODUtNGU4MS1iZGUwLWFiYzFmYTA4ZGJhMyIsInJvbGUiOiI2MTAwMTE1NjA4Mjc4MzE5MjkiLCJpc3MiOiJ5anpmLmNvbSIsImV4cCI6MTY1MzU2MTMyMywiaWF0IjoxNjUwOTY5MzIzLCJqdGkiOiI5MjU4YTA0Ny1iMmNhLTRhNWUtYWQ2NS0xMzNmMTVkOTZhY2UifQ.lCINgHg3dDSzweidgA9WTNVXDfBS75eLGCPD471sQUFau8rephHnOQVArMUy8fpAl8ofcegZ_IvVv53EKMIQ13Msx549M7aoZ6hSP_3XuCKIXb0q7pJnFaReyc40UvBM06cQjhVBjxowwtozCiRRTfgI9IVRzstDDqg6NFxjYaY");


        // 如果响应头中存在token则更新本地token
        Response response = chain.proceed(requestBuilder.build());

        return response;
    }
}
