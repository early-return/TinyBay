package info.zhiqing.tinybay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.Torrent;
import info.zhiqing.tinybay.util.CategoryUtil;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        return new TorrentListViewHolder(inflater.inflate(R.layout.torrents_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TorrentListViewHolder holder, int position) {
        Torrent torrent = torrents.get(position);
        holder.title.setText(torrent.getTitle());
        String info = CategoryUtil.codeToTitle(torrent.getTypeCode()) + "•" + torrent.getSize() + "•" + torrent.getAuthor();
        holder.info.setText(info);
    }

    @Override
    public int getItemCount() {
        return torrents != null ? torrents.size() : 0;
    }

    class TorrentListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView info;

        public TorrentListViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            info = itemView.findViewById(R.id.text_info);
        }
    }
}

