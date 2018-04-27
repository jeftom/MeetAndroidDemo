package com.ronindong.meet.android.helper;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class CxHelper {
    private Context context;

    private CxHelper() {
    }

    public void init(Context cx) {
        this.context = cx.getApplicationContext();
    }

    /**
     *
     */
    public void openAccessSetting() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
