package com.meggnify.main;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.API;
import com.meggnify.helper.Constants;
import com.meggnify.helper.Util;
import com.meggnify.helper.so;
import com.meggnify.mission.GeneralSurveyActivity;
import com.meggnify.mission.MysteryAuditActivity;
import com.meggnify.model.Assignment;
import com.meggnify.model.Mission;
import com.raaf.rDate;
import com.raaf.rDialog;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AssignmentMissionFragment extends BaseFragment {

    private MeggnifyManager meggManager;

    private String mUserToken;

    private String type;

    private LinearLayout actionContainer,layout_location;

    private TextView nameView;
    private TextView locationView;
    private ImageView iconView;
    private TextView typeView;
    private TextView countView;
    private TextView costView;
    private TextView endTimeView;
    private TextView objectiveView;
    private TextView TvMissionEndDay,TvMissionEndMonth,TvMissionEndYear;
    private Button BtBack, BtAccept;
    private int pos;

    public AssignmentMissionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_mission, container, false);


        // mission = extras.getParcelable("mission");
        BtBack = (Button) view.findViewById(R.id.BtBack);
        BtAccept = (Button) view.findViewById(R.id.BtAccept);

        actionContainer = (LinearLayout) view.findViewById(R.id.action_container);
        layout_location = (LinearLayout) view.findViewById(R.id.layout_location);
        TvMissionEndDay= (TextView) view.findViewById(R.id.mission_end_day);
        TvMissionEndMonth= (TextView) view.findViewById(R.id.mission_end_month);
        TvMissionEndYear= (TextView) view.findViewById(R.id.mission_end_year);
        nameView = (TextView) view.findViewById(R.id.mission_name);
        locationView = (TextView) view.findViewById(R.id.mission_location);
        iconView = (ImageView) view.findViewById(R.id.mission_icon);
        typeView = (TextView) view.findViewById(R.id.mission_type);
        countView = (TextView) view.findViewById(R.id.mission_count);
        costView = (TextView) view.findViewById(R.id.mission_cost);

        endTimeView = (TextView) view.findViewById(R.id.mission_end_time);
        objectiveView = (TextView) view.findViewById(R.id.mission_objective);

        nameView.setText(so.getAssignments().get(pos).getName());


        BtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeggnifyActivity) getActivity()).AssignmentSelectItem(1);
            }
        });
        BtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("my_jobs")) {
                    rDialog.ConfirmDialog(getActivity(), "Confirmation", "Start Assignment ?", "Yes", "No", handler_start);
                } else {
                    if (so.getAssignments().size() > 2)
                        rDialog.SetToast(getActivity(), "You have reach maximum Assignment");
                    else
                        rDialog.ConfirmDialog(getActivity(), "Confirmation", "Accept Assignment ?", "Yes", "No", handler_accept);
                }
            }
        });
        return view;
    }

    protected final Handler handler_start = new Handler() {
        public void handleMessage(Message msg) {
            rDialog.SetToast(getActivity(), "Start Assignment");
            //API.MegApplyJob(mUserToken, so.getAssignments().get(pos).getId(), handler);
            //rDialog.ShowProgressDialog(getActivity(), "Accept Assignment", "please wait", true);
        }
    };

    protected final Handler handler_accept = new Handler() {
        public void handleMessage(Message msg) {
            API.MegApplyJob(mUserToken, ass.getId(), handler);
            rDialog.ShowProgressDialog(getActivity(), "Accept Assignment", "please wait", true);
        }
    };


    @Override
    protected void onHandlerResponse(Message msg) {
        super.onHandlerResponse(msg);
        if (so.result.getStatus() == 200) {
            if (so.result.getModul() == Constants.modul_meggnets && so.result.getMode() == 5) {
                //apply job
                //refresh my job
                API.MegMyJob(Util.getToken(getActivity()), handler);
            } else if (so.result.getModul() == Constants.modul_assignment && so.result.getMode() == 3) {
                //start mission
            } else if (so.result.getModul() == Constants.modul_meggnets && so.result.getMode() == 2) {
                //back to my job
                ((MeggnifyActivity) getActivity()).AssignmentSelectItem(0);
            }
        }
        rDialog.CloseProgressDialog();
    }

Assignment ass;
    @Override
    public void onResume() {
        super.onResume();
        Bundle extras = getArguments();
        mUserToken = extras.getString("token");
        type = extras.getString("type");
        pos = extras.getInt("pos");
        SimpleDateFormat simpledateformat;
        if (type.equals("my_jobs")) {
            ass = so.getAssignments().get(pos);
            if (ass.getAssignment_type().equals("Mystery Audit"))
                locationView.setText("");
            BtBack.setVisibility(View.GONE);
            BtAccept.setText("Start");
        } else {//new
            ass = so.getAssignmentsNew().get(pos);
            layout_location.setVisibility(View.GONE);
        }
        if (ass.getAssignment_type().equals("General Survey"))
            layout_location.setVisibility(View.GONE);
        iconView.setImageResource(ass.getIcon_dark());
        typeView.setText(ass.getAssignment_type());
        countView.setText("");
        costView.setText("$" + String.valueOf(ass.getPrice()));
        endTimeView.setText(ass.getEnd_time());
        objectiveView.setText(ass.getObjective());
        simpledateformat = new SimpleDateFormat("dd");
        TvMissionEndDay.setText(simpledateformat.format(ass.getDate_end()));
        simpledateformat = new SimpleDateFormat("MMM");
        TvMissionEndMonth.setText(simpledateformat.format(ass.getDate_end()));
        simpledateformat = new SimpleDateFormat("yyyy");
        TvMissionEndYear.setText(simpledateformat.format(ass.getDate_end()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
