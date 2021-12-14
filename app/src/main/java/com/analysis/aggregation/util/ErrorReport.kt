package com.analysis.aggregation.util

import android.content.Context
import android.os.Process
import android.text.TextUtils
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * 自定义上报错误到第三方平台
 * 已接入bugly、友盟
 *
 * @author afa
 */
class ErrorReport {
    companion object {

        private fun initUmeng(context: Context) {
            MobclickAgent.setCatchUncaughtExceptions(true)
            MobclickAgent.setSessionContinueMillis(1)
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
            UMConfigure.setLogEnabled(true)
            UMConfigure.setEncryptEnabled(true)
            UMConfigure.setProcessEvent(true)

            // SDK预初始化函数不会采集设备信息，也不会向友盟后台上报数据。
            // preInit预初始化函数耗时极少，不会影响App首次冷启动用户体验
            UMConfigure.preInit(context, Constant.UMENG_KEY, Constant.UMENG_CHANNEL)
            //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
            UMConfigure.init(
                context, Constant.UMENG_KEY, Constant.UMENG_CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, ""
            )

            // 支持在子进程中统计自定义事件
            UMConfigure.setProcessEvent(true)
        }

        private fun initBugly(context: Context) {
            // 获取当前包名
            val packageName = context.packageName
            // 获取当前进程名
            val processName = getProcessName(Process.myPid())
            // 设置是否为上报进程
            val strategy = UserStrategy(context)
            strategy.isUploadProcess = processName == null || processName == packageName
            /**
             * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
             *
             * 输出详细的Bugly SDK的Log；
             * 每一条Crash都会被立即上报；
             * 自定义日志将会在Logcat中输出。
             * 建议在测试阶段建议设置成true，发布时设置为false。
             *
             */
            CrashReport.initCrashReport(
                context.applicationContext,
                Constant.BUGLY_APIID, true, strategy
            )
        }

        @JvmStatic
        fun reportError(context: Context, obj: Any?) {
            CrashReport.setUserSceneTag(context.applicationContext, 208685)
            //友盟定义错误上报
            if (obj is Throwable) {
                //友盟-throwable是Throwable格式
                MobclickAgent.reportError(context.applicationContext, obj as Throwable?)
                //bugly上报
                CrashReport.postCatchedException(obj as Throwable?)
            } else if (obj is String) {
                //友盟-errorContent是String格式
                MobclickAgent.reportError(context, obj as String?)
                CrashReport.postCatchedException(Throwable(obj as String?))
            }
        }

        /**
         * 初始化接口一个，支持四个参数：context，友盟appkey，bugly appkey，channel
         * 手动上报错误接口，支持异常即可
         *
         * @param context
         */
        @JvmStatic
        fun init(context: Context, umsdkAppkey: String?, buglyAppkey: String?, channel: String?) {
            Constant.UMENG_KEY = umsdkAppkey
            Constant.UMENG_CHANNEL = channel
            Constant.BUGLY_APIID = buglyAppkey
            Constant.BUGLY_KEY = buglyAppkey
            init(context)
        }

        private fun init(context: Context) {
            initBugly(context.applicationContext)
            initUmeng(context.applicationContext)
        }

        /**
         * 获取进程号对应的进程名
         *
         * @param pid 进程号
         * @return 进程名
         */
        private fun getProcessName(pid: Int): String? {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
                var processName = reader.readLine()
                if (!TextUtils.isEmpty(processName)) {
                    processName = processName.trim { it <= ' ' }
                }
                return processName
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            } finally {
                try {
                    reader?.close()
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }
            return null
        }
    }
}