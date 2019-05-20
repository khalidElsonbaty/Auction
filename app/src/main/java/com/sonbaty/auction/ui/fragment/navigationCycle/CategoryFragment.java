package com.sonbaty.auction.ui.fragment.navigationCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.adapter.CategoryRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.categories.Categories;
import com.sonbaty.auction.data.model.categories.CategoriesData;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.ui.activity.HomeActivity;
import com.sonbaty.auction.ui.fragment.categoryCycle.SubCategoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    @BindView(R.id.Category_RecyclerView_Category)
    RecyclerView CategoryRecyclerViewCategory;
    Unbinder unbinder;
    private View view;
    private GridLayoutManager layoutManager;
    private CategoryRecyclerAdapter categoryAdapter;
    private List<CategoriesData> categoriesData;

    private ApiServices apiServices;
    private TextView toolbarTitle = null;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        onRunRequest();
        return view;
    }

    private void intiObjects() {
        categoriesData = new ArrayList<>();
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
    }

    private void onRunRequest() {
        HelperMethod.showProgressDialog(getActivity(), "Loading...");
        apiServices.getCategoriesData().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    categoriesData.addAll(response.body().getData());
                    categoryAdapter = new CategoryRecyclerAdapter(getContext(), categoriesData ,"categories");
                    CategoryRecyclerViewCategory.setAdapter(categoryAdapter);
                    layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                    CategoryRecyclerViewCategory.setLayoutManager(layoutManager);
                    CategoryRecyclerViewCategory.setNestedScrollingEnabled(true);
                    categoryAdapter.notifyDataSetChanged();
                    categoryAdapter.setOnItemClickListener(new CategoryRecyclerAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Toast.makeText(getActivity(), categoriesData.get(position).getName(), Toast.LENGTH_SHORT).show();
                            HomeActivity homeActivity = (HomeActivity) getActivity();
                            toolbarTitle = homeActivity.toolbarTitle;
                            SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("category_id",categoriesData.get(position).getId());
                            subCategoryFragment.setArguments(bundle);
                            HelperMethod.replace(subCategoryFragment,getActivity().getSupportFragmentManager(), R.id.Home_Container,toolbarTitle,categoriesData.get(position).getName());
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {
                HelperMethod.dismissProgressDialog();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
