package com.my.myshop.adapter;

import android.content.Context;

import com.my.myshop.R;
import com.my.myshop.bean.Category;

import java.util.List;


/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class CategoryAdapter extends SimpleAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, datas, R.layout.item_category_single_text);
    }

    @Override
    public void setViews(BaseViewHolder viewHolder, Category item) {
        viewHolder.getTextView(R.id.textView).setText(item.getName());
    }
}
