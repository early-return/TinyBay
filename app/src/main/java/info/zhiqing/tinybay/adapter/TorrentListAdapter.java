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

public class TorrentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "TorrentListAdapter";

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    private Context context;
    private List<Torrent> torrents;
    private boolean loadingMore = false;

    public TorrentListAdapter(Context context) {
        Log.d(TAG, "In TorrentListAdapter generator");

        this.context = context;
        this.torrents = new ArrayList<>();
    }

    public void addData(List<Torrent> torrents) {
        this.torrents.addAll(torrents);
    }

    public void clearData() {
        torrents.clear();
    }

    public boolean isLoadingMore() {
        return loadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        this.loadingMore = loadingMore;
    }

    public void addData(Torrent torrent) {
        this.torrents.add(torrent);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d(TAG, "On Create View Holder: " + viewType);
        if (viewType == TYPE_ITEM) {
            return new TorrentListViewHolder(inflater.inflate(R.layout.torrents_item, parent, false));
        } else {
            return new TorrentListFooterHolder(inflater.inflate(R.layout.torrents_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            Torrent torrent = torrents.get(position);
            TorrentListViewHolder holder = (TorrentListViewHolder) viewHolder;
            holder.backLayout.setBackgroundColor(CategoryUtil.codeToColor(torrent.getTypeCode()));
            holder.iconIcon.setImageDrawable(context.getResources().getDrawable(CategoryUtil.codeToIconRes(torrent.getTypeCode())));
            String title = CategoryUtil.codeToTitle(CategoryUtil.parentCode(torrent.getTypeCode()));
            holder.iconCate.setText(title);
            holder.title.setText(torrent.getTitle());
            String info = torrent.getSize() + " • " + torrent.getAuthor() + " • ⇧" + torrent.getSeeders() + " • ⇩" + torrent.getLeechers();
            holder.info.setText(info);
        }
        Log.d(TAG, "On Bind: " + position);
    }

    @Override
    public int getItemCount() {
        int count;
        if (loadingMore) {
            count = torrents.size() + 1;
        } else {
            count = torrents.size();
        }
        Log.d(TAG, "Get item count: " + count);
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = position == torrents.size() ? TYPE_FOOTER : TYPE_ITEM;
        Log.d(TAG, position + " : " +type);
        return type;
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

    class TorrentListFooterHolder extends RecyclerView.ViewHolder {
        public TorrentListFooterHolder(View itemView) {
            super(itemView);
        }
    }

}

