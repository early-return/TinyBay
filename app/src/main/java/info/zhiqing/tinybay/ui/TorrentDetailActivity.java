package info.zhiqing.tinybay.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.TorrentDetail;
import info.zhiqing.tinybay.spider.SpiderClient;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TorrentDetailActivity extends AppCompatActivity {
    public static final String TAG = "TorrentDetailActivity";

    public static final String EXTRA_TITLE = "info.zhiqing.tinybay.TorrentDetailActivity.EXTRA_TITLE";
    public static final String EXTRA_CODE = "info.zhiqing.tinybay.TOrrentDetailActivity.EXTRA_CODE";

    private String title;
    private String code;

    private View loadingView;
    private View loadFailedView;
    private View contentView;

    private ImageView loadingImageView;

    private TextView infoTitleTextView;
    private TextView infoTextView;
    private TextView linkTextView;
    private TextView introTextView;
    private Button copyButton;
    private Button openButton;


    private int loadingAnimIndex = 0;
    private int[] loadingAnimRes = {
            R.drawable.ic_loading_anim_01,
            R.drawable.ic_loading_anim_02,
            R.drawable.ic_loading_anim_03
    };


    public static void actionStart(Context context, String title, String code) {
        Intent intent = new Intent(context, TorrentDetailActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_CODE, code);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torrent_detail);

        initField();

        initView();

    }

    private void initField() {
        Intent intent = getIntent();
        title = intent.getStringExtra(EXTRA_TITLE);
        code = intent.getStringExtra(EXTRA_CODE);
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(title);
        }

        loadingView = findViewById(R.id.detail_loading);
        loadingImageView = findViewById(R.id.loading_image);
        loadFailedView = findViewById(R.id.detail_load_failed);
        contentView = findViewById(R.id.detail_content);

        infoTitleTextView = findViewById(R.id.detail_info_title);
        infoTextView = findViewById(R.id.detail_info_text);
        linkTextView = findViewById(R.id.detail_link_text);
        introTextView = findViewById(R.id.detail_intro_text);
        copyButton = findViewById(R.id.detail_button_copy);
        openButton = findViewById(R.id.detail_button_open);


        Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loadingImageView.setImageResource(loadingAnimRes[loadingAnimIndex]);
                        loadingAnimIndex++;
                        if (loadingAnimIndex == loadingAnimRes.length) {
                            loadingAnimIndex = 0;
                        }
                    }
                });

        SpiderClient.getInstance().fetchTorrentDetail(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TorrentDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final TorrentDetail torrentDetail) {
                        if (torrentDetail == null) {
                            onError(new Exception());
                            return;
                        }
                        infoTitleTextView.setText(title);
                        String infoText = "";
                        for (Map.Entry<String, String> item : torrentDetail.getInfo().entrySet()) {
                            infoText += TextUtils.concat(item.getKey(), ": ", item.getValue(), "\n");
                        }
                        infoTextView.setText(infoText);
                        linkTextView.setText(torrentDetail.getLink());
                        introTextView.setText(torrentDetail.getIntro());

                        copyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                cm.setText(torrentDetail.getLink());
                                Snackbar.make(contentView, R.string.tips_copy_to_clipboard, Snackbar.LENGTH_SHORT).show();
                            }
                        });

                        openButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(torrentDetail.getLink()));
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                } else {
                                    Snackbar.make(contentView, R.string.tips_no_app_to_open, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.setVisibility(View.GONE);
                        loadFailedView.setVisibility(View.VISIBLE);
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loadingView.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
