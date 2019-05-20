package com.sonbaty.auction.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sonbaty.auction.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AskLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.Ask_Login_Button_Login, R.id.Ask_Login_Button_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Ask_Login_Button_Login:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.Ask_Login_Button_Register:
                break;
        }
    }
}
