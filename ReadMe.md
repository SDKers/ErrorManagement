# 概述
使用第三方的统计平台，管理日志信息。比如：使用友盟统计、bugly集成数据上报、错误日志收集。


# 目标
在开发阶段，使用第三方平台进行日志收集。

- 初始化接口一个，支持四个参数：context，友盟appkey，bugly appkey，channel
- 手动上报错误接口，支持异常即可

## 通过继承的demo，生成aar文件，在工程中通过脚本动态引入。


## 使用方法
> 接入方法
- 在application的`onCreate()`方法中调用：
  `java
  ErrorReport.init(this,
    Constant.UMENG_KEY,
    Constant.BUGLY_APIID,
    Constant.UMENG_CHANNEL);
  `

> 上报错误日志
- 上报string类型的错入:`ErrorReport.reportError(this, "xxxxx");`
- 上报throwable类型的错误:`ReportUtils.reportError(this, new Throwable("test for throwable."));`

> 生成aar方法
在工程的根build.gradle文件中修改`isDebug`字段，当`isDebug=true`时，生成aar文件，
当`isDebug=false`时，生成apk文件。
