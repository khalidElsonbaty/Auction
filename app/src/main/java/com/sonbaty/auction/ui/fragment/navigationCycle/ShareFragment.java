package com.sonbaty.auction.ui.fragment.navigationCycle;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonbaty.auction.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    Unbinder unbinder;
    private View view;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_share, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Share_Button_Facebook, R.id.Share_Button_Twitter, R.id.Share_Button_Google, R.id.Share_Button_WhatsUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Share_Button_Facebook:
                Intent f = new Intent(Intent.ACTION_VIEW);
                f.setData(Uri.parse("https://www.facebook.com/"));
                startActivity(f);
                break;
            case R.id.Share_Button_Twitter:
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(Uri.parse("https://twitter.com/"));
                startActivity(t);
                break;
            case R.id.Share_Button_Google:
                Intent g = new Intent(Intent.ACTION_VIEW);
                g.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.plus&hl=en"));
                startActivity(g);
                break;
            case R.id.Share_Button_WhatsUp:
                Intent w = new Intent(Intent.ACTION_VIEW);
                w.setData(Uri.parse("https://www.whatsapp.com/"));
                startActivity(w);
                break;
        }
    }
}
