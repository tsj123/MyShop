package com.my.myshop.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.my.myshop.R;
import com.my.myshop.bean.Ware;

import java.util.List;

/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class HotWaresAdapterNotUsed extends RecyclerView.Adapter<HotWaresAdapterNotUsed.ViewHolder>  {

    private List<Ware> wares;

    private LayoutInflater mInflater;

    public HotWaresAdapterNotUsed(List<Ware> wares){
        this.wares = wares;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_hot_wares, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ware ware = getData(position);
        holder.draweeView.setImageURI(Uri.parse(ware.getImgUrl()));
        holder.textTitle.setText(ware.getName());
        holder.textPrice.setText("￥" + ware.getPrice());
    }

    public Ware getData(int position){
        return wares.get(position);
    }

    public List<Ware> getData(){
        return wares;
    }

    public void clearData(){
        wares.clear();
        notifyItemRangeRemoved(0, wares.size());
    }

    public void addData(List<Ware> data){
        addData(0, data);
    }

    public void addData(int position, List<Ware> data){
        if(data!=null && data.size()>0) {
            wares.addAll(data);
            notifyItemRangeChanged(position, wares.size());
        }
    }

    @Override
    public int getItemCount() {
        if(wares!=null && wares.size()>0)
            return wares.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView;
        TextView textTitle;
        TextView textPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textPrice = (TextView) itemView.findViewById(R.id.text_price);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
        }
    }
}
