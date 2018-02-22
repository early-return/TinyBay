package info.zhiqing.tinypiratebay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import info.zhiqing.tinypiratebay.R;
import info.zhiqing.tinypiratebay.entities.Torrent;
import info.zhiqing.tinypiratebay.util.CategoryUtil;

/**
 * Created by lizhi on 2018/1/9.
 */

public class TorrentsAdapter extends BaseQuickAdapter<Torrent, BaseViewHolder> {

    public TorrentsAdapter(int layoutResId, @Nullable List<Torrent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Torrent item) {
        helper.setBackgroundColor(R.id.back_layout, CategoryUtil.codeToColor(item.getTypeCode()));
        helper.setImageResource(R.id.icon_icon, CategoryUtil.codeToIconRes(item.getTypeCode()));
        helper.setText(R.id.icon_cate, CategoryUtil.codeToTitle(CategoryUtil.parentCode(item.getTypeCode())));
        helper.setText(R.id.text_title, item.getTitle());
        helper.setText(R.id.text_info, item.getSize() + " • " + item.getAuthor() + " • ⇧" + item.getSeeders() + " • ⇩" + item.getLeechers());
    }

}
