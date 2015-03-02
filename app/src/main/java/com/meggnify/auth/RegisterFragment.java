package com.meggnify.auth;

import com.meggnify.R;
import com.meggnify.lib.LocalContext;

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

public class RegisterFragment extends Fragment {

    public static final String PROPERTY_REG_ID = "registrationId";

    private Context context;

    String regId;

    private RegisterCallback mCallback;

    private EditText numberText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText phoneText;
    private EditText passwordText;
    private EditText passwordConfirmText;

    private Button submitButton;

    public RegisterFragment() {
        context = LocalContext.getContext();

        regId = getRegistrationId(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        numberText = (EditText) view.findViewById(R.id.id_number);
        firstNameText = (EditText) view.findViewById(R.id.first_name);
        lastNameText = (EditText) view.findViewById(R.id.last_name);
        emailText = (EditText) view.findViewById(R.id.email);
        phoneText = (EditText) view.findViewById(R.id.phone);
        passwordText = (EditText) view.findViewById(R.id.password);
        passwordConfirmText = (EditText) view.findViewById(R.id.password_confirm);

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
                parameters.put("phone_number", phoneText.getText().toString());
                parameters.put("password", passwordText.getText().toString());
                parameters.put("password_confirmation", passwordConfirmText.getText().toString());
                parameters.put("uuid", regId);

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

    private String getRegistrationId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("Main", "Registration not found.");
            return "";
        }
        return registrationId;
    }

    public interface RegisterCallback {
        void onSubmitButtonClick(String parameters);
    }

}
