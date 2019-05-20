package com.sonbaty.auction.ui.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.helper.HelperMethod;
import com.sonbaty.auction.ui.fragment.homeCycle.AdvertisementFragment;
import com.sonbaty.auction.ui.fragment.homeCycle.ContainerFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.AboutFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.CategoryFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.ContactUsFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.MyAccountFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.OnFragmentInteractionListener;
import com.sonbaty.auction.ui.fragment.navigationCycle.RegisterCardFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.RulesFragment;
import com.sonbaty.auction.ui.fragment.navigationCycle.ShareFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @BindView(R.id.Home_Toolbar_Tv)
    TextView HomeToolbarTv;
    @BindView(R.id.Home_View_Logo)
    RelativeLayout HomeViewLogo;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public TextView toolbarTitle;
    private ImageView toolbarIconSearch;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        intiViews();
    }

    private void intiViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.Home_DrawerLayout);
        toolbar = (Toolbar) findViewById(R.id.Home_ToolBar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.Home_Toolbar_Tv);
        toolbarIconSearch = (ImageView) toolbar.findViewById(R.id.Home_Toolbar_iv);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toolbar_open, R.string.toolbar_close);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.sidemenu, this.getTheme());
        actionBarDrawerToggle.setHomeAsUpIndicator(drawable);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        navigationView = (NavigationView) findViewById(R.id.Client_Home_Navigation_Drawer);
        navigationView.setNavigationItemSelectedListener(this);

        HomeToolbarTv.setVisibility(View.GONE);
        HomeViewLogo.setVisibility(View.VISIBLE);
        HelperMethod.replace(new ContainerFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "الرئيسية");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                HelperMethod.replace(new ContainerFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "الرئيسية");
                break;
            case R.id.nav_my_account:
                HelperMethod.replace(new MyAccountFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "حسابي");
                break;
            case R.id.nav_category:
                HelperMethod.replace(new CategoryFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "أقسام");
                break;
            case R.id.nav_advertisement:
                HelperMethod.replace(new AdvertisementFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "اعلاناتي");
                break;
            case R.id.nav_register_card:
                HelperMethod.replace(new RegisterCardFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "باقات الاشتراك");
                break;
            case R.id.nav_share_app:
                HelperMethod.replace(new ShareFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "مشاركة البرنامج");
                break;
            case R.id.nav_rules:
                HelperMethod.replace(new RulesFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "الشروط والاحكام");
                break;
            case R.id.nav_about:
                HelperMethod.replace(new AboutFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "حول البرنامج");
                break;
            case R.id.nav_contact_us:
                HelperMethod.replace(new ContactUsFragment(), getSupportFragmentManager(), R.id.Home_Container, toolbarTitle, "تواصل معنا");
                break;

        }
        navigationView.setCheckedItem(menuItem);
        if (menuItem.getItemId() == R.id.nav_home) {
            HomeToolbarTv.setVisibility(View.GONE);
            HomeViewLogo.setVisibility(View.VISIBLE);
        } else {
            HomeToolbarTv.setVisibility(View.VISIBLE);
            HomeViewLogo.setVisibility(View.GONE);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
