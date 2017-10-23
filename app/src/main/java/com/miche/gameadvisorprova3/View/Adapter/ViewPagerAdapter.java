package com.miche.gameadvisorprova3.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miche on 08/10/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String > titleList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {super(fm); }

    @Override
    public Fragment getItem(int position) { return fragmentList.get(position); }

    @Override
    public int getCount() {return fragmentList.size();  }

    @Override
    public int getItemPosition(Object object) { return super.getItemPosition(object); }

    @Override
    public CharSequence getPageTitle(int position) {return titleList.get(position); }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }

}
