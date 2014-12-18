package com.radiumdigital.meggnify.auth;

import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.R.id;
import com.radiumdigital.meggnify.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

	private LoginCallback mCallback;

    private EditText numberText;
    private EditText passwordText;

	private Button loginButton;
	private Button signupButton;
	
	public LoginFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);

        numberText = (EditText) view.findViewById(id.id_number);
        passwordText = (EditText) view.findViewById(id.password);

		loginButton = (Button) view.findViewById(R.id.button_login);
		loginButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
                HashMap<String,String> parameters = new HashMap<String,String>();
                parameters.put("login", numberText.getText().toString());
                parameters.put("password", passwordText.getText().toString());
                parameters.put("uuid", "123");

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

	public static interface LoginCallback {
		void onSignUpButtonClick();
		void onLoginButtonClick(String parameters);
	}

}
