package com.meggnify.main;

import java.util.ArrayList;
import java.util.List;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.API;
import com.meggnify.helper.BaseFragment;
import com.meggnify.helper.Util;
import com.meggnify.helper.so;
import com.meggnify.model.Assignment;
import com.meggnify.model.Mission;
import com.raaf.rDialog;
import com.raaf.rNet;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AssignmentNewFragment extends BaseFragment {

    private MeggnifyManager meggManager;

    private String mUserToken;

    private MissionAdapter missionAdapter;
    private GridView missionGrid;
    private TextView empty;
    private ProgressBar progressBar;

    public AssignmentNewFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_new, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");
        empty = (TextView) view.findViewById(R.id.empty);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);


        missionAdapter = new MissionAdapter(getActivity(), so.getAssignmentsNew());

        missionGrid = (GridView) view.findViewById(R.id.grid_mission);
        missionGrid.setEmptyView(empty);
        missionGrid.setAdapter(missionAdapter);
        missionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                selectItem(position);
            }

        });

        // loadMission(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!rNet.isNetworkConnected(getActivity()))
        {
            progressBar.setVisibility(View.GONE);
            empty.setText(getString(R.string.no_internet));
            return;
        }
        if (so.getAssignmentsNew().size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            API.AssNewJob(Util.getToken(getActivity()), handler);
        } else
            showData();
        // loadMission(false);
    }

    private void showData() {
        progressBar.setVisibility(View.GONE);
        missionAdapter.notifyDataSetChanged();
        if (so.getAssignmentsNew().size()==0)
            empty.setText(getString(R.string.no_record));
    }

    @Override
    protected void onHandlerResponse(Message msg) {
        super.onHandlerResponse(msg);
        if (so.result.getStatus() == 200) {
            showData();
        }
    }

    void loadMission(boolean cache) {
        new AsyncTask<Boolean, Void, Boolean>() {

            private boolean cache;

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Boolean... params) {
                // TODO Auto-generated method stub
                cache = params[0];

                return meggManager.refreshNewJob(cache);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                progressBar.setVisibility(View.GONE);
                if (status) {
                    List<Mission> tmpMissions = meggManager.newJobs;
//                    missions.clear();
//                    for (int i = 0; i < tmpMissions.size(); i++) {
//                        missions.add(tmpMissions.get(i));
//                    }

                    missionAdapter.notifyDataSetChanged();
                    rDialog.CloseProgressDialog();
                } else {
                    if (!cache) {
                        //Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }.execute(cache);
    }

    void selectItem(int position) {
      //  Mission mission = (Mission) missionAdapter.getItem(position);

        Bundle extras = new Bundle();
        extras.putString("token", mUserToken);
        extras.putString("type", "new_jobs");
        //extras.putParcelable("mission", mission);
        extras.putInt("pos",position);
        AssignmentMissionFragment fragment = new AssignmentMissionFragment();
        fragment.setArguments(extras);

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.assignment_container, fragment)
                .addToBackStack(null)
                .commit();
        ((MeggnifyActivity) getActivity()).AssignmentSetTitle("New Jobs - View Assignment");
    }

    private static class ViewHolder {
        TextView nameView;
        ImageView iconView;
    }

    private class MissionAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;
        private ArrayList<Assignment> assignments;

        public MissionAdapter(Context context, ArrayList<Assignment> assignments) {
            mContext = context;
            this.assignments = assignments;
            mInflater = ((Activity) mContext).getLayoutInflater();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return assignments.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return assignments.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_mission_new, parent, false);

                holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.mission_name);
                holder.iconView = (ImageView) convertView.findViewById(R.id.mission_icon);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Assignment assignments = (Assignment) getItem(position);
            if (assignments != null) {
                holder.nameView.setText(assignments.getName());
                holder.iconView.setImageResource(assignments.getIcon());
            }
            return convertView;
        }

    }

}
