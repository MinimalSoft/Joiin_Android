package com.MinimalSoft.BU.Main;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.AppBarLayout;

import com.MinimalSoft.BU.R;
import com.MinimalSoft.BU.Tabs.Profile;
import com.MinimalSoft.BU.Tabs.NewsFeed;
import com.MinimalSoft.BU.Tabs.Articles;
import com.MinimalSoft.BU.Tabs.Categories;

public class SectionsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener, AppBarLayout.OnOffsetChangedListener {
    private AppCompatActivity appCompatActivity;
    private Categories categoriesFragment;
    private NewsFeed newsFeedFragment;
    private Articles articlesFragment;
    private Profile profileFragment;

    private Resources resources;

    public SectionsPagerAdapter(final AppCompatActivity appCompatActivity) {
        super(appCompatActivity.getSupportFragmentManager());
        this.appCompatActivity = appCompatActivity;

        categoriesFragment = new Categories();
        newsFeedFragment = new NewsFeed();
        articlesFragment = new Articles();
        profileFragment = new Profile();

        resources = appCompatActivity.getResources();
        appCompatActivity.setTitle(resources.getString(R.string.title_page_0));
    }

    /* FragmentPagerAdapter implemented Methods */

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //this.getItem(2);
                //this.getItem(3);
                return newsFeedFragment;
            case 1:
                //return Articles.newInstance();
                return articlesFragment;
            case 2:
                //return Categories.newInstance();
                return categoriesFragment;
            case 3:
                //return Profile.newInstance();
                return profileFragment;
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
        switch (position) {
            case 0:
                appCompatActivity.setTitle(resources.getString(R.string.title_page_0));
                break;

            case 1:
                appCompatActivity.setTitle(resources.getString(R.string.title_page_1));
                break;

            case 2:
                appCompatActivity.setTitle(resources.getString(R.string.title_page_2));
                //categoriesFragment.modifyVerticalPosition(toolbarOffset);
                break;

            case 3:
                appCompatActivity.setTitle(resources.getString(R.string.title_page_3));
                //profileFragment.reloadPictures();
                break;
        }
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
        /*toolbarOffset = verticalOffset;

        if (pageSelected == 2) {
            categoriesFragment.modifyVerticalPosition(toolbarOffset);
        }*/
    }
}