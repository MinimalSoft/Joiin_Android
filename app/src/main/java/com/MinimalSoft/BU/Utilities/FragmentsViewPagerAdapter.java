package com.MinimalSoft.BU.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentsViewPagerAdapter extends FragmentPagerAdapter {
    private boolean titled;
    private List<String> titleList;
    private List<Fragment> fragmentList;

    public FragmentsViewPagerAdapter(FragmentManager manager, boolean titled) {
        super(manager);
        this.titled = titled;
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titled ? titleList.get(position) : super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}