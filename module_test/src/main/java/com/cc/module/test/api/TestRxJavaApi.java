package com.cc.module.test.api;

import com.cc.library.base.entity.TestLisTEntity;
import com.cc.module.test.entity.LocationEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestRxJavaApi {

    @POST("/yjyz.web.api/v1/menuIcon")
    Observable<TestLisTEntity> getMenuIcon(@Body Map<String,Object> params);

    @POST("/yjyz.web.api/v1/app/location")
    Observable<LocationEntity> getLocationInfo(@Body Map<String,Object> params);
}
