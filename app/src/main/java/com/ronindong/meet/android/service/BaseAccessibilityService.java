package com.ronindong.meet.android.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.ronindong.meet.android.dao.IAccessbilityAction;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @author donghailong
 */
public abstract class BaseAccessibilityService extends AccessibilityService
        implements IAccessbilityAction {
    public static final int DELAY_TIME = 1000;
    private AccessibilityManager mManager;
    private Context mContext;
    private Handler mHandler = new Handler();
    /**
     * 监听APP包
     */
    private String[] packageNames = {"com.android.packageinstaller",
            "com.android.settings"};

    public BaseAccessibilityService() {
        mManager = (AccessibilityManager) getApplicationContext()
                .getSystemService(Context.ACCESSIBILITY_SERVICE);
    }


    @Override
    public void performBack() {
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    @Override
    public void performScrollUp() {
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    @Override
    public void performScrollDown() {
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
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
            Log.i(TAG, "accessibilityNodeInfo is null");
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo
                .findAccessibilityNodeInfosByText(text);
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
    public void clickTextViewByViewId(String viewId) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            Log.i(TAG, "accessibilityNodeInfo is null");
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo
                .findAccessibilityNodeInfosByViewId(viewId);
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
