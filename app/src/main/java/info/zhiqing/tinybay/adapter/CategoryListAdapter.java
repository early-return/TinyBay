package info.zhiqing.tinybay.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.activity.SearchActivity;
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
        return new CateListItemViewHolder(inflater.inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CateListItemViewHolder holder, int position) {
        final Category category = CategoryUtil.CATEGORIES.get(position);
        holder.icon.setImageResource(
                CategoryUtil.codeToIconRes(category.getCode()));
        holder.title.setText(category.getTitle());
        holder.cateListBar.setBackgroundColor(
                CategoryUtil.codeToColor(category.getCode()));
        holder.list.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        holder.list.setAdapter(adapters.get(position));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.actionStart(context, "https://thepiratebay.org/browse/" + category.getCode(), category.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return CategoryUtil.CATEGORIES.size();
    }

    class CateListItemViewHolder extends RecyclerView.ViewHolder {
        View item;
        ImageView icon;
        TextView title;
        RecyclerView list;
        View cateListBar;

        public CateListItemViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.category_item);
            icon = itemView.findViewById(R.id.cate_title_bar_icon);
            title = itemView.findViewById(R.id.cate_title_bar_title);
            list = itemView.findViewById(R.id.sub_category_list);
            cateListBar = itemView.findViewById(R.id.cate_list_bar);
        }
    }
}
