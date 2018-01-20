package info.zhiqing.tinybay.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.SearchHistory;
import info.zhiqing.tinybay.util.ConfigUtil;
import info.zhiqing.tinybay.util.SearchUtil;

public class SearchActivity extends AppCompatActivity implements FloatingSearchView.OnSearchListener {
    public static final String TAG = "SearchActivity";

    public static final String EXTRA_URL = "info.zhiqing.tinybay.SearchActivity.EXTRA_URL";
    public static final String EXTRA_TITLE = "info.zhiqing.tinybay.SearchActivity.EXTRA_TITLE";


    private FloatingSearchView floatingSearchView;
    private String title = "";
    private String baseUrl = ConfigUtil.BASE_URL + "/search/hello";
    private String searchUrl;

    public static void actionStart(Context context, String url, String title) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        initView();

    }

    private void init() {
        baseUrl = getIntent().getStringExtra(EXTRA_URL);
        title = getIntent().getStringExtra(EXTRA_TITLE);

        searchUrl = ConfigUtil.BASE_URL + "/search/";
    }

    private void initView() {
        setContentView(R.layout.activity_search);

        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search);
        floatingSearchView.setSearchText(title);
        floatingSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                onBackPressed();
            }
        });
        SearchUtil.swapHistory(floatingSearchView);

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                SearchHistory history = (SearchHistory) searchSuggestion;
                SearchUtil.addToHistory(SearchActivity.this, history.getTitle());

            }

            @Override
            public void onSearchAction(String currentQuery) {
                SearchUtil.addToHistory(SearchActivity.this, currentQuery);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.torrents_content, TorrentListFragment.newInstance(searchUrl + currentQuery))
                        .commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingSearchView.setSearchFocused(true);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.torrents_content, TorrentListFragment.newInstance(baseUrl))
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {

    }
}
