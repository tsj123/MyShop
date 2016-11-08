package com.my.myshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.myshop.R;
import com.my.myshop.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MY on 2016/10/2.
 */
public class HCAdapter extends SimpleAdapter<HomeCampaign>{
    private static int VIEW_TYPE1 = 0;  //大图在左的布局
    private static int VIEW_TYPE2 = 1;  //大图在右的布局

    public HCAdapter(Context context, List<HomeCampaign> data) {
        super(context, data, R.layout.item_home_cardview1);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE2)
            view = layoutInflater.inflate(R.layout.item_home_cardview2, null, false);
        else
            view = layoutInflater.inflate(R.layout.item_home_cardview1, null, false);
        return new BaseViewHolder(view, onItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0)
            return VIEW_TYPE2;
        else
            return VIEW_TYPE1;
    }

    @Override
    public void setViews(BaseViewHolder holder, HomeCampaign homeCampaign) {
        holder.getTextView(R.id.text_title).setText(homeCampaign.getTitle());
        Picasso.with(context).load(homeCampaign.getCpOne().getImgUrl()).into(holder.getImageView(R.id.image_view_big));
        Picasso.with(context).load(homeCampaign.getCpTwo().getImgUrl()).into(holder.getImageView(R.id.img_small_top));
        Picasso.with(context).load(homeCampaign.getCpThree().getImgUrl()).into(holder.getImageView(R.id.img_small_bottom));
    }
}
