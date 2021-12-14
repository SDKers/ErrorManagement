package com.analysis.libreport;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.Date;


/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/11/30 4:46 PM
 * @author: sanbo
 */
public class P {
    private static boolean isinitum = false;
    private static boolean isinitBugly = false;

    public static void initReport(Context ctx, String umKey, String buglyKey, String channel) {
        initBugly(ctx, buglyKey, channel);
        inituum(ctx, umKey, channel);
    }

    public static void report(Context ctx, Throwable e) {
        if (isinitBugly) {
//           reportBugly(ctx,e);

            CrashReport.postCatchedException(e);
        }
        if (isinitum) {
//            reportUm(ctx,e);
            MobclickAgent.reportError(ctx, e);
        }
    }

    static void initBugly(Context ctx, String key, String channel) {
        if (isinitBugly) {
            return;
        }
        CrashReport.initCrashReport(ctx, key, false);
        CrashReport.setUserId(channel);
        CrashReport.setUserSceneTag(ctx, new Date().getDate());
        isinitBugly = true;
    }


    static void inituum(Context ctx, String key, String channel) {
        if (isinitum) {
            return;
        }
        UMConfigure.preInit(ctx, key, channel);
        UMConfigure.init(ctx, key, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setCatchUncaughtExceptions(true);
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);
        UMConfigure.setProcessEvent(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        isinitum = true;
    }


}