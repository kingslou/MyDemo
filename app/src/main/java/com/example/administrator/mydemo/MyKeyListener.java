package com.example.administrator.mydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by Administrator
 */
public class MyKeyListener {
    static final String TAG = "HomeKeyListener";
    private Context mContext;
    private IntentFilter mFilter;
    private OnHomePressedListener mListener;
    private InnerRecevier mRecevier;
    /**
     * Home键监听构造初始化
     * @param context
     */
    public MyKeyListener(Context context) {
        mContext = context;
        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mFilter.addAction(Intent.ACTION_SCREEN_ON);

    }

    /**
     * 回调接口
     * @author miaowei
     *
     */
    public interface OnHomePressedListener {

        /**
         * Home键短按
         */
        public void onHomePressed();
        /**
         * Home键长按
         */
        public void onHomeLongPressed();
        /**
         * 监听电源键/开
         */
        public void onScreenPressed();
        /**
         * 监听电源键/关
         */
        public void offScreenPressed();
    }

    /**
     * 设置监听
     * @param listener
     */
    public void setOnHomePressedListener(OnHomePressedListener listener) {
        mListener = listener;
        mRecevier = new InnerRecevier();
    }


    /**
     * 开始监听,注册广播
     */
    public void startHomeListener() {
        if (mRecevier != null) {
            mContext.registerReceiver(mRecevier, mFilter);
        }
    }

    /**
     * 停止监听，注销广播
     */
    public void stopHomeListener() {
        if (mRecevier != null) {
            mContext.unregisterReceiver(mRecevier);
        }
    }
    /**
     * 广播接收
     * @author miaowei
     *
     */
    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.e(TAG, "action:" + action + ",reason:" + reason);
                    if (mListener != null) {
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            // 短按home键
                            mListener.onHomePressed();
                        } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            // 长按home键
                            mListener.onHomeLongPressed();
                        }
                    }
                }
            }//监听电源键开
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {

                mListener.offScreenPressed();

            }else if (action.equals(Intent.ACTION_SCREEN_ON)) {

                mListener.onScreenPressed();
            }
        }
    }
}



