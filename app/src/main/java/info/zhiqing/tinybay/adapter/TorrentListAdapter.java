package info.zhiqing.tinybay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.Torrent;
import info.zhiqing.tinybay.spider.Spider;
import info.zhiqing.tinybay.spider.SpiderClient;
import info.zhiqing.tinybay.util.CategoryUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhiqing on 18-1-5.
 */

public class TorrentListAdapter extends RecyclerView.Adapter<TorrentListAdapter.TorrentListViewHolder> {
    public static final String TAG = "TorrentListAdapter";

    private Context context;
    private List<Torrent> torrents;
    private int currentPage = 0;
    private String baseUrl;
    private boolean loadedAll = false;
    private int loadedCount = 0;

    public TorrentListAdapter(Context context, String url) {
        this.context = context;
        this.torrents = new ArrayList<>();
        this.baseUrl = url;
    }

    public void addData(List<Torrent> torrents) {
        this.torrents.addAll(torrents);
        loadedCount++;
        this.notifyDataSetChanged();
    }

    public boolean isLoadedAll() {
        return loadedAll;
    }

    public void setLoadedAll(boolean loadedAll) {
        this.loadedAll = loadedAll;
    }

    public int getLoadedCount() {
        return loadedCount;
    }

    public void clearLoadedCount() {
        loadedCount = 0;
    }

    public void refreshData() {
        torrents.clear();
        loadedAll = false;
        currentPage = 0;
        loadData();
    }

    public Observable<Torrent> loadData() {

        if (loadedAll) return Observable.empty();

        String url = baseUrl + "/" + currentPage + "";

        clearLoadedCount();

        Observable<Torrent> observable = SpiderClient.getInstance().fetchTorrentsByUrl(url);
        observable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<Torrent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Start load!");
                    }

                    @Override
                    public void onNext(Torrent torrent) {
                        addData(torrent);
                        Log.d(TAG, torrent.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Load failed: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Load completed!");
                        currentPage++;
                        if (getLoadedCount() == 0) {
                            loadedAll = true;
                        }
                    }
                });

        return observable;
    }

    public void addData(Torrent torrent) {
        this.torrents.add(torrent);
        loadedCount++;
        this.notifyDataSetChanged();
    }

    @Override
    public TorrentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new TorrentListViewHolder(inflater.inflate(R.layout.torrents_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TorrentListViewHolder holder, int position) {
        Torrent torrent = torrents.get(position);
        holder.backLayout.setBackgroundColor(CategoryUtil.codeToColor(torrent.getTypeCode()));
        holder.iconIcon.setImageDrawable(context.getResources().getDrawable(CategoryUtil.codeToIconRes(torrent.getTypeCode())));
        holder.iconCate.setText(CategoryUtil.codeToTitle(torrent.getTypeCode()));
        holder.title.setText(torrent.getTitle());
        String info = torrent.getSize() + " • " + torrent.getAuthor() + " • ⇧" + torrent.getSeeders() + " • ⇩" + torrent.getLeechers();
        holder.info.setText(info);
    }

    @Override
    public int getItemCount() {
        return torrents != null ? torrents.size() : 0;
    }

    class TorrentListViewHolder extends RecyclerView.ViewHolder {
        View backLayout;
        ImageView iconIcon;
        TextView iconCate;
        TextView title;
        TextView info;

        public TorrentListViewHolder(View itemView) {
            super(itemView);
            backLayout = itemView.findViewById(R.id.back_layout);
            iconIcon = itemView.findViewById(R.id.icon_icon);
            iconCate = itemView.findViewById(R.id.icon_cate);
            title = itemView.findViewById(R.id.text_title);
            info = itemView.findViewById(R.id.text_info);
        }
    }
}

