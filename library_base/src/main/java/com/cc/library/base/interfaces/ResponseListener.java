package com.cc.library.base.interfaces;

import io.reactivex.disposables.Disposable;

public interface ResponseListener<T>{


    void loadSuccess(T data);

    void loadFailed(String errorCode,String errorMsg);

    void addDisposable(Disposable disposable);

}
