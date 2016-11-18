package com.my.myshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.my.myshop.R;
import com.my.myshop.widget.MyToolBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by MY on 2016/9/16.
 */
public class CartFragment extends Fragment {
    @InjectView(R.id.toolbar)
    MyToolBar toolbar;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    @InjectView(R.id.txt_total)
    TextView txtTotal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        ButterKnife.inject(this, view);
        initToolBar();

        return view;
    }

    private void initToolBar() {
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.btn_order, R.id.btn_del, R.id.cb_check_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_order:
                break;
            case R.id.btn_del:
                break;
            case R.id.cb_check_all:
                break;
        }
    }
}
