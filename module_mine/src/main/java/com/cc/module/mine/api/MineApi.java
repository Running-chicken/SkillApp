package com.cc.module.mine.api;


import com.cc.library.base.entity.BaseResponse;
import com.cc.module.mine.entity.MenuIconBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MineApi {


    @POST("/yjyz.web.api/v1/menuIcon")
    Observable<BaseResponse<List<MenuIconBean>>> getMenuIcon(@Body Map<String,Object> params);

}
