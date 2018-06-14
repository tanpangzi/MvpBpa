package com.android.base.activity.mvp.test;


import com.android.base.activity.mvp.MvpBaseModel;

import java.util.ArrayList;
import java.util.List;

public class MvpTestMode extends MvpBaseModel {

    /**
     * 1.	删除交易所(app/auth/coin/delUserExchange)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/4 12:19
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/4 12:19
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void loadUserInfo(String exchangeCodefinal, final OnRequestListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    ArrayList<String> data = new ArrayList<String>();
                    for (int i = 1; i < 8; i++) {
                        data.add("item" + i);
                    }
                    if (null != listener) {
                        listener.onSuccess(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFailed();
                }
            }
        }).start();
    }

    public interface OnRequestListener {

        void onSuccess(List<String> data);

        void onFailed();
    }
}
