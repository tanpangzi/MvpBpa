package com.android.base.activity.mvp;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.android.base.R;
import com.android.base.utils.BroadcastFilters;
import com.android.base.utils.AppManagerUtil;
import com.android.base.utils.LogUtil;
import com.android.base.utils.ToastUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基类Activity
 * <p>
 * 所有的Activity都继承自此Activity，并实现基类模板方法，本类的目的是为了规范团队开发项目时候的开发流程的命名， 基类也用来处理需要集中分发处理的事件、广播、动画等，如开发过程中有发现任何改进方案都可以一起沟通改进。
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class MvpBaseActivity<V extends MvpBaseView, T extends MvpBasePresenter<V>> extends FragmentActivity {

    /** MvpBasePresenter */
    public T basePresenter;
    /** MvpBaseView */
    public V baseView;
    /** 广播接收器 */
    protected BroadcastReceiver receiver;
    /** 广播过滤器 */
    protected IntentFilter filter;
    /** SystemBarTintManager 用于修改状态栏的颜色 */
    protected SystemBarTintManager tintManager;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManagerUtil.getAppManager().addActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        try {
            Type[] types = getGenericSuperclass();
            if (types == null) {
                return;
            }

            Class c = (Class) types[0];
            /*以下调用带参的、私有构造函数*/
            Constructor c0 = c.getDeclaredConstructor(getClass());
            c0.setAccessible(true);
            baseView = (V) c0.newInstance(this);

            c = (Class) types[1];
            /*以下调用无参的、私有构造函数*/
            //            c0 = c.getDeclaredConstructor();
            //            c0.setAccessible(true);
            //            basePresenter = (T) c0.newInstance();
            //            basePresenter.setView(baseView);
            /*以下调用带参的、私有构造函数*/
            c0 = c.getDeclaredConstructor(baseView.getClass());
            c0.setAccessible(true);
            basePresenter = (T) c0.newInstance(baseView);


            baseView.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerReceiver();
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                baseView.finishActivity();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onStop() {
        ToastUtil.cancelToast(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        AppManagerUtil.getAppManager().removeActivity(this);
        if (basePresenter != null) {
            basePresenter.onDestroy();
            basePresenter = null;
            System.gc();
        }
    }

    /**
     * 反射获得泛型参数
     *
     * @return 泛型参数集合
     */
    private Type[] getGenericSuperclass() {

        //getSuperclass()获得该类的父类
        //        System.out.println(klass.getSuperclass());
        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type type = getClass().getGenericSuperclass();

        if (type == null || !(type instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //getActualTypeArguments 获取参数化类型的数组，泛型可能有多个
        Type[] types = parameterizedType.getActualTypeArguments();

        if (types == null || types.length == 0) {
            return null;
        }
        return types;
    }

    /**
     * 设置广播过滤器
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:43:15
     * <br> UpdateTime: 2016年5月22日,下午1:43:15
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected void setFilterActions() {
        //   添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
        filter = new IntentFilter();
        filter.addAction(BroadcastFilters.ACTION_CHANGE_LANGUAGE);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 网络变化广播
        filter.addAction(BroadcastFilters.ACTION_APP_EXIT);// 程序退出
        filter.addAction(BroadcastFilters.ACTION_LOCATION_COMPLETE);// 定位完成
        filter.addAction(BroadcastFilters.ACTION_TONKEN_EXPIRED);// TONKEN过期
    }

    /**
     * 注册广播Activity
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:41:25
     * <br> UpdateTime: 2016年5月22日,下午1:41:25
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    protected void registerReceiver() {
        setFilterActions();
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
                    MvpBaseActivity.this.onReceive(intent);
                }
            }
        };
        registerReceiver(receiver, filter);

    }

    /**
     * 广播监听回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:40:30
     * <br> UpdateTime: 2016年5月22日,下午1:40:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param intent
     *         广播附带内容
     */
    protected void onReceive(Intent intent) {
        //   父类集中处理共同的请求
        if (intent.getAction().equals(BroadcastFilters.ACTION_CHANGE_LANGUAGE)) {// 修改语言
            baseView.onCreate();
        } else if (intent.getAction().equals(BroadcastFilters.ACTION_TONKEN_EXPIRED)) {// Token失效
            if (!this.getClass().getName().equals("LoginActivity")) {
                tokenInvalid();
            }
            LogUtil.i("Token失效......" + this.getClass().getName());
        }
    }

    /**
     * 登录过期、Token无效 请重新登录 或者被抢登
     */
    public void tokenInvalid() {
        //        try {
        //            //            JPushUtil.getInstance(this).setAlias("");
        ToastUtil.showToast(this, getString(R.string.activity_token_error));
        //            //            dismissLoadingDialog();
        //
        //            BaseApplication.setUserInfoBean(null);
        AppManagerUtil.getAppManager().finishAllActivity();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        IntentUtil.gotoActivityToTopAndFinish(this,  Class.forName(mActivityName));
        //        IntentUtil.gotoActivityToTopAndFinish(this, LoginActivity.class);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

        // 沉浸模式 会导致输入法与edittext不能顶起
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        // 设置状态栏背景色
        setTintResource(R.color.transparent);
        //  tintManager.setTintColor(Color.parseColor("#00838F"));
    }

    /**
     * 设置状态栏背景色
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/1/22 9:50
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/1/22 9:50
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param resId
     *         颜色资源ID
     */
    public void setTintResource(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager != null) {
                // 设置状态栏背景色
                tintManager.setTintResource(resId);
            }
        }
    }

}