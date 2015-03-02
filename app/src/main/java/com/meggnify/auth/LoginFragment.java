package com.meggnify.auth;

import com.meggnify.R;
import com.meggnify.lib.LocalContext;
import com.raaf.rDialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginFragment extends Fragment {

    public static final String PROPERTY_REG_ID = "registrationId";

    private Context context;

    String regId;

    private LoginCallback mCallback;

    private EditText numberText;
    private EditText passwordText;

    private Button loginButton;
    private Button signupButton;

    public LoginFragment() {
        context = LocalContext.getContext();

        regId = getRegistrationId(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        numberText = (EditText) view.findViewById(R.id.id_number);
        passwordText = (EditText) view.findViewById(R.id.password);

        loginButton = (Button) view.findViewById(R.id.button_login);
        loginButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                rDialog.ShowProgressDialog(getActivity(), "Login to server", "please wait", true);
                HashMap<String,String> parameters = new HashMap<String,String>();
                parameters.put("login", numberText.getText().toString());
                parameters.put("password", passwordText.getText().toString());
                parameters.put("uuid", regId);

                JSONObject params = new JSONObject(parameters);
                mCallback.onLoginButtonClick(params.toString());
            }

        });

        signupButton = (Button) view.findViewById(R.id.button_signup);
        signupButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                mCallback.onSignUpButtonClick();
            }

        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (LoginCallback) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException("Uninplemented");
        }
    }

    private String getRegistrationId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("Main", "Registration not found.");
            return "";
        }
        return registrationId;
    }

    public static interface LoginCallback {
        void onSignUpButtonClick();
        void onLoginButtonClick(String parameters);
    }

}
