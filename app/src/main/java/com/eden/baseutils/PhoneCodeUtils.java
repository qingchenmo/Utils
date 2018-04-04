package com.eden.baseutils;

import android.view.View;
import android.widget.TextView;

/**
 * @autor zcs
 * @time 2017/9/26
 * @describe 根据手机号获取验证码工具类
 */

public abstract class PhoneCodeUtils {
    /**
     * 输入手机号码的控件，用于获取手机号
     */
    private TextView mPhoneView;
    /**
     * 点击获取验证码的控件
     */
    private TextView mClickView;

    /**
     * @param phoneView 输入手机号码的控件，用于获取手机号
     * @param clickView 点击获取验证码的控件
     */
    public PhoneCodeUtils(TextView phoneView, TextView clickView) {
        if (phoneView == null || clickView == null) {
            throw new NullPointerException("phoneView或者clickView可能为null");
        } else if (!(phoneView instanceof TextView) || !(clickView instanceof TextView)) {
            throw new ClassCastException("phoneView或者clickView必须是TextView或其子类");
        } else {
            this.mClickView = clickView;
            this.mPhoneView = phoneView;
            mClickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestPhoneCode(mPhoneView.getText().toString().trim());
                }
            });
        }
    }

    /**
     * 使用网络请求验证码
     * 请求成功之后调用setDownTime(60)方法进行倒计时
     *
     * @param phone 请求验证码的手机号
     */
    public abstract void requestPhoneCode(String phone);

    /**
     * 倒计时
     *
     * @param time
     */
    public void setDownTime(int time) {
        time = time - 1;
        final int time1 = time;
        mClickView.setEnabled(false);
        mClickView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (time1 != 0) {
                    setDownTime(time1);
                    mClickView.setText("重新获取" + time1 + "s");
                } else {
                    mClickView.setEnabled(TextVerifyUtils.isMobile(mPhoneView.getText().toString().trim()));
                    mClickView.setText("重新获取");
                }
            }
        }, 1000);
    }
}
