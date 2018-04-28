package com.ronindong.meet.android.app;

import android.app.Application;

import com.ronindong.meet.android.helper.CxHelper;
import com.ronindong.meet.android.manager.SingletonManager;

/**
 * @author donghailong
 */
public class MeetAndroidApplication extends Application {

    private static MeetAndroidApplication inst;

    public static MeetAndroidApplication getInstance() {
        return inst;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inst = this;
        init();

    }

    private void init() {
        SingletonManager.get(CxHelper.class).init(this);
    }
}
