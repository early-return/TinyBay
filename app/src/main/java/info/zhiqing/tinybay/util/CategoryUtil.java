package info.zhiqing.tinybay.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.Category;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhiqing on 18-1-5.
 */

public class CategoryUtil {

    public static List<Category> CATEGORIES = null;
    public static Map<String, List<Category>> SUB_CATEGORIES = null;

    private static Map<String, String> codeTitleMap = null;
    private static Map<String, Integer> codeColorMap = null;
    private static Map<String, Integer> codeIconMap = null;

    //返回给定分类编号对应的标题
    public static String codeToTitle(String code) {
        return codeTitleMap.get(code);
    }

    public static int codeToColor(String code) {
        return codeColorMap.get(code.substring(0, 1) + "00");
    }

    public static int codeToIconRes(String code) {
        if (codeIconMap.containsKey(code)) {
            return codeIconMap.get(code);
        } else {
            return codeIconMap.get(code.substring(0, 1) + "00");
        }
    }

    //初始化分类信息
    public static Observable<Void> initCategories(Context context) {
        if (CategoryUtil.CATEGORIES == null ||
                CategoryUtil.SUB_CATEGORIES == null ||
                CategoryUtil.codeTitleMap == null) {

            final Resources res = context.getResources();

            return Observable.create(new ObservableOnSubscribe<Void>() {
                @Override
                public void subscribe(ObservableEmitter<Void> e) throws Exception {
                    initParentCategories(res);
                    initSubCategories(res);
                    initCodeTitleMap();
                    initCodeColorMap(res);
                    initCodeIconMap();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return Observable.empty();
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

    private static void initCodeTitleMap() {
        codeTitleMap = new HashMap<>();

        for (Category category : CATEGORIES) {
            codeTitleMap.put(category.getCode(), category.getTitle());

            List<Category> list = SUB_CATEGORIES.get(category.getCode());

            for (Category cate : list) {
                codeTitleMap.put(cate.getCode(), cate.getTitle());
            }
        }
    }

    private static void initCodeColorMap(Resources res) {
        String[] codes = res.getStringArray(R.array.category_list_parent_code);
        int[] colors = res.getIntArray(R.array.category_list_parent_color);

        codeColorMap = new HashMap<>();

        for (int i = 0; i < codes.length; i++) {
            codeColorMap.put(codes[i], colors[i]);
            Log.d("CategoryUtil", codes[i] + ": " + colors[i]);
        }
    }

    private static void initCodeIconMap() {
        codeIconMap = new HashMap<>();

        codeIconMap.put("100", R.drawable.ic_music_note_white_24dp);
        codeIconMap.put("200", R.drawable.ic_video_library_white_24dp);
        codeIconMap.put("300", R.drawable.ic_apps_white_24dp);
        codeIconMap.put("400", R.drawable.ic_videogame_asset_white_24dp);
        codeIconMap.put("500", R.drawable.ic_favorite_white_24dp);
        codeIconMap.put("600", R.drawable.ic_get_app_white_24dp);
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
