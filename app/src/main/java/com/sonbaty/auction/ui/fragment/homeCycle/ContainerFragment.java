package com.sonbaty.auction.ui.fragment.homeCycle;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.adapter.ConversationRecyclerAdapter;
import com.sonbaty.auction.adapter.ServiceConditionsRecyclerAdapter;
import com.sonbaty.auction.adapter.ViewPagerAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.serviceConditions.ServiceConditions;
import com.sonbaty.auction.data.model.serviceConditions.ServiceConditionsData;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.ui.activity.HomeActivity;
import com.sonbaty.auction.ui.fragment.categoryCycle.AddAdvertisementFragment;
import com.sonbaty.auction.ui.fragment.categoryCycle.SubCategoryFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContainerFragment extends Fragment {

    View view;
    Unbinder unbinder;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] navIcons = {
            R.drawable.home,
            R.drawable.conversation,
            R.drawable.notifi,
            R.drawable.favourit,
            R.drawable.ads
    };
    private int[] navLabels = {
            R.string.nav_home,
            R.string.nav_conversation,
            R.string.nav_notifications,
            R.string.nav_favourite,
            R.string.nav_advertisement
    };
    private OnFragmentInteractionListener mListener;

    private int[] navIconsActive = {
            R.drawable.homee,
            R.drawable.conversationnn,
            R.drawable.noti,
            R.drawable.likee,
            R.drawable.adss
    };
    private ApiServices apiServices;
    private List<ServiceConditionsData> data;
    private ServiceConditionsRecyclerAdapter serviceConditionsAdapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private RecyclerView serviceConditionsRecyclerView;
    private TextView toolbarTitle = null;


    public ContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_container, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiObjects();
        intiViews();
        return view;
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        data = new ArrayList<>();
    }


    private void intiViews() {
        viewPager = (ViewPager) view.findViewById(R.id.Container_ViewPager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.Container_TabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.nav_tab, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);
            tab_label.setText(getResources().getString(navLabels[i]));
            if (i == 0) {
                tab_label.setTextColor(getResources().getColor(R.color.efent_color));
                tab_icon.setImageResource(navIconsActive[i]);
            } else {
                tab_icon.setImageResource(navIcons[i]);
            }
            tabLayout.getTabAt(i).setCustomView(tab);
            tabLayout.setOnTabSelectedListener(
                    new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            super.onTabSelected(tab);
                            View tabView = tab.getCustomView();
                            TextView tab_label = (TextView) tabView.findViewById(R.id.nav_label);
                            ImageView tab_icon = (ImageView) tabView.findViewById(R.id.nav_icon);
                            tab_label.setTextColor(getResources().getColor(R.color.efent_color));
                            tab_icon.setImageResource(navIconsActive[tab.getPosition()]);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                            super.onTabUnselected(tab);
                            View tabView = tab.getCustomView();
                            TextView tab_label = (TextView) tabView.findViewById(R.id.nav_label);
                            ImageView tab_icon = (ImageView) tabView.findViewById(R.id.nav_icon);
                            tab_label.setTextColor(getResources().getColor(R.color.white_color));
                            tab_icon.setImageResource(navIcons[tab.getPosition()]);
                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            super.onTabReselected(tab);
                        }
                    }
            );

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HomeFragment(), "الرئيسية");
        adapter.addFragment(new ConversationFragment(), "المحادثات");
        adapter.addFragment(new notificationFragment(), "الاشعاراة");
        adapter.addFragment(new FavouriteFragment(), "المفضلة");
        adapter.addFragment(new AdvertisementFragment(), "إعلانات مميزة");
        viewPager.setAdapter(adapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Container_FloatingActionButton)
    public void onViewClicked() {
        showDialog();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialoge_rules);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar = (ProgressBar) dialog.findViewById(R.id.ServiceConditions_ProgressBar);
        serviceConditionsRecyclerView = (RecyclerView) dialog.findViewById(R.id.ServiceConditions_RecyclerView);
        ImageView cancelView = (ImageView) dialog.findViewById(R.id.ServiceConditions_ImageView_cancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView okView = (ImageView) dialog.findViewById(R.id.ServiceConditions_ImageView_ok);
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                HomeActivity homeActivity = (HomeActivity) getActivity();
                toolbarTitle = homeActivity.toolbarTitle;
                HelperMethod.replace(new AddAdvertisementFragment(), getActivity().getSupportFragmentManager(), R.id.Home_Container, toolbarTitle,"اضافة اعلان");
            }
        });
        dialog.show();
        onDataRequest();
    }

    private void onDataRequest() {
        progressBar.setVisibility(View.VISIBLE);
        apiServices.getServiceConditionsData().enqueue(new Callback<ServiceConditions>() {
            @Override
            public void onResponse(@NonNull Call<ServiceConditions> call, @NonNull Response<ServiceConditions> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    data.addAll(response.body().getData());
                    serviceConditionsAdapter = new ServiceConditionsRecyclerAdapter(getContext(), data);
                    serviceConditionsRecyclerView.setAdapter(serviceConditionsAdapter);
                    layoutManager = new LinearLayoutManager(getActivity());
                    serviceConditionsRecyclerView.setLayoutManager(layoutManager);
                    serviceConditionsRecyclerView.setNestedScrollingEnabled(true);
                    serviceConditionsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceConditions> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
