
package com.MinimalSoft.BrujulaUniversitaria.Main;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.AppBarLayout;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Profile;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.NewsFeed;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Articles;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Categories;

public class SectionsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener, AppBarLayout.OnOffsetChangedListener {

    private AppCompatActivity appCompatActivity;
    private Categories categoriesFragment;
    private StringBuilder sbTittle;
    private Resources resources;

    private short pageSelected;
    private int toolbarOffset;

    public SectionsPagerAdapter(final AppCompatActivity appCompatActivity, final ViewPager pagerView) {
        super(appCompatActivity.getSupportFragmentManager());
        this.appCompatActivity = appCompatActivity;

        categoriesFragment = new Categories();
        sbTittle = new StringBuilder();
        toolbarOffset = 0;

        resources = appCompatActivity.getApplicationContext().getResources();
        appCompatActivity.setTitle(resources.getString(R.string.title_page_0));
    }

    /* FragmentPagerAdapter implemented Methods */

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                this.getItem(2);
                this.getItem(3);
                return NewsFeed.newInstance();
            case 1:
                return Articles.newInstance();
            case 2:
                return categoriesFragment;
            case 3:
                return Profile.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return resources.getInteger(R.integer.page_count);
    }

    /* OnPageChangeListener implemented Methods */

    @Override
    public void onPageSelected(int position) {
        sbTittle.delete(0, sbTittle.length());
        pageSelected = (short) position;

        switch (position) {
            case 0:
                sbTittle.append(resources.getString(R.string.title_page_0));
                break;

            case 1:
                sbTittle.append(resources.getString(R.string.title_page_1));
                break;

            case 2:
                sbTittle.append(resources.getString(R.string.title_page_2));
                categoriesFragment.modifyVerticalPosition(toolbarOffset);
                break;

            case 3:
                sbTittle.append(resources.getString(R.string.title_page_3));
                break;
        }

        appCompatActivity.setTitle(sbTittle.toString());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /* OnOffsetChangedListener implemented Methods */

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        toolbarOffset = verticalOffset;

        if (pageSelected == 2) {
            categoriesFragment.modifyVerticalPosition(verticalOffset);
        }
    }
}
