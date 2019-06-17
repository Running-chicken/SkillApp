package com.cc.skillapp.entity;

import java.util.List;

public class TuPaiCalendarEntity {


    /**
     * data : [{"announcementCount":2,"closingCount":2,"completetionCount":2,"date":"2017-10-17","hasAnnouncement":false,"hasClosing":false,"hasCompletetion":false,"hasPassIn":false,"passInCount":2}]
     * meta : {"cached":true,"code":0,"encrypted":true,"message":"string","structure":1}
     */

    public MetaBean meta;
    public List<CalendarDateBean> data;

    public static class MetaBean {
        /**
         * cached : true
         * code : 0
         * encrypted : true
         * message : string
         * structure : 1
         */

        public boolean cached;
        public int code;
        public boolean encrypted;
        public String message;
        public int structure;
    }

    public static class CalendarDateBean {
        /**
         * announcementCount : 2
         * closingCount : 2
         * completetionCount : 2
         * date : 2017-10-17
         * hasAnnouncement : false
         * hasClosing : false
         * hasCompletetion : false
         * hasPassIn : false
         * passInCount : 2
         */

        public int announcementCount;
        public int closingCount;
        public int completetionCount;
        public String date;
        /**公告*/
        public boolean hasAnnouncement;
        /**成交*/
        public boolean hasClosing;
        /**截止*/
        public boolean hasCompletetion;
        /**流拍*/
        public boolean hasPassIn;
        public int passInCount;

        public int month;
        public int day;
    }
}
