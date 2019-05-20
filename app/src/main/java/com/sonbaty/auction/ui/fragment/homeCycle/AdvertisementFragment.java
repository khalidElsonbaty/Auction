package com.sonbaty.auction.ui.fragment.homeCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.adapter.AdsRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.ads.Ads;
import com.sonbaty.auction.data.model.ads.AdsData;
import com.sonbaty.auction.data.model.ads.Data;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.ui.activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertisementFragment extends Fragment {

    private View view;
    @BindView(R.id.Advertisement_RecyclerView_Ads)
    RecyclerView AdvertisementRecyclerViewAds;
    Unbinder unbinder;
    private ApiServices apiServices;
    private AdsRecyclerAdapter adsAdapter;
    private List<AdsData> data;
    private LinearLayoutManager layoutManager;

    public AdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_advertisement, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        initViews();
        return view;
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        data = new ArrayList<>();
    }



    private void initViews() {
        HelperMethod.showProgressDialog(getActivity(), "Loading...");
        apiServices.getAdsData().enqueue(new Callback<Ads>() {
            @Override
            public void onResponse(@NonNull Call<Ads> call, @NonNull Response<Ads> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    List<Data> list = response.body().getData();
                    for (int i = 0; i < list.size(); i++) {
                        data.addAll(response.body().getData().get(i).getAds());
                        adsAdapter = new AdsRecyclerAdapter(getContext(), data);
                        AdvertisementRecyclerViewAds.setAdapter(adsAdapter);
                        layoutManager = new LinearLayoutManager(getActivity());
                        AdvertisementRecyclerViewAds.setLayoutManager(layoutManager);
                        AdvertisementRecyclerViewAds.setNestedScrollingEnabled(true);
                        adsAdapter.notifyDataSetChanged();
                        adsAdapter.setOnItemClickListener(new AdsRecyclerAdapter.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                startActivity(intent);
                            }
                        });
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
