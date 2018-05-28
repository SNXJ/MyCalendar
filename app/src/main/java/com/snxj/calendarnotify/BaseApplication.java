package com.snxj.calendarnotify;

import android.app.Application;
import android.content.Context;

/**
 * @Author: SNXJ
 * @Date : 2018/5/5 0005.
 * @Describe :
 **/
public class BaseApplication extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

}
