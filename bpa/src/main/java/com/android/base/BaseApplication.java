package com.android.base;

import android.app.Application;


/**
 * 全局公用Application类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月24日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BaseApplication extends Application {

    /** 全局TApplication  获取全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示 */
    protected static BaseApplication application;

    /**
     * 获取全局TApplication
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/3/7 17:51
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/3/7 17:51
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return BaseApplication
     */
    public static BaseApplication getInstance() {
        if (application == null) {
            synchronized (BaseApplication.class) {
                if (application == null) {
                    application = new BaseApplication();
                }
            }
        }
        return application;
    }

    /**
     * 整个应用程序的初始入口函数
     * <p>
     * 本方法内一般用来初始化程序的全局数据，或者做应用的长数据保存取回操作
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月24日, 下午2:12:17
     * <br> UpdateTime: 2016年12月24日, 下午2:12:17
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    @Override
    public void onCreate() {
        application = this;
        //applicationId要和包路径相同，才能正常重启APP WelcomeActivity
        super.onCreate();
    }
}