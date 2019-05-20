package com.sonbaty.auction.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.google.firebase.FirebaseApp;
import com.sonbaty.auction.R;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.ui.fragment.loginCycle.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.Login_FrameLayout_Container)
    FrameLayout LoginFrameLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        HelperMethod.replace(new LoginFragment(), getSupportFragmentManager(),R.id.Login_FrameLayout_Container,null,null );
    }
}
