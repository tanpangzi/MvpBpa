package com.android.base.activity.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * 容器 BaseModel  BaseView  BasePresenter
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public interface MvpBaseContract {

    interface BaseView {

        /**
         * onCreate
         * <p>
         * 在 {@link android.app.Activity#onCreate(Bundle)} 内调用
         * 在 {@link android.app.Fragment#onCreate(Bundle)} 内调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午2:31:19
         * <br> UpdateTime: 2016年4月21日,下午2:31:19
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         */
        void onCreate();

        /**
         * 获取显示view的xml文件ID
         * <p>
         * 在 {@link #onCreate()} 内调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午2:31:19
         * <br> UpdateTime: 2016年4月21日,下午2:31:19
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         *
         * @return xml文件ID
         */
        int getContentViewId();

        /**
         * 根据 {@link #getContentViewId()}的资源ID，初始化ContentView
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午2:31:19
         * <br> UpdateTime: 2016年4月21日,下午2:31:19
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         *
         * @return xml文件ID
         */
        View initContentView();

        /**
         * 控件查找
         * <p>
         * 在 {@link #initContentView()} 之后被调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午1:58:20
         * <br> UpdateTime: 2016年4月21日,下午1:58:20
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         */
        void findViews();

        /**
         * 获取上一个界面传送过来的数据
         * <p>
         * 在{@link #findViews()}之后被调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午2:42:56
         * <br> UpdateTime: 2016年4月21日,下午2:42:56
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         */
        void initGetData(Bundle bundle);

        /**
         * 初始化应用程序，设置一些初始化数据都获取数据等操作
         * <p>
         * 在{@link #initGetData(Bundle bundle)}之后被调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午1:55:15
         * <br> UpdateTime: 2016年4月21日,下午1:55:15
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         */
        void init();

        /**
         * 组件监听模块
         * <p>
         * 在{@link #init()}后被调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午1:56:06
         * <br> UpdateTime: 2016年4月21日,下午1:56:06
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         */
        void widgetListener();

        /**
         * 用于网络请求成功后，初始化View的数据
         * <p>
         * 对外只暴露一个 initViewData 方法，如果有多个网络请求、多种返回数据类型 请根据 object instanceof XXXX，做区分
         * <p>
         * 在{@link MvpBasePresenter#loadData()} 之后被调用
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016年4月21日,下午1:55:15
         * <br> UpdateTime: 2016年4月21日,下午1:55:15
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         */
        void initViewData(Object object);
    }

    interface BaseModel {
    }

    interface BasePresenter {
        void onDestroy();
    }
}
