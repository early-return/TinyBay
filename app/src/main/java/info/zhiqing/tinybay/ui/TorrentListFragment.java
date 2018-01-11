package info.zhiqing.tinybay.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.adapter.TorrentsAdapter;
import info.zhiqing.tinybay.entities.Torrent;
import info.zhiqing.tinybay.entities.TorrentDetail;
import info.zhiqing.tinybay.spider.SpiderClient;
import info.zhiqing.tinybay.view.LoadMoreView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TorrentListFragment extends Fragment {
    public static final String TAG = "TorrentListFragment";

    private static final String ARG_URL = "info.zhiqing.tinybay.ARG_URL";

    public static final int STATE_LOADING = 0;
    public static final int STATE_SHOWING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_FAILED = 3;

    private int loadingAnimIndex = 0;
    private int[] loadingAnimRes = {
            R.drawable.ic_loading_anim_01,
            R.drawable.ic_loading_anim_02,
            R.drawable.ic_loading_anim_03
    };

    private int state = 0;

    //视图
    private RecyclerView recyclerView;
    private View loadingView;
    private View loadingFailedView;
    private View torrentsEmptyView;
    private ImageView loadingImageView;
    private SwipeRefreshLayout swipeLayout;

    private TorrentsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<View> pages = new ArrayList<>();

    private int currentPage = 0;
    private String baseUrl = "https://thepiratebay.org/recent";
    private boolean loadedAll = false;

    public static TorrentListFragment newInstance(String url) {
        TorrentListFragment fragment = new TorrentListFragment();

        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        fragment.setArguments(args);

        return fragment;
    }


    public TorrentListFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        baseUrl = args.getString(ARG_URL);

        if (adapter == null) {
            adapter = new TorrentsAdapter(R.layout.torrents_item, new ArrayList<Torrent>());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_torrent_list, container, false);

        initView(v);

        switchPage();

        return v;
    }

    private void initView(View v) {
        loadingView = v.findViewById(R.id.torrents_loading);
        loadingFailedView = v.findViewById(R.id.torrents_load_failed);
        torrentsEmptyView = v.findViewById(R.id.torrents_empty);
        pages.add(loadingView);
        pages.add(loadingFailedView);
        pages.add(torrentsEmptyView);

        loadingImageView = v.findViewById(R.id.loading_image);

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

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = v.findViewById(R.id.torrent_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        pages.add(recyclerView);


        swipeLayout = v.findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }
        });


        adapter.openLoadAnimation();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Torrent torrent = (Torrent) adapter.getData().get(position);
                TorrentDetailActivity.actionStart(getContext(), torrent.getTitle(), torrent.getCode());
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(false);
            }
        }, recyclerView);
        adapter.setLoadMoreView(new LoadMoreView());

        if (currentPage == 0) {
            loadData(false);
        }
    }

    private void switchPage() {
        switch (state) {
            case STATE_LOADING:
                switchPage(loadingView);
                break;
            case STATE_SHOWING:
                switchPage(recyclerView);
                break;
            case STATE_EMPTY:
                switchPage(torrentsEmptyView);
                break;
            case STATE_FAILED:
                switchPage(loadingFailedView);
                break;
        }
    }

    private void switchPage(View v) {
        for (int i = 0; i < pages.size(); i++) {
            if (v != pages.get(i)) {
                pages.get(i).setVisibility(View.INVISIBLE);
            }
        }
        v.setVisibility(View.VISIBLE);
    }


    public void loadData(final boolean refresh) {

        if (refresh) {
            loadedAll = false;
            currentPage = 0;
        }

        if (loadedAll) return;

        String url = baseUrl + "/" + currentPage + "";


        Observable<List<Torrent>> observable = SpiderClient.getInstance().fetchTorrentsByUrl(url);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Torrent>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Torrent> torrents) {
                        if (torrents.size() == 0) {
                            loadedAll = true;
                            adapter.loadMoreEnd();
                            if (currentPage == 0) {
                                state = STATE_EMPTY;
                                switchPage();
                            }
                            return;
                        }
                        if (refresh) {
                            recyclerView.smoothScrollToPosition(0);
                            adapter.setNewData(new ArrayList<Torrent>());
                        }
                        adapter.addData(torrents);
                        adapter.notifyDataSetChanged();
                        adapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (currentPage == 0) {
                            state = STATE_FAILED;
                            switchPage();
                        }
                        adapter.loadMoreFail();
                    }

                    @Override
                    public void onComplete() {
                        swipeLayout.setRefreshing(false);
                        if (state != STATE_EMPTY) {
                            state = STATE_SHOWING;
                            currentPage++;
                            switchPage();
                        }
                    }
                });
    }

}
