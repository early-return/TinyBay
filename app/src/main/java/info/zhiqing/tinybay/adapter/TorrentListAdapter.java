package info.zhiqing.tinybay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.zhiqing.tinybay.entities.Torrent;

/**
 * Created by zhiqing on 18-1-5.
 */

public class TorrentListAdapter extends RecyclerView.Adapter<TorrentListAdapter.TorrentListViewHolder> {

    private Context context;
    private List<Torrent> torrents;

    public TorrentListAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<Torrent> torrents) {
        this.torrents.addAll(torrents);
        this.notifyDataSetChanged();
    }

    @Override
    public TorrentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TorrentListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TorrentListViewHolder extends RecyclerView.ViewHolder {

        public TorrentListViewHolder(View itemView) {
            super(itemView);
        }
    }
}

