package com.ronindong.meet.android.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;


/**
 * @author donghailong
 */
public class AutoOpenRedPacketService extends BaseAccessibilityService {
    private static final String TAG = AutoOpenRedPacketService.class.getSimpleName();
    /**
     * wx包名
     */
    public static final String WX_PACKAGE_NAME = "com.tencent.mm";
    /**
     * text
     */
    public static final String WX_RED_PACKAGE_TEXT = "微信红包";
    /**
     * 开 对应的viewId（随着微信的更新，可能会变）
     */
    public static final String WX_OPEN_RED_PACKAGE_VIEW_ID = "com.tencent.mm:id/c31";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "event type:" + event.getEventType());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
                && event.getPackageName().equals(WX_PACKAGE_NAME)) {
            clickTextViewByText(WX_RED_PACKAGE_TEXT);
            clickTextViewByViewId(WX_OPEN_RED_PACKAGE_VIEW_ID);
        }
    }

    @Override
    public void onInterrupt() {

    }
}
