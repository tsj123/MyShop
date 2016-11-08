package com.my.myshop.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.myshop.R;
import com.my.myshop.bean.Campaign;
import com.my.myshop.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 主页中活动列表的适配器
 * Created by Ivan on 15/9/30.
 */
public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private static int VIEW_TYPE1 = 0;  //大图在左的布局
    private static int VIEW_TYPE2 = 1;  //大图在右的布局

    private LayoutInflater mInflater;
    private List<HomeCampaign> homeCampaigns;
    private Context mContext;
    private OnCampaignClickListener mListener;

    public HomeCategoryAdapter(List<HomeCampaign> data, Context context){
        homeCampaigns = data;
        this.mContext = context;
    }

    public void setOnCampaignClickListener(OnCampaignClickListener listener){
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        mInflater = LayoutInflater.from(viewGroup.getContext());
        if(type == VIEW_TYPE2)
            return new ViewHolder(mInflater.inflate(R.layout.item_home_cardview2, viewGroup,false));
        else
            return new ViewHolder(mInflater.inflate(R.layout.item_home_cardview1, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        HomeCampaign homeCampaign = homeCampaigns.get(i);
        viewHolder.textTitle.setText(homeCampaign.getTitle());
        //对图片进行缓存
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(viewHolder.imageViewBig);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(viewHolder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(viewHolder.imageViewSmallBottom);
    }

    @Override
    public int getItemCount() {
        if(homeCampaigns==null || homeCampaigns.size()<=0)
            return 0;
        return homeCampaigns.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0)
            return VIEW_TYPE2;
        else
            return VIEW_TYPE1;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.image_view_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.img_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.img_small_bottom);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                anim(v);
            }
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
                      HomeCampaign campaign = homeCampaigns.get(getLayoutPosition());
                      switch (v.getId()){
                          case R.id.image_view_big:
                              mListener.onClick(v, campaign.getCpOne());
                              break;
                          case R.id.img_small_top:
                              mListener.onClick(v, campaign.getCpTwo());
                              break;
                          case R.id.img_small_bottom:
                              mListener.onClick(v, campaign.getCpThree());
                              break;
                      }
                  }
              });
              animator.start();
        }
    }

    //HomeFragment调用此接口并重写onClick方法
    public interface OnCampaignClickListener{
        void onClick(View view, Campaign campaign);
    }

}
