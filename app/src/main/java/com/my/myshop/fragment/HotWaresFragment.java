package com.my.myshop.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.my.myshop.Constant;
import com.my.myshop.R;
import com.my.myshop.adapter.BaseAdapter;
import com.my.myshop.adapter.HotWaresAdapterSimple;
import com.my.myshop.bean.Page;
import com.my.myshop.bean.Ware;
import com.my.myshop.http.OkHttpHelper;
import com.my.myshop.util.Loader;

import java.util.List;

/**
 * Created by MY on 2016/9/15.
 */
public class HotWaresFragment extends Fragment implements Loader.OnPageListener<Ware>{
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private int currPage = 2;
    private int pageSize = 10;
    private HotWaresAdapterSimple hotWaresAdapter;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        refreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);
        initLoader();
        return view;
    }

    private void initLoader() {
        Loader loader = Loader.newBuilder()
                .setUrl(Constant.API.WARES_HOT)
                .setLoadMore(true)
                .setOnPageListener(this)
                .setPageSize(20)
                .setRefreshLayout(refreshLayout)
                .build(getActivity(), new TypeToken<Page<Ware>>() {}.getType());
        loader.request();
    }

    @Override
    public void load(final List<Ware> data, int totalPage, int totalCount) {
        hotWaresAdapter = new HotWaresAdapterSimple(getActivity(), data);
        hotWaresAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), data.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(hotWaresAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void refresh(List<Ware> data, int totalPage, int totalCount) {
        hotWaresAdapter.refreshData(data);
    }

    @Override
    public void loadMore(List<Ware> data, int totalPage, int totalCount) {
        hotWaresAdapter.loadMoreData(data);
    }
}
