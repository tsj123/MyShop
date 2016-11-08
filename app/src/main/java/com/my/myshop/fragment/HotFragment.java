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

import com.my.myshop.Constant;
import com.my.myshop.R;
import com.my.myshop.adapter.BaseAdapter;
import com.my.myshop.adapter.HotWaresAdapter;
import com.my.myshop.bean.Page;
import com.my.myshop.bean.Ware;
import com.my.myshop.http.OkHttpHelper;
import com.my.myshop.http.SpotsCallBack;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * Created by MY on 2016/9/15.
 */
public class HotFragment extends Fragment{
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private int currPage = 2;
    private int pageSize = 10;
    private HotWaresAdapter hotWaresAdapter;
    private RecyclerView recyclerView;
    private  List<Ware> wares;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        getData();
        return view;
    }

    private void getData(){
        String url = Constant.API.WARES_HOT + "?curPage=" + currPage + "&pageSize=" + pageSize;
        httpHelper.get(url, new SpotsCallBack<Page<Ware>>(getActivity()) {
            @Override
            public void onSuccess(Response response, Page<Ware> page) {
                wares = page.getList();
                currPage = page.getCurrentPage();
                pageSize = page.getPageSize();
                showData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showData(){
//        hotWaresAdapter = new HotWaresAdapterNotUsed(wares);
//        recyclerView.setAdapter(hotWaresAdapter);
        hotWaresAdapter = new HotWaresAdapter(getActivity(), wares);
        hotWaresAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Toast.makeText(getActivity(), wares.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(hotWaresAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}
