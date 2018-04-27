package com.ronindong.meet.android.dao;

import android.view.accessibility.AccessibilityNodeInfo;

public interface IAccessbilityAction {
    /**
     * 后退事件
     */
    void performBack();

    /**
     *
     */
    void performScrollUp();

    /**
     *
     */
    void performScrollDown();

    /**
     * @param nodeInfo
     */
    void performViewClick(AccessibilityNodeInfo nodeInfo);

    /**
     * @param text
     * @return
     */
    AccessibilityNodeInfo findViewByText(String text);

    AccessibilityNodeInfo findViewByText(String text, boolean clickable);

    void clickTextViewByText(String text);

    void clickTextViewByViewId(String viewId);

    void performInputText(AccessibilityNodeInfo info, String text);

    boolean checkAccessbilityEnabled(String serviceName);
}
