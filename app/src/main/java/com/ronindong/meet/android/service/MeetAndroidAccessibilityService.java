package com.ronindong.meet.android.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.ronindong.meet.android.dao.IAccessbilityAction;

import java.util.List;

/**
 * @author donghailong
 */
public class MeetAndroidAccessibilityService extends AccessibilityService
        implements IAccessbilityAction {

    private AccessibilityManager manager;
    private Context context;
    private Handler handler = new Handler();

    public void init(Context cx) {
        this.context = cx.getApplicationContext();
        manager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void performBack() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                performGlobalAction(GLOBAL_ACTION_BACK);
            }
        }, 1000);
    }

    @Override
    public void performScrollUp() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
        }, 1000);
    }

    @Override
    public void performScrollDown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
            }
        }, 1000);
    }

    @Override
    public void performViewClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
    }

    @Override
    public AccessibilityNodeInfo findViewByText(String text) {
        return findViewByText(text, false);
    }

    @Override
    public AccessibilityNodeInfo findViewByText(String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList =
                accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    @Override
    public void clickTextViewByText(String text) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo);
                    break;
                }
            }
        }
    }

    @Override
    public void performInputText(AccessibilityNodeInfo info, String text) {

    }

    @Override
    public boolean checkAccessbilityEnabled(String serviceName) {
        return false;
    }
}
