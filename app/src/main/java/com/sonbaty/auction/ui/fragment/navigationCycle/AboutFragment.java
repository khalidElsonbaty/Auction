package com.sonbaty.auction.ui.fragment.navigationCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.about.About;
import com.sonbaty.auction.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    @BindView(R.id.About_TextView_Text)
    TextView AboutTextViewText;
    Unbinder unbinder;
    private View view;
    private ApiServices apiServices;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        return view;
    }

    private void getData() {
        HelperMethod.showProgressDialog(getActivity(), "Loading...");
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        apiServices.getAboutText().enqueue(new Callback<About>() {
            @Override
            public void onResponse(@NonNull Call<About> call, @NonNull Response<About> response) {
                HelperMethod.dismissProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    AboutTextViewText.setText(response.body().getData());
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<About> call, @NonNull Throwable t) {
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
