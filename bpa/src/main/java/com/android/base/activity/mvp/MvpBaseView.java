package com.android.base.activity.mvp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.android.base.R;
import com.android.base.utils.LoadingDialogUtil;
import com.android.base.utils.ToastUtil;

/**
 * MvpBaseView
 * <p>
 * MVP中所有的View层都必须继承自MvpBaseView
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/6/14
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class MvpBaseView<A extends MvpBaseActivity> implements MvpBaseContract.BaseView {

    /** 上下文 */
    protected A activity;
    /** 父视图 */
    private View viewParent;

    //    public MvpBaseView() {
    //    }

    public MvpBaseView(A activity) {
        this.activity = activity;
    }

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
    @Override
    public void onCreate() {
        try {
            if (activity.isFinishing()) {
                return;
            }
            switchLanguage();
            activity.setContentView(initContentView());
            activity.getWindow().setBackgroundDrawable(null);
            findViews();
            initGetData(activity.getIntent().getExtras());
            init();
            widgetListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 泛型:查找控件
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:40:30
     * <br> UpdateTime: 2016年5月22日,下午1:40:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param id
     *         控件ID
     *
     * @return 控件view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewByIds(int id) {
        if (viewParent == null) {
            viewParent = View.inflate(activity, getContentViewId(), null);
        }
        return (T) viewParent.findViewById(id);
    }

    @Override
    public View initContentView() {
        viewParent = View.inflate(activity, getContentViewId(), null);
        return viewParent;
    }

    // ************************************************************************等待对话框
    /** 等待对话框 */
    private LoadingDialogUtil loadingDialogUtil;

    public void showProgress() {
        showProgress(true);
    }

    public void showProgress(boolean cancelable) {
        showProgress(activity.getString(R.string.process_handle_wait), cancelable);
    }

    /**
     * 显示等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月23日,上午10:41:59
     * <br> UpdateTime: 2016年4月23日,上午10:41:59
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param processMsg
     *         如果内容不为空，则会显示提示框，否则不显示
     * @param cancelable
     *         提示框是否可以取消，默认可以取消
     */
    public void showProgress(String processMsg, boolean cancelable) {

        if (activity.isFinishing()) {
            return;
        }

        if (loadingDialogUtil == null) {
            loadingDialogUtil = new LoadingDialogUtil(activity);
        }

        if (TextUtils.isEmpty(processMsg)) {
            processMsg = activity.getString(R.string.process_handle_wait);
        }
        loadingDialogUtil.showDialog(processMsg, cancelable);
    }

    /**
     * 关闭等待对话框
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-23,下午3:10:40
     * <br> UpdateTime: 2016-11-23,下午3:10:40
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void dismissProgress() {
        if (activity.isFinishing()) {
            return;
        }

        if (loadingDialogUtil != null) {
            loadingDialogUtil.dismissDialog();
        }
    }

    /**
     * 显示提示信息
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/6/13 14:34
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/6/13 14:34
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param content
     *         提示内容
     */
    public void showToast(String content) {
        if (activity.isFinishing()) {
            return;
        }
        ToastUtil.showToast(activity, content);
    }

    /**
     * 结束当前Activity
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月22日,下午1:40:30
     * <br> UpdateTime: 2016年5月22日,下午1:40:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void finishActivity() {

        if (activity.isFinishing()) {
            return;
        }
        activity.finish();
    }

    /**
     * 切换语言
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月24日,下午2:52:16
     * <br> UpdateTime: 2016年9月24日,下午2:52:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void switchLanguage() {
        //        if (activity.isFinishing()) {
        //            return;
        //        }
        //        String language = SystemUtil.getAppLanguage();
        //        // 获得res资源对象
        //        Resources resources = activity.getResources();
        //        // 获得设置对象
        //        Configuration config = resources.getConfiguration();
        //
        //        if (language.startsWith("zh")) {
        //            config.locale = Locale.SIMPLIFIED_CHINESE;
        //        } else if (language.startsWith("en")) {
        //            config.locale = Locale.ENGLISH;
        //        } else {
        //            config.locale = new Locale(language);
        //        }
        //        // 应用内配置语言
        //        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        //        resources.updateConfiguration(config, dm);
    }

}