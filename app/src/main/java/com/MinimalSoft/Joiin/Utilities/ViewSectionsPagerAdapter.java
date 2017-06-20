package com.MinimalSoft.Joiin.Utilities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewSectionsPagerAdapter extends FragmentPagerAdapter {
    private boolean titled;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    /**
     * A customized ViewPagerAdapter using fragments.
     *
     * @param manager The FragmentManager for interacting with fragments associated with current activity.
     * @param titled  Determines whether the pages should be titled or not.
     */
    public ViewSectionsPagerAdapter(@NonNull FragmentManager manager, boolean titled) {
        super(manager);
        this.titled = titled;
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