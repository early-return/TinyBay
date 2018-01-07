package info.zhiqing.tinybay.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.Category;
import info.zhiqing.tinybay.util.CategoryUtil;

/**
 * Created by zhiqing on 18-1-7.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CateListItemViewHolder> {

    private Context context;
    private List<SubCategoryRecyclerAdapter> adapters;

    public CategoryListAdapter(Context context) {
        this.context = context;

        adapters = new ArrayList<>();
        for (Category category : CategoryUtil.CATEGORIES) {
            adapters.add(new SubCategoryRecyclerAdapter(context, CategoryUtil.SUB_CATEGORIES.get(category.getCode())));
        }
    }

    @Override
    public CateListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new CateListItemViewHolder(inflater.inflate(R.layout.fragment_sub_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CateListItemViewHolder holder, int position) {
        holder.icon.setImageResource(CategoryUtil.codeToIconRes(CategoryUtil.CATEGORIES.get(position).getCode()));
        holder.title.setText(CategoryUtil.CATEGORIES.get(position).getTitle());
        holder.cateListBar.setBackgroundColor(CategoryUtil.codeToColor(CategoryUtil.CATEGORIES.get(position).getCode()));
        holder.list.setLayoutManager(new GridLayoutManager(context, 4));
        holder.list.setAdapter(adapters.get(position));
    }

    @Override
    public int getItemCount() {
        return CategoryUtil.CATEGORIES.size();
    }

    class CateListItemViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        RecyclerView list;
        View cateListBar;

        public CateListItemViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.cate_title_bar_icon);
            title = itemView.findViewById(R.id.cate_title_bar_title);
            list = itemView.findViewById(R.id.sub_category_list);
            cateListBar = itemView.findViewById(R.id.cate_list_bar);
        }
    }
}
