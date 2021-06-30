package com.cc.skillapp.entity;

import java.io.Serializable;

/**
 * 新中转接口统一返回根实体类
 */
public class BaseRootEntity<T> implements Serializable {
    /**填充接口返回数据实体类*/
    public T data;
    /**中转统一返回meta*/
    public MetaEntity meta;
    public static class MetaEntity {
        /**
         * code : 0
         * message : string
         * timestamp : 0
         */

        public String code;
        public String message;
        public String timestamp;
    }
}
