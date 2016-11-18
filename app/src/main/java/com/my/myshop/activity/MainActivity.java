package com.my.myshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.my.myshop.R;
import com.my.myshop.bean.Tab;
import com.my.myshop.fragment.CartFragment;
import com.my.myshop.fragment.CategoryFragment;
import com.my.myshop.fragment.HomeFragment;
import com.my.myshop.fragment.HotWaresFragment;
import com.my.myshop.fragment.MineFragment;
import com.my.myshop.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private FragmentTabHost tabHost;
    private List<Tab> tabs = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class, R.string.home, R.drawable.selector_icon_home);
        Tab tab_hot = new Tab(HotWaresFragment.class, R.string.hot, R.drawable.selector_icon_hot);
        Tab tab_category = new Tab(CategoryFragment.class, R.string.category, R.drawable.selector_icon_category);
        Tab tab_cart = new Tab(CartFragment.class, R.string.cart, R.drawable.selector_icon_cart);
        Tab tab_mine = new Tab(MineFragment.class, R.string.mine, R.drawable.selector_icon_mine);

        tabs.add(tab_home);
        tabs.add(tab_hot);
        tabs.add(tab_category);
        tabs.add(tab_cart);
        tabs.add(tab_mine);

        inflater = LayoutInflater.from(this);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //if(tabHost == null) System.out.println("tabHost null");
        tabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        for (Tab tab : tabs){
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            tabHost.addTab(tabSpec,tab.getFragment(), null);
        }

        tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        tabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab){

        View view = inflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return  view;
    }


}
