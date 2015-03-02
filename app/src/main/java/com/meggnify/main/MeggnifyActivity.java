package com.meggnify.main;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.auth.AuthActivity;
import com.meggnify.helper.API;
import com.meggnify.helper.Constants;
import com.meggnify.helper.Util;
import com.meggnify.helper.so;
import com.meggnify.lib.LocalContext;
import com.raaf.rDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

public class MeggnifyActivity extends BaseActivity implements DrawerFragment.DrawerCallback, IMainListener {

    private Context mContext;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private DrawerFragment drawerFragment;

    private CharSequence mTitle;
    Fragment fragment = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meggnify);
        bindToolbar();
        mContext = LocalContext.getContext();

        meggManager = MeggnifyManager.sharedInstance();

        mUserToken = getToken(mContext);

        mTitle = getTitle();

        setCustomTitle("Home");

        drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        drawerFragment.setView(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (so.getUser().getFull_name() == null)
            API.MegProfile(Util.getToken(this),handler);
    }

    @Override
    protected void onHandlerResponse(Message msg) {
        super.onHandlerResponse(msg);
//        if (so.result.getStatus()==200){
//            if (so.result.getModul()==Constants.modul_meggnets && so.result.getMode()==3){
//
//            }
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (drawerFragment.isDrawerOpen())
                drawerFragment.closeDrawer();
            else
                drawerFragment.openDrawer();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AssignmentFragment();
                break;
            case 2:
                fragment = new CashFragment();
                break;
            case 3:
                fragment = new ProfileFragment();
                break;
            case 4:
                fragment = new SettingFragment();
                break;
            case 5:
                fragment = new CashSuccessFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        Bundle extras = new Bundle();
        extras.putString("token", mUserToken);
        fragment.setArguments(extras);

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    private String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token = prefs.getString(Constants.PREF_USER_TOKEN, "");
        if (token.isEmpty()) {
            return null;
        }
        return token;
    }

    @Override
    public void onFragmentAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "Home";
                break;
            case 1:
                mTitle = "Assignment";
                break;
            case 2:
            case 5:
                mTitle = "Cash Account";
                break;
            case 3:
                mTitle = "Profile";
                break;
            case 4:
                mTitle = "Settings";
                break;
            default:
                mTitle = "Home";
                break;
        }
        setCustomTitle(mTitle.toString());
    }

    @Override
    public void onBackPressed() {
        if (drawerFragment.isDrawerOpen())
            drawerFragment.closeDrawer();
        else
            super.onBackPressed();

        return;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (drawerFragment.isDrawerOpen())
                drawerFragment.closeDrawer();
            else
                drawerFragment.openDrawer();
            return true;
        } else
            return super.onKeyUp(keyCode, event);
    }

    public void AssignmentSelectItem(int i)
    {
        ((AssignmentFragment)fragment).selectItem(i);
    }

    public void AssignmentSetTitle(String s)
    {
        ((AssignmentFragment)fragment).SetTitle(s);
    }

    public void ChangeModule(int i)
    {
        drawerFragment.selectItem(i);
    }


}
