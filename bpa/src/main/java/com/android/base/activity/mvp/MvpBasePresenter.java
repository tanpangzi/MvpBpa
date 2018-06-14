package com.android.base.activity.mvp;

import android.os.Handler;
import android.os.Looper;

/**
 * MvpBasePresenter
 * <p>
 * MVP中所有的Presenter层都必须继承自MvpBasePresenter
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class MvpBasePresenter<V extends MvpBaseView> implements MvpBaseContract.BasePresenter {

    // 如果使用异步网络请求可以删除handler
    public Handler handler;
    public V view;

    public MvpBasePresenter(V view) {
        handler = new Handler(Looper.getMainLooper());
        this.view = view;
    }

    @Override
    public void onDestroy() {
        if (view != null) {
            view = null;
            System.gc();
        }
    }

    /**
     * 加载数据（通过Mode加载数据）
     * <p>
     * 对外只暴露一个 loadData 方法
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> CreateTime: 2018/6/13 14:53
     * <br> UpdateTime: 2018/6/13 14:53
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public abstract void loadData();
}
