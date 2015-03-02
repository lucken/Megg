// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.meggnify.R;
import com.meggnify.auth.AuthActivity;
import com.meggnify.helper.API;
import com.meggnify.helper.Constants;
import com.meggnify.helper.so;
import com.raaf.rDialog;

public class BaseActivity extends ActionBarActivity {

    protected Toolbar mToolbar;
    protected ImageView mToolbarIcon;
    protected TextView mToolbarTimer;
    protected TextView mToolbarTitle;

    public BaseActivity() {
    }

    public void bindToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarIcon = (ImageView) findViewById(R.id.ImgToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.TvToolbarTitle);
        mToolbarTimer = (TextView) findViewById(R.id.TvToolbarTimer);
    }

    public void setCustomTitle(String title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    public void setIcon(int i) {
        if (mToolbarIcon == null) {
            return;
        }
        switch (i) {
            default:
                mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
                return;

            case 1: // '\001'
                mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_assignment));
                return;

            case 2: // '\002'
            case 5: // '\005'
                mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_cash));
                return;

            case 3: // '\003'
                mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile));
                return;

            case 4: // '\004'
                mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_setting));
                break;
        }
    }

    protected final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            onHandlerResponse(msg);
        }
    };

    protected void onHandlerResponse(Message msg) {
        API.ApiParser(msg);
        if (so.result.getStatus() == 401)
            doLogout();
        if (so.result.getStatus() == 200)
            return;
        else
            rDialog.SetToast(this, so.result.getStatus() + " : " + so.result.getjSon());
    }

    public void doLogout() {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        edit.putString(Constants.PREF_USER_TOKEN, "");
        edit.commit();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }


}
