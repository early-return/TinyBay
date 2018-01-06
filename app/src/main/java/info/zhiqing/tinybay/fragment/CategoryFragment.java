package info.zhiqing.tinybay.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.adapter.CategoryViewPagerAdapter;
import info.zhiqing.tinybay.entities.Category;
import info.zhiqing.tinybay.util.CategoryUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private ViewPager viewPager;

    private FragmentStatePagerAdapter adapter = null;


    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {


        return new CategoryFragment();
    }

    private void init() {
        adapter = new CategoryViewPagerAdapter(getFragmentManager(), CategoryUtil.CATEGORIES);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        viewPager = v.findViewById(R.id.category_pager);

        viewPager.setAdapter(adapter);

        return v;
    }





}
