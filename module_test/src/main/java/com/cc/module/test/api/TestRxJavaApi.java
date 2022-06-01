package com.cc.module.test.api;

import com.cc.library.base.entity.TestLisTEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestRxJavaApi {

    @POST("/yjyz.web.api/v1/menuIcon")
    Observable<TestLisTEntity> getMenuIcon(@Body Map<String,Object> params);

}
