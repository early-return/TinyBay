package info.zhiqing.tinybay.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.adapter.SubCategoryRecyclerAdapter;
import info.zhiqing.tinybay.entities.Category;
import info.zhiqing.tinybay.util.CategoryUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {

    private Category parentCate;
    private List<Category> categories;

    private static final String ARG_PARENT = "info.zhiqing.tinybay.ARG_PARENT";

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(Category parentCate) {
        SubCategoryFragment fragment = new SubCategoryFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARENT, parentCate);

        fragment.setArguments(args);

        return fragment;
    }


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_category, container, false);


        initView(v);

        return v;
    }

    private void init() {
        parentCate = (Category) getArguments().getSerializable(ARG_PARENT);
        categories = CategoryUtil.SUB_CATEGORIES.get(parentCate.getCode());

        adapter = new SubCategoryRecyclerAdapter(getContext(), categories);
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.sub_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }
}
