//package com.analysis.aggregation.util;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.tencent.bugly.crashreport.CrashReport;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
///**
// * 自定义上报错误到第三方平台
// * 已接入bugly、友盟
// *
// * @author afa
// */
//public class ErrorReport {
//    private ErrorReport() {
//    }
//
//    private final static class Holder {
//        static ErrorReport instance = new ErrorReport();
//    }
//
//    public static ErrorReport get() {
//        return Holder.instance;
//    }
//
//    /**
//     * 初始化接口一个，支持四个参数：context，友盟appkey，bugly appkey，channel
//     * 手动上报错误接口，支持异常即可
//     *
//     * @param context
//     */
//    public void init(Context context, String umsdkAppkey, String buglyAppkey, String channel) {
//        Constant.UMENG_KEY = umsdkAppkey;
//        Constant.UMENG_CHANNEL = channel;
//        Constant.BUGLY_APIID = buglyAppkey;
//        Constant.BUGLY_KEY = buglyAppkey;
//        init(context);
//    }
//
//    private void init(Context context) {
//        initBugly(context.getApplicationContext());
//        initUmeng(context.getApplicationContext());
//    }
//
//    private void initUmeng(Context context) {
//        MobclickAgent.setCatchUncaughtExceptions(true);
//        MobclickAgent.setSessionContinueMillis(1);
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//        UMConfigure.setLogEnabled(true);
//        UMConfigure.setEncryptEnabled(true);
//        UMConfigure.setProcessEvent(true);
//
//        // SDK预初始化函数不会采集设备信息，也不会向友盟后台上报数据。
//        // preInit预初始化函数耗时极少，不会影响App首次冷启动用户体验
//        UMConfigure.preInit(context, Constant.UMENG_KEY, Constant.UMENG_CHANNEL);
//        //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
//        UMConfigure.init(context, Constant.UMENG_KEY, Constant.UMENG_CHANNEL,
//                UMConfigure.DEVICE_TYPE_PHONE, "");
//
//        // 支持在子进程中统计自定义事件
//        UMConfigure.setProcessEvent(true);
//    }
//
//
//    private void initBugly(Context context) {
//        // 获取当前包名
//        String packageName = context.getPackageName();
//        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        /**
//         * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//         *
//         *          输出详细的Bugly SDK的Log；
//         *          每一条Crash都会被立即上报；
//         *          自定义日志将会在Logcat中输出。
//         *          建议在测试阶段建议设置成true，发布时设置为false。
//
//         */
//
//        CrashReport.initCrashReport(context.getApplicationContext(),
//                Constant.BUGLY_APIID, true, strategy);
//    }
//
//    /**
//     * 获取进程号对应的进程名
//     *
//     * @param pid 进程号
//     * @return 进程名
//     */
//    private static String getProcessName(int pid) {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
//            String processName = reader.readLine();
//            if (!TextUtils.isEmpty(processName)) {
//                processName = processName.trim();
//            }
//            return processName;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//
//    public void reportError(Context context, Object obj) {
//        CrashReport.setUserSceneTag(context.getApplicationContext(), 208685);
//        //友盟定义错误上报
//        if (obj instanceof Throwable) {
//            //友盟-throwable是Throwable格式
//            MobclickAgent.reportError(context.getApplicationContext(), (Throwable) obj);
//            //bugly上报
//            CrashReport.postCatchedException((Throwable) obj);
//        } else if (obj instanceof String) {
//            //友盟-errorContent是String格式
//            MobclickAgent.reportError(context, (String) obj);
//
//            CrashReport.postCatchedException(new Throwable((String) obj));
//        }
//
//    }
//
//}
