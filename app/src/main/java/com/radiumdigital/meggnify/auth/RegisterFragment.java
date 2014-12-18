package com.radiumdigital.meggnify.auth;

import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.R.id;
import com.radiumdigital.meggnify.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterFragment extends Fragment {

	private RegisterCallback mCallback;

    private EditText numberText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText phoneText;
    private EditText passwordText;
    private EditText passwordConfirmText;

	private Button submitButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register, container, false);

        numberText = (EditText) view.findViewById(id.id_number);
        firstNameText = (EditText) view.findViewById(id.first_name);
        lastNameText = (EditText) view.findViewById(id.last_name);
        emailText = (EditText) view.findViewById(id.email);
        phoneText = (EditText) view.findViewById(id.phone);
        passwordText = (EditText) view.findViewById(id.password);
        passwordConfirmText = (EditText) view.findViewById(id.password_confirm);

		submitButton = (Button) view.findViewById(R.id.button_submit);
		submitButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
                HashMap<String,String> parameters = new HashMap<String,String>();
                parameters.put("identification_number", numberText.getText().toString());
                parameters.put("fist_name", firstNameText.getText().toString());
                parameters.put("last_name", lastNameText.getText().toString());
                parameters.put("email", emailText.getText().toString());
                parameters.put("phone", phoneText.getText().toString());
                parameters.put("password", passwordText.getText().toString());
                parameters.put("password_confirmation", passwordConfirmText.getText().toString());
                parameters.put("uuid", "123");

                JSONObject params = new JSONObject(parameters);
				mCallback.onSubmitButtonClick(params.toString());
			}
			
		});
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (RegisterCallback) activity;
		} catch(ClassCastException e) {
			throw new ClassCastException("Uninplemented");
		}
	}
	
	public interface RegisterCallback {
		void onSubmitButtonClick(String parameters);
	}

}
