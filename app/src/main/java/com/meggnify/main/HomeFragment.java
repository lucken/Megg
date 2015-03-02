package com.meggnify.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.API;
import com.meggnify.helper.BaseFragment;
import com.meggnify.helper.Util;
import com.meggnify.helper.so;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends BaseFragment {

    private static final int FRAGMENT_POSITION = 0;

    private IMainListener mListener;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private TextView welcome;
    private TextView pendingAssignment;
    private TextView earliestAssignment;

    private ProgressBar progressBar;

    public HomeFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);


        welcome = (TextView) view.findViewById(R.id.welcome);
        pendingAssignment = (TextView) view.findViewById(R.id.assignment_pending);
        earliestAssignment = (TextView) view.findViewById(R.id.assignment_earliest);

       // loadSummary();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (so.getUser().getName()==null)
            API.MegSummary(Util.getToken(getActivity()), handler);
        else
            ShowData();
    }

    @Override
    protected void onHandlerResponse(Message msg) {
        super.onHandlerResponse(msg);
        if (so.result.getStatus()==200){
           ShowData();
        }
    }

    private void ShowData(){
        welcome.setText(String.format("Hello %s,", so.getUser().getName()));
        pendingAssignment.setText(String.format("You have %s pending missions", so.getUser().getNumber_of_pending()));
        earliestAssignment.setText(String.format("Your earliest assignment ends at %s", so.getUser().getDate()));
        progressBar.setVisibility(View.GONE);
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

    void loadSummary() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                // TODO Auto-generated method stub
                return meggManager.refreshSummary();
            }

            @Override
            protected void onPostExecute(Boolean status) {
                progressBar.setVisibility(View.GONE);
                if (status) {
                    JSONObject summary = meggManager.summary;
                    if (summary==null){
                        ((MeggnifyActivity) getActivity()).doLogout();
                    return;}
                    try {
                        welcome.setText(String.format("Hello %s,", summary.getString("name")));
                        pendingAssignment.setText(String.format("You have %s pending missions", summary.getString("number_of_pending")));
                        earliestAssignment.setText(String.format("Your earliest assignment ends at %s", summary.getString("date")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute();
    }

}
