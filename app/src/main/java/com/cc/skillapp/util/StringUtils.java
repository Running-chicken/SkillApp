package com.cc.skillapp.util;

public class StringUtils {



    /**
     * 判断是否为空
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0 || "null".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

}
