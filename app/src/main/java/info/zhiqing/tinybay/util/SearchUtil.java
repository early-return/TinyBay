package info.zhiqing.tinybay.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.SearchHistory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lizhi on 2018/1/20.
 */

public class SearchUtil {
    public static final String TAG = "SearchUtil";

    public static String KEY_HISTORY = "search_history";
    public static final int HISTORY_LENGTH = 10;

    public static List<SearchHistory> histories;
    private static List<String> titles;

    public static void init(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String string = pref.getString(KEY_HISTORY, "");

        Log.d(TAG, "init: " + string);

        titles = new ArrayList<>();
        histories = new ArrayList<>();
        Scanner scanner = new Scanner(string);
        if (scanner.hasNextLine()) {
            String title = scanner.nextLine();
            titles.add(title);
        }
        updateHistories();

        Log.d(TAG, "init: histories size:" + histories.size());
    }

    private static void updateHistories() {
        histories.clear();

        for (String title : titles) {
            SearchHistory history = new SearchHistory();
            history.setTitle(title);
            histories.add(history);
        }
    }

    public static void addToHistory(final Context context, final String title) {

        Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                if (titles.contains(title)) {
                    Log.d(TAG, "subscribe: first branch");
                    titles.remove(titles.indexOf(title));
                }
                if (titles.size() >= HISTORY_LENGTH) {
                    Log.d(TAG, "subscribe: second branch");
                    titles.remove(titles.size() - 1);
                }
                titles.add(0, title);
                updateHistories();

                Log.d(TAG, "subscribe: titles size: " + titles.size());

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                String historiesPref = "";
                for (String title : titles) {
                    historiesPref = historiesPref + title + "\n";
                }
                pref.edit().putString(KEY_HISTORY, historiesPref)
                        .apply();
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }


    public static void swapHistory(FloatingSearchView view) {
        view.swapSuggestions(histories);

        view.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageResource(R.drawable.ic_history_black_24dp);
                SearchHistory history = (SearchHistory) item;
                textView.setText(history.getTitle());
            }
        });
    }
}
