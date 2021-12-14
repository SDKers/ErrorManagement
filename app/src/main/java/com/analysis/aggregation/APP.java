package com.analysis.aggregation;

import android.app.Application;

import com.analysis.libreport.ErrorReport;

/**
 * @author afa
 */
public class APP extends Application {
    //    友盟的注册信息
    public static String UMENG_KEY = "61b6baebe0f9bb492b903539";
    public static String UMENG_CHANNEL = "Umeng";
    //bugly注册信息
    public static String BUGLY_APIID = "27f5405e8a";
    public static String BUGLY_KEY = "b0639f25-0f5d-4f37-b630-70ea19bda4b9";

    @Override
    public void onCreate() {
        super.onCreate();

        ErrorReport.init(this,
                UMENG_KEY,
                BUGLY_APIID,
                UMENG_CHANNEL);
    }
}
