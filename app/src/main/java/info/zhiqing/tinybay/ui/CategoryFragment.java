package info.zhiqing.tinybay.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.adapter.CategoryListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView categoriesListView;
    private View rootView;

    private CategoryListAdapter adapter = null;


    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {


        return new CategoryFragment();
    }

    private void init() {
        if (adapter == null) {
            adapter = new CategoryListAdapter(getContext());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        categoriesListView = v.findViewById(R.id.cate_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        categoriesListView.setLayoutManager(layoutManager);

        categoriesListView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
