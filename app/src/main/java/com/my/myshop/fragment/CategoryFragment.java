package com.my.myshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.SliderLayout;
import com.my.myshop.R;
import com.my.myshop.adapter.CategoryAdapter;
import com.my.myshop.http.OkHttpHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by MY on 2016/9/16.
 */
public class CategoryFragment extends Fragment {
    @InjectView(R.id.recyclerview_category)
    RecyclerView recyclerviewCategory;
    @InjectView(R.id.slider)
    SliderLayout slider;
    @InjectView(R.id.recyclerview_wares)
    RecyclerView recyclerviewWares;
    @InjectView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private CategoryAdapter mCategoryAdapter;
//    private WaresAdapter mWaresAdatper;

    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();

    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private long category_id=0;

    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;

    private int state = STATE_NORMAL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    public void init() {
        requestCategoryData();
//        requestBannerData();
//        initRefreshLayout();
    }

    private void requestCategoryData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
