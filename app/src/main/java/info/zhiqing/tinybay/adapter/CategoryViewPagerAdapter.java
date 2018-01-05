package info.zhiqing.tinybay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import info.zhiqing.tinybay.entities.Category;
import info.zhiqing.tinybay.fragment.CategoryFragment;
import info.zhiqing.tinybay.fragment.SubCategoryFragment;

/**
 * Created by zhiqing on 18-1-5.
 */

public class CategoryViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Category> categories;
    private List<Fragment> fragments;

    public CategoryViewPagerAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        this.categories = categories;
        fragments = new ArrayList<>();
        for (Category cate : categories) {
            fragments.add(SubCategoryFragment.newInstance(cate));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return categories.size();
    }
}
