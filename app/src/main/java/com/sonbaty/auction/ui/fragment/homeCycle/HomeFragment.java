package com.sonbaty.auction.ui.fragment.homeCycle;


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
import com.sonbaty.auction.adapter.HomeRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.ads.Ads;
import com.sonbaty.auction.data.model.ads.Data;
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
public class HomeFragment extends Fragment {

    View view;

    Unbinder unbinder;
    @BindView(R.id.Home_RecyclerView_Ads)
    RecyclerView HomeRecyclerViewAds;
    private ApiServices apiServices;
    private List<Data> data;
    private HomeRecyclerAdapter homeAdapter;
    private LinearLayoutManager layoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        intiObjects();
        initViews();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        data = new ArrayList<>();
    }

    private void initViews() {
        HelperMethod.showProgressDialog(getActivity(), "Loading");
        apiServices.getAdsData().enqueue(new Callback<Ads>() {
            @Override
            public void onResponse(@NonNull Call<Ads> call, @NonNull Response<Ads> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    List<Data> list = response.body().getData();
                    for (int i = 0; i < list.size(); i++) {
                        data.addAll(response.body().getData());
                        homeAdapter = new HomeRecyclerAdapter(getContext(), data,"Home");
                        HomeRecyclerViewAds.setAdapter(homeAdapter);
                        layoutManager = new LinearLayoutManager(getActivity());
                        HomeRecyclerViewAds.setLayoutManager(layoutManager);
                        HomeRecyclerViewAds.setNestedScrollingEnabled(true);
                        homeAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Ads> call, @NonNull Throwable t) {
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
