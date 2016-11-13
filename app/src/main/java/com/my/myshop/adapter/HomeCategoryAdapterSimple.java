package com.my.myshop.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
public class HomeCategoryAdapterSimple extends BaseAdapter<HomeCampaign, HomeCategoryAdapterSimple.MyViewHolder>{
    private static int VIEW_TYPE1 = 0;  //大图在左的布局
    private static int VIEW_TYPE2 = 1;  //大图在右的布局
    List<HomeCampaign> homeCampaigns;

    public HomeCategoryAdapterSimple(Context context, List<HomeCampaign> data) {
        super(context, data, R.layout.item_home_cardview1);
        homeCampaigns = data;
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


    class MyViewHolder extends BaseViewHolder{

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }

        /**
         * 执行点击翻转的动画与onClick方法
         * @param v 执行动画的控件
         */
        private void anim(final View v){
            ObjectAnimator animator =  ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                    .setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    int position = getLayoutPosition();
                    switch (v.getId()){
                        case R.id.image_view_big:
                            onItemClickListener.onClick(v, position);
                            break;
                        case R.id.img_small_top:
                            onItemClickListener.onClick(v, position);
                            break;
                        case R.id.img_small_bottom:
                            onItemClickListener.onClick(v, position);
                            break;
                    }
                }
            });
            animator.start();
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null)
                anim(v);
            super.onClick(v);
        }
    }

}
