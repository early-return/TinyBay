package info.zhiqing.tinybay.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.zhiqing.tinybay.R;
import info.zhiqing.tinybay.entities.Category;

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

    private void init() {
        parentCate = (Category) getArguments().getSerializable(ARG_PARENT);
        categories = CategoryFragment.subCategories.get(parentCate.getCode());
    }

    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_category, container, false);

        init();


        textView = v.findViewById(R.id.test_text);
        textView.setText(parentCate.getTitle());


        return v;
    }

}
