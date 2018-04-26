package com.ronindong.meet.android.app;

import android.app.Application;

import com.ronindong.meet.android.helper.CxHelper;
import com.ronindong.meet.android.manager.SingletonManager;
import com.ronindong.meet.android.service.MeetAndroidAccessibilityService;

public class MeetAndroidApplication extends Application {

    private static volatile MeetAndroidApplication inst;

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
        SingletonManager.get(CxHelper.class).init(inst);
        SingletonManager.get(MeetAndroidAccessibilityService.class).init(inst);

    }
}
