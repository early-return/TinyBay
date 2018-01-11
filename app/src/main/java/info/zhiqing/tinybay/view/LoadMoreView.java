package info.zhiqing.tinybay.view;

import info.zhiqing.tinybay.R;

/**
 * Created by lizhi on 2018/1/9.
 */

public class LoadMoreView extends com.chad.library.adapter.base.loadmore.LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.torrents_loadmore;
    }


    @Override
    protected int getLoadingViewId() {
        return R.id.loading_more_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.loading_more_failed;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.loading_more_end;
    }
}
