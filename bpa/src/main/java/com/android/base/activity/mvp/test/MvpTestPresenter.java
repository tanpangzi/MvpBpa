package com.android.base.activity.mvp.test;

import com.android.base.activity.mvp.MvpBasePresenter;
import com.android.base.utils.LogUtil;

import java.util.List;

public class MvpTestPresenter extends MvpBasePresenter<MvpTestView> {

    private MvpTestMode model;

    public MvpTestPresenter(MvpTestView view) {
        super(view);
        model = new MvpTestMode();
    }

    @Override
    public void loadData() {
        if (view == null) {
            LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
            return;
        }

        String str = view.getTest();
        view.showProgress();
        model.loadUserInfo(

                str, new MvpTestMode.OnRequestListener() {
            @Override
            public void onSuccess(final List<String> data) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (view == null) {
                            LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                            return;
                        }
                        view.dismissProgress();
                        view.initViewData(data);
                        view.showToast("操作成功");
                    }
                });
            }

            @Override
            public void onFailed() {
                if (view == null) {
                    LogUtil.e(getClass() + " 页面已经销毁，不在进行任何操作");
                    return;
                }
                view.showToast("操作失败");
            }
        });
    }
    //    @Override
    //    public Class<MvpTestView> getViewClass() {
    //        return getViewClass(getClass());
    //    }
}
