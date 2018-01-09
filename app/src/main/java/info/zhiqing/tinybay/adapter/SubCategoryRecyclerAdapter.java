package info.zhiqing.tinybay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.ui.SearchActivity;
import info.zhiqing.tinybay.entities.Category;
import info.zhiqing.tinybay.util.CategoryUtil;

/**
 * Created by zhiqing on 18-1-5.
 */

public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter<SubCategoryRecyclerAdapter.SubCategoryItemViewHolder> {

    Context context;
    List<Category> categories;

    public SubCategoryRecyclerAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }


    @Override
    public SubCategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new SubCategoryItemViewHolder(inflater.inflate(R.layout.sub_cate_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SubCategoryItemViewHolder holder, final int position) {
        holder.textView.setText(categories.get(position).getTitle());
        holder.textView.setBackgroundColor(CategoryUtil.codeToColor(categories.get(position).getCode()));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.actionStart(context, "https://thepiratebay.org/browse/" + categories.get(position).getCode(), categories.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class SubCategoryItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public SubCategoryItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sub_cate_button);
        }
    }
}


