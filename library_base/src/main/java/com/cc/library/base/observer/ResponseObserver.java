package com.cc.library.base.observer;

import android.net.ParseException;

import com.cc.library.base.entity.BaseResponse;
import com.cc.library.base.exception.ApiException;
import com.cc.library.base.exception.ExceptionCode;
import com.cc.library.base.exception.NoDataException;
import com.cc.library.base.exception.NoNetworkException;
import com.cc.library.base.util.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class ResponseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException exception = ((HttpException) e);

            int code = exception.code();
            if (code <= 500) {
                String msg = "服务器异常";
                String apiCode = code + "";

                try {
                    msg = ((HttpException) e).response().errorBody().string();
                    BaseResponse response = new Gson().fromJson(msg, BaseResponse.class);
                    if (response != null) {
                        if (response.getMsg() != null)
                            msg = response.getMsg();
                        if (response.getCode() != null)
                            apiCode = response.getCode();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (JsonParseException e2) {
                    e2.printStackTrace();
                    msg = "数据解析异常";
                } catch (Exception e3) {
                    e3.printStackTrace();
                    msg = "数据异常 "+e3.getMessage();
                }

                onError(apiCode, msg);

            } else {
                onError(code + "", "服务器异常");
            }

        } else if (e instanceof ApiException) {
            ApiException exception = ((ApiException) e);
            onError(exception.getCode(), exception.getMsg());
        } else if (e instanceof NoDataException) {
            onSuccess(null);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            onError(ExceptionCode.JSON_PARSE_ERROR, "数据解析异常");
        } else if (e instanceof NoNetworkException) {
            onError(ExceptionCode.NO_NERWORK_ERROR, "当前网络不可用，请检查您的网络设置");
        } else if (e instanceof SocketTimeoutException || e instanceof SSLHandshakeException) {
            onError(ExceptionCode.TIMEOUT_ERROR, "网络超时，请重试");
        } else {
            onError(ExceptionCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T data);

    public void onError(String code, String msg) {
        Utils.log(getClass(),msg);
    }
}