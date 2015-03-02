package com.meggnify.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CashFragment extends Fragment {

    private static final int FRAGMENT_POSITION = 2;

    private IMainListener mListener;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private TextView cashHarvest;
    private TextView cashConsumed;
    private ImageView ImgCash;
private Button BtPaypall,BtBank;
    private ProgressBar progressBar;

    public CashFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        cashHarvest = (TextView) view.findViewById(R.id.cash_harvest);
        cashConsumed = (TextView) view.findViewById(R.id.cash_consumed);
        BtBank = (Button) view.findViewById(R.id.BtCashBank);
        BtPaypall = (Button) view.findViewById(R.id.BtCashPaypall);
        ImgCash = (ImageView) view.findViewById(R.id.ImgCash);
        ImgCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeggnifyActivity)getActivity()).ChangeModule(5);
            }
        });
        loadCash();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (IMainListener) activity;
            mListener.onFragmentAttached(FRAGMENT_POSITION);
        } catch(ClassCastException e) {
            throw new ClassCastException("Activity must implement DrawerCallback.");
        }
    }

    void loadCash() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                // TODO Auto-generated method stub
                return meggManager.refreshProfile();
            }

            @Override
            protected void onPostExecute(Boolean status) {
                progressBar.setVisibility(View.GONE);
                if (status) {
                    JSONObject cash = meggManager.profile;
                    try {
                        cashHarvest.setText(String.format("Harvest Value ($%s)", cash.getString("harvest_value")));
                        cashConsumed.setText(String.format("Consumed  Value ($%s)", cash.getString("consumed_value")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute();
    }

}
