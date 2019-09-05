package com.cc.skillapp.entity;

import java.io.Serializable;

/**
 * Created by User on 2019-04-10.
 */

public class AllDataEntity implements Serializable {

    public String role;
    public String total;
    public String page;
    public String pagesize;
    public String timeUsed;

    public String type;//doufang（抖房）   live （直播）
    public String score;
    public String city;//城市
    public String title;//标题
    public String id;
    public String img;//封面图
    public String date;
    public String url;
    public String click;
    public String author;// 作者
    public String userimg;//用户图像
    public String videoUrl;//视频地址
    public String videoWidth;
    public String videoHigh;
    public String liveState;//直播中    直播预告  直播回放
    public String videoListType;//doufangpic
    public String gif;
}
