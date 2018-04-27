package com.ronindong.meet.android.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import static android.content.ContentValues.TAG;

/**
 * @author donghailong
 */
public class AutoOpenRedPacketService extends BaseAccessibilityService {
    public static final String WX_PACKAGE = "com.tencent.mm";
    public static final String GET_RED_PACKAGE = "微信红包";
    public static final String OPEN_RED_PACKAGE_VIEW_ID = "com.tencent.mm:id/c31";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "event type:" + event.getEventType());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
                && event.getPackageName().equals(WX_PACKAGE)) {
            clickTextViewByText(GET_RED_PACKAGE);
            clickTextViewByViewId(OPEN_RED_PACKAGE_VIEW_ID);
        }
    }

    @Override
    public void onInterrupt() {

    }
}
