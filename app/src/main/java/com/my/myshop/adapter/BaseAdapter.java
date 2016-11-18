package com.my.myshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Iterator;
import java.util.List;

/**
 * Created by MY on 2016/9/16.
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> data;
    protected LayoutInflater layoutInflater;
    protected Context context;
    protected int layoutResId;
    protected OnItemClickListener onItemClickListener;

    public BaseAdapter(Context context, List<T> data, int layoutResId){
        this.context = context;
        this.data = data;
        this.layoutResId = layoutResId;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(layoutResId, null, false);
        return new BaseViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItem(position);
        setViews(holder, t);
    }

    public abstract void setViews(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        if(data!=null && data.size()>0)
            return data.size();
        return 0;
    }

    public T getItem(int position){
        return data.get(position);
    }

    public List<T> getData(){
        return data;
    }

    public void addData(List<T> data){
        addData(0, data);
    }

    public void addData(int position, List<T> data){
        if(data!=null && data.size()>0) {
            this.data.addAll(data);
            notifyItemRangeChanged(position, data.size());
        }
    }

    public void clearData(){
        data.clear();
        notifyItemRangeRemoved(0, data.size());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }

    public void refreshData(List<T> list){
        clear();
        if(list !=null && list.size()>0){
            int size = list.size();
            for (int i=0;i<size;i++){
                data.add(i,list.get(i));
                notifyItemInserted(i);
            }
        }
    }

    public void loadMoreData(List<T> list){
        if(list!=null && list.size()>0){
            int size = list.size();
            int begin = data.size();
            for (int i=0; i<size; i++){
                data.add(list.get(i));
                notifyItemInserted(i + begin);
            }
        }
    }

    public void clear(){
//        int itemCount = datas.size();
//        datas.clear();
//        this.notifyItemRangeRemoved(0,itemCount);
        if(data==null || data.size()<=0)
            return;
        for (Iterator it = data.iterator(); it.hasNext();){
            T t = (T) it.next();
            int position = data.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }
}
