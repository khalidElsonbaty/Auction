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
import com.sonbaty.auction.adapter.ConversationRecyclerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.conversation.Conversation;
import com.sonbaty.auction.data.model.conversation.ConversationData;
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
public class ConversationFragment extends Fragment {

    View view;

    Unbinder unbinder;
    @BindView(R.id.Conversation_RecyclerView_Views)
    RecyclerView ConversationRecyclerViewViews;
    private ApiServices apiServices;
    private List<ConversationData> data;
    private ConversationRecyclerAdapter conversationAdapter;
    private LinearLayoutManager layoutManager;

    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_conversation, container, false);
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
        HelperMethod.showProgressDialog(getActivity(), "Loading...");
        apiServices.getConversationData(88).enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(@NonNull Call<Conversation> call, @NonNull Response<Conversation> response) {
                HelperMethod.dismissProgressDialog();
                Log.i(TAG, "onResponse: " + response.raw());
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse Successful: " + response.raw());
                    data.addAll(response.body().getData());
                    conversationAdapter = new ConversationRecyclerAdapter(getContext(), data);
                    ConversationRecyclerViewViews.setAdapter(conversationAdapter);
                    layoutManager = new LinearLayoutManager(getActivity());
                    ConversationRecyclerViewViews.setLayoutManager(layoutManager);
                    ConversationRecyclerViewViews.setNestedScrollingEnabled(true);
                    conversationAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Conversation> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
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
