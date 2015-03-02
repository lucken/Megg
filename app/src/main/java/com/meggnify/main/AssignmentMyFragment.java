package com.meggnify.main;

import java.util.ArrayList;
import java.util.List;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.API;
import com.meggnify.helper.so;
import com.meggnify.lib.LocalContext;
import com.meggnify.model.Assignment;
import com.meggnify.model.Mission;
import com.raaf.rDialog;
import com.raaf.rNet;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AssignmentMyFragment extends Fragment {

    private MeggnifyManager meggManager;

    private String mUserToken;

    private List<Mission> missions;
    private MissionAdapter missionAdapter;
    private ListView missionList;
private TextView TvEmpty;
    private ProgressBar progressBar;

    public AssignmentMyFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_my, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");
        TvEmpty = (TextView) view.findViewById(R.id.TvEmpty);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        missions = new ArrayList<Mission>();
        missionAdapter = new MissionAdapter(getActivity(), so.getAssignments());

        missionList = (ListView) view.findViewById(R.id.list_mission);
        missionList.setEmptyView(TvEmpty);
        missionList.setAdapter(missionAdapter);
        missionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                selectItem(position);
            }
        });

        //loadMission(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!rNet.isNetworkConnected(getActivity()))
        {
            progressBar.setVisibility(View.GONE);
            TvEmpty.setText(getString(R.string.no_internet));
            return;
        }
        if (so.getAssignments().size() > 0)
            missionAdapter.notifyDataSetChanged();
        else {
            API.MegMyJob(mUserToken, handler);
            progressBar.setVisibility(View.VISIBLE);
        }
        // loadMission(false);
    }

    protected final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            API.ApiParser(msg);
            if (so.result.getStatus() == 200) {
                missionAdapter.notifyDataSetChanged();
            } else  if (so.result.getStatus() == 401) {
                ((MeggnifyActivity) getActivity()).doLogout();
            }else
                rDialog.SetToast(getActivity(), so.result.getStatus() + " : " + so.result.getjSon());
            progressBar.setVisibility(View.GONE);
        }
    };


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

                return meggManager.refreshMyJob(cache);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                progressBar.setVisibility(View.GONE);
                if (status) {
                    List<Mission> tmpMissions = meggManager.myJobs;
                    missions.clear();
                    for (int i = 0; i < tmpMissions.size(); i++) {
                        missions.add(tmpMissions.get(i));
                    }

                    missionAdapter.notifyDataSetChanged();
                } else {
                    if (!cache) {
                        //Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }.execute(cache);
    }

    void selectItem(int position) {
//        Mission mission = (Mission) missionAdapter.getItem(position);

        Bundle extras = new Bundle();
        extras.putString("token", mUserToken);
        extras.putString("type", "my_jobs");
        //extras.putParcelable("mission", mission);
        extras.putInt("pos",position);
        AssignmentMissionFragment fragment = new AssignmentMissionFragment();
        fragment.setArguments(extras);

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.assignment_container, fragment)
                .addToBackStack(null)
                .commit();
        ((MeggnifyActivity)getActivity()).AssignmentSetTitle("My Jobs - View Assignment");
    }

    private static class ViewHolder {
        TextView nameView;
        TextView endDateView;
        TextView endTimeView;
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
                convertView = mInflater.inflate(R.layout.item_mission_my, parent, false);

                holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.mission_name);
                holder.endDateView = (TextView) convertView.findViewById(R.id.mission_end_date);
                holder.endTimeView = (TextView) convertView.findViewById(R.id.mission_end_time);
                holder.iconView = (ImageView) convertView.findViewById(R.id.mission_icon);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Assignment as = (Assignment) getItem(position);
            if (as != null) {
                holder.nameView.setText(as.getName());
                holder.endDateView.setText(as.getEnd_date());
                holder.endTimeView.setText(as.getEnd_time());
                if (as.getAssignment_type().equals("Mystery Audit")) {
                    holder.iconView.setImageResource(R.drawable.ic_mystery_audit);
                } else {
                    holder.iconView.setImageResource(R.drawable.ic_general_survey);
                }

            }

            return convertView;
        }
    }

}
