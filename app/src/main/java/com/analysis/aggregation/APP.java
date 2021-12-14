package com.analysis.aggregation;

import android.app.Application;

import com.analysis.aggregation.util.Constant;
import com.analysis.aggregation.util.ErrorReport;

/**
 * @author afa
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ErrorReport.init(this,
                Constant.UMENG_KEY,
                Constant.BUGLY_APIID,
                Constant.UMENG_CHANNEL);
    }
}
