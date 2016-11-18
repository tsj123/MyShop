package com.my.myshop.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.my.myshop.Constant;
import com.my.myshop.R;
import com.my.myshop.adapter.BaseAdapter;
import com.my.myshop.adapter.BaseViewHolder;
import com.my.myshop.adapter.SimpleAdapter;
import com.my.myshop.bean.HomeSlider;
import com.my.myshop.bean.Category;
import com.my.myshop.bean.Page;
import com.my.myshop.bean.Ware;
import com.my.myshop.http.OkHttpHelper;
import com.my.myshop.http.SimpleCallback;
import com.squareup.okhttp.Response;

import java.util.List;

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

    private CategoryNamesAdapter categoryNamesAdapter;
    private CategoryWaresAdapter categoryWaresAdapter;

//    private CategoryAdapter mCategoryAdapter;
//    private WaresAdapter mWaresAdatper;

    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();
    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private long category_id = 1;

    private  static final int STATE_INIT = 0;
    private  static final int STATE_REFRESH = 1;
    private  static final int STATE_MORE = 2;
    private  static final int STATE_CLICK = 3;

    private int state = STATE_INIT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    public void init() {
        initTopSlider();
        initLeftList();
        LoadRightWares(1);
        initRefreshLayout();
    }

    private void initTopSlider() {
        String url = Constant.API.BANNER + "?type=1";
        mHttpHelper.get(url, new SimpleCallback<List<HomeSlider>>(getActivity()){
            @Override
            public void onSuccess(Response response, List<HomeSlider> banners) {
                slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                slider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
                if (banners != null){
                    for (HomeSlider banner : banners){
                        TextSliderView textSliderView = new TextSliderView(getActivity());
                        textSliderView.image(banner.getImgUrl());
                        textSliderView.description(banner.getName());
                        slider.addSlider(textSliderView);
                    }
                }
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void initLeftList() {
        mHttpHelper.get(Constant.API.CATEGORY_LIST, new SimpleCallback<List<Category>>(getActivity()) {
            @Override
            public void onSuccess(Response response, final List<Category> categories) {
                categoryNamesAdapter = new CategoryNamesAdapter(getActivity(), categories);
                categoryNamesAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //ToastUtil.show(getActivity(), categories.get(position).getName());
                        state = STATE_REFRESH;
                        category_id =categories.get(position).getId();
                        LoadRightWares(category_id);
                    }
                });
                recyclerviewCategory.setAdapter(categoryNamesAdapter);
                recyclerviewCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerviewCategory.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void LoadRightWares(long categoryId) {
        String url = Constant.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize;
        mHttpHelper.get(url, new SimpleCallback<Page<Ware>>(getActivity()) {
            @Override
            public void onSuccess(Response response, Page<Ware> warePage) {
                switch (state){
                    case STATE_INIT:
                        categoryWaresAdapter = new CategoryWaresAdapter(getActivity(), warePage.getList());
                        recyclerviewWares.setAdapter(categoryWaresAdapter);
                        recyclerviewWares.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerviewWares.setItemAnimator(new DefaultItemAnimator());
                        break;
                    case STATE_REFRESH:
                        currPage = 1;
                        categoryWaresAdapter.refreshData(warePage.getList());
                        break;
                    case STATE_MORE:
                        //categoryWaresAdapter.addData(categoryWaresAdapter.getData().size(), warePage.getList());
                        if(warePage.getList().size() == 0) {
                            Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT).show();
                        }
                        categoryWaresAdapter.loadMoreData(warePage.getList());
                        recyclerviewWares.scrollToPosition(categoryWaresAdapter.getData().size());
                        refreshLayout.finishRefreshLoadMore();
                        break;
                    case STATE_CLICK:
                        break;
                }

            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state = STATE_REFRESH;
                LoadRightWares(category_id);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                state = STATE_MORE;
                currPage++;
                LoadRightWares(category_id);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class CategoryNamesAdapter extends SimpleAdapter<Category>{

        public CategoryNamesAdapter(Context context, List<Category> data) {
            super(context, data, R.layout.item_text);
        }

        @Override
        public void setViews(BaseViewHolder holder, Category category) {
            holder.getTextView(R.id.text).setText(category.getName());
        }
    }

    class CategoryWaresAdapter extends SimpleAdapter<Ware>{

        public CategoryWaresAdapter(Context context, List<Ware> data) {
            super(context, data, R.layout.item_grid_wares);
        }

        @Override
        public void setViews(BaseViewHolder holder, Ware ware) {
            SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
            Uri uri = Uri.parse(ware.getImgUrl());
            draweeView.setImageURI(uri);
            holder.getTextView(R.id.text_title).setText(ware.getName());
            holder.getTextView(R.id.text_price).setText("¥"+ware.getPrice());
        }
    }
}
