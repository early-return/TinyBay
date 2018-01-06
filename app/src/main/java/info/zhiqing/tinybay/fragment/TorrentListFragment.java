package info.zhiqing.tinybay.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.adapter.TorrentListAdapter;
import info.zhiqing.tinybay.entities.Torrent;
import info.zhiqing.tinybay.spider.SpiderClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TorrentListFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private TorrentListAdapter adapter;
    private SpiderClient client;

    private String baseUrl = "https://thepiratebay.org/recent";
    private boolean multiPages = false;
    private boolean loadedAll = false;
    private int currentPage = 0;


    public TorrentListFragment() {
        // Required empty public constructor

    }

    private void initData() {

        List<Torrent> list = new ArrayList<>();

        Torrent torrent1 = new Torrent();
        torrent1.setTitle("Hello World!");
        torrent1.setSize("123M");
        torrent1.setSeeders(123);
        torrent1.setLeechers(234);
        torrent1.setTypeCode("200");
        list.add(torrent1);

        Torrent torrent2 = new Torrent();
        torrent2.setTitle("Hahahaha!");
        torrent2.setSize("13M");
        torrent2.setSeeders(13);
        torrent2.setLeechers(34);
        torrent2.setTypeCode("100");
        list.add(torrent2);

        Torrent torrent3 = new Torrent();
        torrent3.setTitle("Wow world!");
        torrent3.setSize("12M");
        torrent3.setSeeders(12);
        torrent3.setLeechers(23);
        torrent3.setTypeCode("500");
        list.add(torrent3);

        Torrent torrent4 = new Torrent();
        torrent4.setTitle("Aha!");
        torrent4.setSize("23M");
        torrent4.setSeeders(23);
        torrent4.setLeechers(34);
        torrent4.setTypeCode("400");
        list.add(torrent4);

        adapter.addData(list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TorrentListAdapter(getActivity());
        client = new SpiderClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_torrent_list, container, false);

        init(v);
        //initData();
        loadData();

        return v;
    }

    private void init(View v) {

        recyclerView = v.findViewById(R.id.torrent_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        swipeLayout = v.findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );
    }

    private void loadData() {

        if (loadedAll) return;

        String url = multiPages ? baseUrl + "/" + currentPage : baseUrl;

        client.fetchTorrentsByUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Torrent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(getContext(), "Start load!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Torrent torrent) {
                        adapter.addData(torrent);
                        Log.d("TorrentListFragment", torrent.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), "Load completed!", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
