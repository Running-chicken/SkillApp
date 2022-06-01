package com.cc.module.test.entity;

public class LocationEntity {


    public String msg;
    public boolean succeed;
    public DataDTO data;
    public String code;
    public Object requestId;

    public static class DataDTO {
        public String cityName;
        public String cityCode;
        public String cityDomain;
        public int isEsf;
        public int isNewhouse;
        public int isRent;
        public int isShop;
        public int isOffice;
        public boolean isDefaultCity;
        public String cityShort;
        public int minorCityCode;
        public double latitude;
        public double longitude;
    }
}
