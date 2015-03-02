package com.meggnify.main;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.model.Venue;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AssignmentMapFragment extends Fragment implements OnMapReadyCallback {

    private MeggnifyManager meggManager;

    private String mUserToken;

    private List<Venue> venues;

    private SupportMapFragment mapFragment;
    LocationManager locationManager;
    GoogleMap mMap;

    public AssignmentMapFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_map, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        venues = new ArrayList<Venue>();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(this);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.map_container, mapFragment)
                .commit();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng singapore = new LatLng(1.3146631, 103.8454093);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(singapore)
                .zoom(10)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        loadVenue(false);
    }

    void loadVenue(Boolean cache) {
        new AsyncTask<Boolean, Void, Boolean>() {

            private boolean cache;

            @Override
            protected Boolean doInBackground(Boolean... params) {
                // TODO Auto-generated method stub
                cache = params[0];

                return meggManager.refreshVenue(cache);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                if (status) {
                    List<Venue> tmpVenues = meggManager.venues;
                    venues.clear();
                    for (int i = 0; i < tmpVenues.size(); i++) {
                        Venue venue = tmpVenues.get(i);
                        mMap.addMarker(new MarkerOptions()
                                .title(venue.missionName)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                                .position(new LatLng(venue.latitude, venue.longitude)));
                        venues.add(venue);
                    }
                } else {
                    if (!cache) {
                        //Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }.execute(cache);
    }
}
