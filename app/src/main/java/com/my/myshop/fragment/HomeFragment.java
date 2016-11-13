package com.my.myshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.my.myshop.Constant;
import com.my.myshop.R;
import com.my.myshop.adapter.BaseAdapter;
import com.my.myshop.adapter.HomeCategoryAdapter;
import com.my.myshop.adapter.HomeCategoryAdapterSimple;
import com.my.myshop.bean.Banner;
import com.my.myshop.bean.Campaign;
import com.my.myshop.bean.HomeCampaign;
import com.my.myshop.http.BaseCallback;
import com.my.myshop.http.OkHttpHelper;
import com.my.myshop.util.ToastUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * 主页
 * Created by MY on 2016/9/13.
 */
public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private RecyclerView recyclerView;
    private List<Banner> banners;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //获取图片并填充图片轮播控件sliderLayout
        requestImages();
        //设置促销板块
        initRecyclerView();
        return view;
    }

    /**
     * 获取网络图片
     */
    private void requestImages(){
        String url = Constant.API.BANNER + "?type=1";

        okHttpHelper.get(url, new BaseCallback<List<Banner>>() {
            @Override
            public void onRequestBefore(Request request) {
            }
            @Override
            public void onResponse(Response response) {
            }
            @Override
            public void onFailure(Request request, Exception e) {
            }
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                HomeFragment.this.banners = banners;
                initSlider();
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    /**
     *将获取到的图片填充到SliderLayout中
     */
    public void initSlider(){
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);

        if (banners != null){
            for (Banner banner : banners){
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                sliderLayout.addSlider(textSliderView);
            }
        }
    }

    /**
     *
     */
    private void initRecyclerView() {
        okHttpHelper.get(Constant.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onRequestBefore(Request request) {
            }
            @Override
            public void onResponse(Response response) {
            }
            @Override
            public void onFailure(Request request, Exception e) {
            }
            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initData(homeCampaigns);
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void initData(final List<HomeCampaign> homeCampaigns){
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(homeCampaigns, this.getActivity());
        adapter.setOnCampaignClickListener(new HomeCategoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(getActivity(), campaign.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

//todo 封装HomeCategoryAdapterSimple
//        HomeCategoryAdapterSimple adapter = new HomeCategoryAdapterSimple(this.getActivity(), homeCampaigns);
//        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                ToastUtil.show(getActivity(), homeCampaigns.get(position).getTitle());
//            }
//        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }


}
