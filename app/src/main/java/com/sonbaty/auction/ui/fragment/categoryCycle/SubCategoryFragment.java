package com.sonbaty.auction.ui.fragment.categoryCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.adapter.CategoryRecyclerAdapter;
import com.sonbaty.auction.adapter.SubCategoryRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.subCategories.SubCategories;
import com.sonbaty.auction.data.model.subCategories.SubCategoriesData;
import com.sonbaty.auction.helper.HelperMethod;

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
public class SubCategoryFragment extends Fragment {

    View view;
    @BindView(R.id.SubCategory_RecyclerView_Product)
    RecyclerView SubCategoryRecyclerViewProduct;
    Unbinder unbinder;
    private ApiServices apiServices;
    private List<SubCategoriesData> subCategoriesData;
    private SubCategoryRecyclerAdapter subCategoryAdapter;
    private GridLayoutManager layoutManager;

    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        onRunRequest();
        return view;
    }

    private void intiObjects() {
        subCategoriesData = new ArrayList<>();
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
    }

    private void onRunRequest() {
        int category_id = getArguments().getInt("category_id", 0);
        HelperMethod.showProgressDialog(getActivity(),"Loading");
        apiServices.getSubCategoriesData(category_id).enqueue(new Callback<SubCategories>() {
            @Override
            public void onResponse(@NonNull Call<SubCategories> call,@NonNull Response<SubCategories> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body()!= null){
                    subCategoriesData.addAll(response.body().getData());
                    subCategoryAdapter = new SubCategoryRecyclerAdapter(getContext(), subCategoriesData);
                    SubCategoryRecyclerViewProduct.setAdapter(subCategoryAdapter);
                    layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                    SubCategoryRecyclerViewProduct.setLayoutManager(layoutManager);
                    SubCategoryRecyclerViewProduct.setNestedScrollingEnabled(true);
                    subCategoryAdapter.notifyDataSetChanged();
                    subCategoryAdapter.setOnItemClickListener(new CategoryRecyclerAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Toast.makeText(getActivity(), subCategoriesData.get(position).getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubCategories> call,@NonNull Throwable t) {
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
