package com.meggnify.auth;

import com.meggnify.R;
import com.meggnify.helper.Constants;
import com.meggnify.lib.LocalContext;
import com.meggnify.main.MeggnifyActivity;
import com.meggnify.MeggnifyManager;
import com.raaf.rDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class AuthActivity extends ActionBarActivity implements LoginFragment.LoginCallback, RegisterFragment.RegisterCallback {

    private MeggnifyManager meggManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        meggManager = MeggnifyManager.sharedInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void onSignUpButtonClick() {
        showRegister();
    }

    @Override
    public void onLoginButtonClick(final String parameters) {
        String params = String.format("{\"meggnet\":%s}", parameters);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                String requestParams = params[0];

                return meggManager.login(requestParams);
            }

            @Override
            protected void onPostExecute(String token) {
                if (token != null) {
                    storeToken(token);

                    goToActivity();
                } else {
                    Toast.makeText(AuthActivity.this, "Could not log you in", Toast.LENGTH_SHORT).show();
                }
                rDialog.CloseProgressDialog();
            }

        }.execute(params);
    }

    @Override
    public void onSubmitButtonClick(String parameters) {
        String params = String.format("{\"meggnet\":%s}", parameters);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                String requestParams = params[0];

                return meggManager.register(requestParams);
            }

            @Override
            protected void onPostExecute(String token) {
                if (token != null) {
                    storeToken(token);

                    goToActivity();
                } else {
                    Toast.makeText(AuthActivity.this, "Could not log you in", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute(params);
    }

    private void storeToken(String token) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(LocalContext.getContext());
        sp.edit().putString(Constants.PREF_USER_TOKEN, token).commit();
    }

    void showRegister() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    void goToActivity() {
        Intent intent = new Intent(AuthActivity.this, MeggnifyActivity.class);
        startActivity(intent);
        finish();
    }
}