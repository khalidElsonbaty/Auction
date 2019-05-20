package com.sonbaty.auction.ui.fragment.homeCycle;


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
import com.sonbaty.auction.adapter.NotificationsRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.notifications.Notifications;
import com.sonbaty.auction.data.model.notifications.NotificationsData;
import com.sonbaty.auction.helper.HelperMethod;

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
public class notificationFragment extends Fragment {

    View view;
    @BindView(R.id.Notifications_RecyclerView_Views)
    RecyclerView NotificationsRecyclerViewViews;
    Unbinder unbinder;
    private ApiServices apiServices;
    private List<NotificationsData> data;
    private NotificationsRecyclerAdapter notificationsAdapter;
    private LinearLayoutManager layoutManager;

    public notificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
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
        onRunRequest();
    }

    private void onRunRequest() {
        apiServices.getNotificationsData(88).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(@NonNull Call<Notifications> call,@NonNull Response<Notifications> response) {

                if (response.isSuccessful() && response.body() != null)
                {
                    data.addAll(response.body().getData());
                    notificationsAdapter = new NotificationsRecyclerAdapter(getContext(), data);
                    NotificationsRecyclerViewViews.setAdapter(notificationsAdapter);
                    layoutManager = new LinearLayoutManager(getActivity());
                    NotificationsRecyclerViewViews.setLayoutManager(layoutManager);
                    NotificationsRecyclerViewViews.setNestedScrollingEnabled(true);
                    notificationsAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Notifications> call,@NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: "+t.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
