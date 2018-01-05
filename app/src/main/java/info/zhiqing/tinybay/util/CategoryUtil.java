package info.zhiqing.tinybay.util;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.Category;

/**
 * Created by zhiqing on 18-1-5.
 */

public class CategoryUtil {

    public static List<Category> CATEGORIES = null;
    public static Map<String, List<Category>> SUB_CATEGORIES = null;

    private static Map<String, String> codeTitleMapper = null;

    //返回给定分类编号对应的标题
    public static String codeToTitle(String code) {
        return codeTitleMapper.get(code);
    }

    //初始化分类信息
    public static void initCategories(Context context) {
        if (CategoryUtil.CATEGORIES == null ||
                CategoryUtil.SUB_CATEGORIES == null ||
                CategoryUtil.codeTitleMapper == null) {

            Resources res = context.getResources();

            initParentCategories(res);
            initSubCategories(res);
            initMapper();

        }

    }


    //初始化父分类
    private static void initParentCategories(Resources res) {
        CATEGORIES = getCategoryList(
                res.getStringArray(R.array.category_list_parent_title),
                res.getStringArray(R.array.category_list_parent_code));
    }

    //初始化子分类
    private static void initSubCategories(Resources res) {
        //初始化视频子分类
        SUB_CATEGORIES = new HashMap<>();
        SUB_CATEGORIES.put(
                CATEGORIES.get(0).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_video_title),
                        res.getStringArray(R.array.category_list_sub_video_code)));

        //初始化音频子分类
        SUB_CATEGORIES.put(
                CATEGORIES.get(1).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_audio_title),
                        res.getStringArray(R.array.category_list_sub_audio_code)));

        //初始化应用子分类
        SUB_CATEGORIES.put(
                CATEGORIES.get(2).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_app_title),
                        res.getStringArray(R.array.category_list_sub_app_code)));

        //初始化游戏子分类
        SUB_CATEGORIES.put(
                CATEGORIES.get(3).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_game_title),
                        res.getStringArray(R.array.category_list_sub_game_code)));

        //初始化哲学子分类
        SUB_CATEGORIES.put(
                CATEGORIES.get(4).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_porn_title),
                        res.getStringArray(R.array.category_list_sub_porn_code)));

        //初始化其他子分类
        SUB_CATEGORIES.put(
                CATEGORIES.get(5).getCode(),
                getCategoryList(
                        res.getStringArray(R.array.category_list_sub_other_title),
                        res.getStringArray(R.array.category_list_sub_other_code)));
    }

    private static void initMapper() {
        codeTitleMapper = new HashMap<>();

        for (Category category : CATEGORIES) {
            codeTitleMapper.put(category.getCode(), category.getTitle());

            List<Category> list = SUB_CATEGORIES.get(category.getCode());

            for (Category cate : list) {
                codeTitleMapper.put(cate.getCode(), cate.getTitle());
            }
        }

    }


    //通过标题数组和代码数组获取分类列表
    private static List<Category> getCategoryList(String[] titleList, String[] codeList) {
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < titleList.length; i++) {
            list.add(new Category(titleList[i], codeList[i]));
        }
        return list;
    }



}
