package com.meggnify.main;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.model.Venue;

import java.util.ArrayList;
import java.util.List;

public class AssignmentVenueFragment extends DialogFragment {

    private MeggnifyManager meggManager;

    private String mUserToken;

    private int missionId;

    private List<Venue> venues;
    private VenueAdapter venueAdapter;
    private ListView venueList;

    private ProgressBar progressBar;

    public AssignmentVenueFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_venue, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");
        missionId = extras.getInt("mission_id");

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        venues = new ArrayList<Venue>();
        venueAdapter = new VenueAdapter(getActivity(), venues);

        venueList = (ListView) view.findViewById(R.id.list_venue);
        venueList.setAdapter(venueAdapter);
        venueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                selectItem(position);
            }
        });

        loadVenue();

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void loadVenue() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                // TODO Auto-generated method stub
                return meggManager.refreshJobVenue(missionId);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                progressBar.setVisibility(View.GONE);
                if (status) {
                    List<Venue> tmpVenues = meggManager.newJobVenues;
                    venues.clear();
                    for (int i = 0; i < tmpVenues.size(); i++) {
                        venues.add(tmpVenues.get(i));
                    }

                    venueAdapter.notifyDataSetChanged();
                } else {

                }

            }

        }.execute();
    }

    void selectItem(int position) {

    }

    private static class ViewHolder {
        TextView nameView;
        TextView addressView;
    }

    private class VenueAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;
        private List<Venue> mVenues;

        public VenueAdapter(Context context, List<Venue> venues) {
            mContext = context;
            mVenues = venues;
            mInflater = ((Activity) mContext).getLayoutInflater();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mVenues.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mVenues.get(position);
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
                convertView = mInflater.inflate(R.layout.item_mission_venue, parent, false);

                holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.venue_name);
                holder.addressView = (TextView) convertView.findViewById(R.id.venue_address);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Venue venue = (Venue) getItem(position);
            if (venue != null) {
                holder.nameView.setText(venue.name);
                holder.addressView.setText(venue.address);
            }

            return convertView;
        }
    }


}
