package com.my.myshop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.my.myshop.R;
import com.my.myshop.bean.Ware;

import java.util.List;

/**
 * Created by MY on 2016/9/16.
 */
public class HotWaresAdapterSimple extends SimpleAdapter<Ware>{
    public HotWaresAdapterSimple(Context context, List<Ware> data) {
        super(context, data, R.layout.item_hot_wares);
    }

    @Override
    public void setViews(BaseViewHolder holder, Ware ware) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        Uri uri = Uri.parse(ware.getImgUrl());

        holder.getTextView(R.id.text_title).setText(ware.getName());
        holder.getTextView(R.id.text_price).setText("￥" + ware.getPrice());
        simpleDraweeView.setImageURI(uri);
    }
}
