package com.example.library_base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import static android.net.ConnectivityManager.TYPE_WIFI;

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

    public static final int getNetWorkType(Context c) {
        ConnectivityManager conn = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn == null) {
            return -1;
        }
        NetworkInfo info = conn.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return -1;
        }

        int type = info.getType(); // MOBILE（GPRS）;WIFI
        if (type == TYPE_WIFI) {
            return TYPE_WIFI;
        } else {
            return 2;
        }
    }

}
