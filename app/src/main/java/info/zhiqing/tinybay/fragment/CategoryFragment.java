package info.zhiqing.tinybay.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.adapter.CategoryViewPagerAdapter;
import info.zhiqing.tinybay.entities.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;



    public static List<Category> categories = null;
    public static Map<String, List<Category>> subCategories = null;
    private FragmentStatePagerAdapter adapter = null;


    public CategoryFragment() {

    }

    private void init() {
        if (categories == null || subCategories == null) {
            initCategories();
        }
        adapter = new CategoryViewPagerAdapter(getFragmentManager(), categories);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        init();

        viewPager = v.findViewById(R.id.category_pager);
        tabLayout = v.findViewById(R.id.category_tab_layout);

        viewPager.setAdapter(adapter);

        return v;
    }


    //初始化分类信息
    private void initCategories() {
        Resources res = getResources();

        //初始化父分类
        categories = getCategoryList(
                res.getStringArray(R.array.category_list_parent_title),
                res.getStringArray(R.array.category_list_parent_code));

        //初始化视频子分类
        subCategories = new HashMap<>();
        subCategories.put(
                categories.get(0).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_video_title),
                        res.getStringArray(R.array.category_list_sub_video_code)));

        //初始化音频子分类
        subCategories = new HashMap<>();
        subCategories.put(
                categories.get(1).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_audio_title),
                        res.getStringArray(R.array.category_list_sub_audio_code)));

        //初始化应用子分类
        subCategories = new HashMap<>();
        subCategories.put(
                categories.get(2).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_audio_title),
                        res.getStringArray(R.array.category_list_sub_audio_code)));

        //初始化游戏子分类
        subCategories = new HashMap<>();
        subCategories.put(
                categories.get(3).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_audio_title),
                        res.getStringArray(R.array.category_list_sub_audio_code)));

        //初始化哲学子分类
        subCategories = new HashMap<>();
        subCategories.put(
                categories.get(4).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_porn_title),
                        res.getStringArray(R.array.category_list_sub_porn_code)));

        //初始化其他子分类
        subCategories = new HashMap<>();
        subCategories.put(
                categories.get(5).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_other_title),
                        res.getStringArray(R.array.category_list_sub_other_code)));

    }

    //通过标题数组和代码数组获取分类列表
    private List<Category> getCategoryList(String[] titleList, String[] codeList) {
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < titleList.length; i++) {
            list.add(new Category(titleList[i], codeList[i]));
        }
        return list;
    }



}
