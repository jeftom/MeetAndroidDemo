package com.ronindong.meet.android.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.ronindong.meet.android.dao.IAccessbilityAction;

import java.util.List;


/**
 * @author donghailong
 */
public class BaseAccessibilityService extends AccessibilityService
        implements IAccessbilityAction {
    private static final String TAG = BaseAccessibilityService.class.getSimpleName();
    /**
     *
     */
    private AccessibilityManager mManager;

    public BaseAccessibilityService() {
//        mManager = (AccessibilityManager) getApplicationContext()
//                .getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

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
    public AccessibilityNodeInfo findViewByViewId(String viewId) {
        return findViewByViewId(viewId, true);
    }

    @Override
    public AccessibilityNodeInfo findViewByViewId(String viewId, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList =
                accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", text);
            clipboard.setPrimaryClip(clip);
            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
    }

    @Override
    public boolean checkAccessbilityEnabled(String serviceName) {
        List<AccessibilityServiceInfo> accessibilityServices =
                mManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
