package com.analysis.libreport;

import android.content.Context;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/30 4:46 PM
 * @author: sanbo
 */
public class ErrorReport {
    private static boolean isinitum = false;
    private static boolean isinitBugly = false;

    public static void init(Context ctx, String umKey, String buglyKey, String channel) {
        initBugly(ctx, buglyKey, channel);
        inituum(ctx, umKey, channel);
    }

    public static void reportError(Context ctx, Throwable e) {
        if (isinitBugly) {
            reportBugly(ctx, e);
        }
        if (isinitum) {
            reportUm(ctx, e);
        }
    }

    private static void reportUm(Context ctx, Throwable e) {
        MobclickAgent.reportError(ctx.getApplicationContext(), e);
    }

    private static void reportBugly(Context ctx, Throwable e) {
        CrashReport.setUserSceneTag(ctx.getApplicationContext(), 208685);
        CrashReport.postCatchedException(e);

    }

    static void initBugly(Context ctx, String key, String channel) {
        if (isinitBugly) {
            return;
        }
        CrashReport.initCrashReport(ctx, key, false);
        CrashReport.setUserId(channel);

        CrashReport.setUserSceneTag(ctx, getIntToday());
        isinitBugly = true;
    }

    static int getIntToday() {
        String todafy =
                new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
        return Integer.parseInt(todafy);
    }

    static void inituum(Context ctx, String key, String channel) {
        if (isinitum) {
            return;
        }
        Log.i("afa", "inituum: key=" + key + ",channel=" + channel);
        UMConfigure.preInit(ctx, key, channel);
        UMConfigure.init(ctx, key, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setSessionContinueMillis(1);
        MobclickAgent.setCatchUncaughtExceptions(true);
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);
        UMConfigure.setProcessEvent(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        isinitum = true;


        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);

    }


}
