//package com.android.base.activity.mvp.test;
//
//import android.os.Bundle;
//
//import com.android.base.R;
//import com.android.base.activity.mvp.MvpBaseActivity;
//import com.android.base.activity.mvp.MvpBaseView;
//import com.android.base.configs.ConstantKey;
//
//public class MvpTestView1 extends MvpBaseView {
//
//    public MvpTestView1(MvpBaseActivity activity) {
//        super(activity);
//    }
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.view_banner;
//    }
//
//    @Override
//    public void findViews() {
//        //        findViewByIds(R.id.img_tag_id);
//    }
//
//    @Override
//    public void initGetData(Bundle bundle) {
//        bundle.getString(ConstantKey.INTENT_KEY_ACCOUNT, "");
//    }
//
//    @Override
//    public void init() {
//        //title.sette
//    }
//
//    @Override
//    public void widgetListener() {
//        //        title.setonclick
//    }
//
//    @Override
//    public void initViewData(Object object) {
//
//    }
//
//}
