package com.android.base.activity.mvp.test;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.R;
import com.android.base.activity.mvp.MvpBaseView;

import java.util.ArrayList;

public class MvpTestView extends MvpBaseView<MvpTestActivity> {

    /** 标题栏 */
    private TextView item0;
    private View item1;
    private View item2;
    private View item3;
    private View item4;
    private View item5;
    private View item6;
    private View item7;
    private View item8;
    private View item9;
    private View item10;
    private View item11;
    private LinearLayout view_heard;

    public MvpTestView(MvpTestActivity activity) {
        super(activity);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void findViews() {
        item0 = findViewByIds(R.id.item0);
        item1 = findViewByIds(R.id.item1);
        item2 = findViewByIds(R.id.item2);
        item3 = findViewByIds(R.id.item3);
        item4 = findViewByIds(R.id.item4);
        item5 = findViewByIds(R.id.item5);
        item6 = findViewByIds(R.id.item6);
        item7 = findViewByIds(R.id.item7);
        item8 = findViewByIds(R.id.item8);
        item9 = findViewByIds(R.id.item9);
        item10 = findViewByIds(R.id.item10);
        item11 = findViewByIds(R.id.item11);
        view_heard = findViewByIds(R.id.view_heard);

    }

    @Override
    public void initGetData(Bundle bundle) {

    }

    @Override
    public void init() {
        activity.setTintResource(R.color.font_blue);
    }

    @Override
    public void widgetListener() {
        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.basePresenter.loadData();
                activity.test();
            }
        });
    }

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public void initViewData(Object object) {
        arrayList.add(String.valueOf(object));
        item0.setText(String.valueOf(arrayList.size()));
    }

    public String getTest() {
        return item0.getText().toString();
    }

}
