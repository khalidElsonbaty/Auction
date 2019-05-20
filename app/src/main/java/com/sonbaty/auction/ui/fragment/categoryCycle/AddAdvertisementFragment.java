package com.sonbaty.auction.ui.fragment.categoryCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.adapter.CategoryRecyclerAdapter;
import com.sonbaty.auction.adapter.HomeRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.ads.Ads;
import com.sonbaty.auction.data.model.ads.Data;
import com.sonbaty.auction.data.model.categories.Categories;
import com.sonbaty.auction.data.model.categories.CategoriesData;
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
public class AddAdvertisementFragment extends Fragment {


    @BindView(R.id.AddAdvertisement_RecyclerView_Order)
    RecyclerView AddAdvertisementRecyclerViewOrder;
    Unbinder unbinder;
    private List<CategoriesData> data;
    private ApiServices apiServices;
    private CategoryRecyclerAdapter addAdvertisementAdapter;
    private LinearLayoutManager layoutManager;

    public AddAdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_advertisement, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        initViews();
        return view;
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
    }

    private void initViews() {
        data = new ArrayList<>();
        HelperMethod.showProgressDialog(getActivity(), "Loading");
        apiServices.getCategoriesData().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    data.addAll(response.body().getData());
                    addAdvertisementAdapter = new CategoryRecyclerAdapter(getContext(), data, "advertisement");
                    AddAdvertisementRecyclerViewOrder.setAdapter(addAdvertisementAdapter);
                    layoutManager = new LinearLayoutManager(getActivity());
                    AddAdvertisementRecyclerViewOrder.setLayoutManager(layoutManager);
                    AddAdvertisementRecyclerViewOrder.setNestedScrollingEnabled(true);
                    addAdvertisementAdapter.notifyDataSetChanged();
                    addAdvertisementAdapter.setOnItemClickListener(new CategoryRecyclerAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Toast.makeText(getActivity(), response.body().getData().get(position).getName(), Toast.LENGTH_SHORT).show();
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
